package com.example.vocabularynote.main

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.FragmentMainEditDetailBinding
import com.example.vocabularynote.main.adapter.EditNoteRvAdapter
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.util.AppMsgUtil
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.Const.TEXT_INSERT_NOTE_ITEM_SUCCESS
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID
import com.example.vocabularynote.util.DataUtil
import com.example.vocabularynote.util.FileUtil
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainEditDetailFragment : Fragment() {

    private var _binding: FragmentMainEditDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private val openDocumentLauncher = getOpenDocumentLauncher()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainEditDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val noteId = it.getLong(TEXT_NOTE_ID, -1)
            handler.postDelayed({
                lifecycleScope.launch(Dispatchers.IO) {
                    val list =
                        (requireActivity().application as BaseApplication).noteDao.getNoteItemAllByNoteId(noteId)
                    val maxId = (requireActivity().application as BaseApplication).noteDao.getNoteItemMaxId()
                    val note =
                        (requireActivity().application as BaseApplication).noteDao.getNoteById(noteId)
                    lifecycleScope.launch(Dispatchers.Main) {
                        setRecyclerView(list, noteId, maxId + 1, note.useTranslation)
                    }
                }
            }, Const.DELAY_SHOW_UI)
        }
        setClickEvent()
        setBackPressEvent()
        aboutKeyboard()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            (binding.recyclerView.adapter as EditNoteRvAdapter).print()
            val result = (binding.recyclerView.adapter as EditNoteRvAdapter).getResult()
            lifecycleScope.launch(Dispatchers.IO) {
                (requireActivity().application as BaseApplication).noteDao.insertNoteItemAll(result)
                lifecycleScope.launch(Dispatchers.Main) {
                    AppMsgUtil.showMsg(TEXT_INSERT_NOTE_ITEM_SUCCESS, requireActivity())
                    arguments?.let {
                        val noteId = it.getLong(Const.TEXT_NOTE_ID, -1)
                        val bundle = Bundle()
                        bundle.putLong(TEXT_NOTE_ID, noteId)
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_refresh_edit_detail, bundle)
                    }
                }
            }
        }
        binding.btnItemAdd.setOnClickListener {
            val newSize = (binding.recyclerView.adapter as EditNoteRvAdapter).addEditItem()
            (binding.recyclerView.adapter as EditNoteRvAdapter).notifyItemRangeChanged(newSize, 1)
        }
        binding.btnImport.setOnClickListener {
            // android 13 above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) processImportExcelAboveAndroid13()
            // android 13 under
            else processImportExcelUnderAndroid13()
        }
        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
        binding.btnImportDescription.setOnClickListener {
            binding.showImportDescription = true
        }
    }

    private fun setRecyclerView(
        list: List<NoteItem>,
        noteId: Long,
        nextId: Long,
        useTranslation: Boolean
    ) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EditNoteRvAdapter(
            DataUtil.convertToNoteItemViewModel(list),
            noteId,
            nextId,
            useTranslation
        )
        binding.showUI = true
    }

    private fun setBackPressEvent() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun aboutKeyboard() {
        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
            if (it.id != R.id.layout_import_description) {
                binding.showImportDescription = false
            }
        }
    }
    private fun processImportExcelAboveAndroid13() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openDocumentLauncher.launch(intent)
    }

    private fun processImportExcelUnderAndroid13() {
        // **Need to check if it works**
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        openDocumentLauncher.launch(intent)
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    private fun getOpenDocumentLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    it.data?.let { uri ->
                        if (FileUtil.isXlsxType(uri)) {
                            val takeFlags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            requireContext().contentResolver.takePersistableUriPermission(
                                uri,
                                takeFlags
                            )
                            FileUtil.readExcel(uri, requireContext())?.let { workbook ->
                                DataUtil.convertExcelToItems(workbook)?.let { list ->
                                    val newSize = (binding.recyclerView.adapter as EditNoteRvAdapter).addAllEditItem(DataUtil.convertToNoteItemViewModel(list))
                                    (binding.recyclerView.adapter as EditNoteRvAdapter).notifyItemRangeChanged(newSize, list.size)
                                }
                            }
                        }
                    }
                }
            }
        }
}