package com.example.vocabularynote.main

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.appcompat.app.AppCompatActivity
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
import com.example.vocabularynote.util.Const.TEXT_KEY
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID
import com.example.vocabularynote.util.Const.TEXT_RESULT
import com.example.vocabularynote.util.Const.TEXT_VALUE
import com.example.vocabularynote.util.DataUtil
import com.example.vocabularynote.util.FileUtil
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.Workbook

class MainEditDetailFragment : Fragment() {

    private var _binding: FragmentMainEditDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private val fileExplorerLauncher = getFileExplorerLauncherResultLauncher()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainEditDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val noteId = it.getLong(Const.TEXT_NOTE_ID, -1)
            handler.postDelayed({
                lifecycleScope.launch(Dispatchers.IO) {
                    val list =
                        (requireActivity().application as BaseApplication).noteDao.getNoteItemAllByNoteId(
                            noteId
                        )

                    val maxId =
                        (requireActivity().application as BaseApplication).noteDao.getNoteItemMaxId()

                    val note =
                        (requireActivity().application as BaseApplication).noteDao.getNoteById(
                            noteId
                        )

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
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            fileExplorerLauncher.launch(intent)
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

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }

    private fun getFileExplorerLauncherResultLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                if (result.data?.data != null) {
                    val uri: Uri? = result.data?.data
                    if (uri != null) {
                        val fileExtension = FileUtil.getFileExtension(requireContext(), uri)
                        if (fileExtension != null) {
                            val workbook =
                                FileUtil.getWorkbook(requireContext(), fileExtension, uri)
                            if (workbook != null) {
                                val result = getNoteItems(workbook)
                                if (result != null && result.isNotEmpty()) {
                                    val newSize =
                                        (binding.recyclerView.adapter as EditNoteRvAdapter).addAllEditItem(
                                            DataUtil.convertToNoteItemViewModel(result)
                                        )
                                    (binding.recyclerView.adapter as EditNoteRvAdapter).notifyItemRangeChanged(
                                        newSize,
                                        result.size
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    private fun getNoteItems(workbook: Workbook): List<NoteItem>? {
        val sheet = workbook.getSheet(TEXT_RESULT)
        val keyTitle = sheet.getRow(0).getCell(0).toString()
        val valueTitle = sheet.getRow(0).getCell(1).toString()
        return if (keyTitle == TEXT_KEY && valueTitle == TEXT_VALUE) {
            val result = arrayListOf<NoteItem>()
            for (i in 1..sheet.lastRowNum) {
                val row = sheet.getRow(i)
                val key =
                    if (row.getCell(0) == null || row.getCell(0).toString()
                            .trim().isEmpty()
                    ) null else row.getCell(0)
                val value =
                    if (row.getCell(1) == null || row.getCell(1).toString()
                            .trim().isEmpty()
                    ) null else row.getCell(1)
                if (key == null || value == null) continue
                val item = NoteItem(key = key.toString(), value = value.toString())
                result.add(item)
            }
            result
        } else null
    }
}