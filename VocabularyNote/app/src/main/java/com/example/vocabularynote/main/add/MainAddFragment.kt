package com.example.vocabularynote.main.add

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.FragmentMainAddBinding
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.util.AppMsgUtil
import com.example.vocabularynote.util.Const
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainAddFragment : Fragment() {

    private var _binding: FragmentMainAddBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())
    private var prev: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id = it.getLong(Const.TEXT_NOTE_ID, -1)
            if (id != -1L) {
                setPrevNote(id)
            }
        }
        aboutKeyboard()
        setBackPressEvent()
        setClickEvent()
        setSwitchOnChangedEvent()
        setSelectLanguageSpinner()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            if (isValidate()) {
                val title = binding.etTitle.text!!.toString()
                val memo = binding.etMemo.text.toString()
                val useTranslation = binding.swiUseTranslation.isChecked
                var keyCountryCode = ""
                var valueCountryCode = ""
                if (useTranslation) {
                    val keyId = binding.spinnerKey.selectedItemId
                    val valueId = binding.spinnerValue.selectedItemId
                    if (keyId == valueId) {
                        AppMsgUtil.showErrMsg(
                            requireContext().getString(R.string.text_select_equal_language),
                            requireActivity()
                        )
                        return@setOnClickListener
                    }
                    keyCountryCode = Const.SELECT_LANGUAGE_LIST[keyId.toInt()].countryCode
                    valueCountryCode = Const.SELECT_LANGUAGE_LIST[valueId.toInt()].countryCode
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    if (prev != null) {
                        val item = Note(
                            prev!!.id,
                            title,
                            memo,
                            useTranslation,
                            keyCountryCode,
                            valueCountryCode
                        )
                        (requireActivity().application as BaseApplication).noteDao.updateNote(item)
                    } else {
                        val item =
                            Note(0, title, memo, useTranslation, keyCountryCode, valueCountryCode)
                        (requireActivity().application as BaseApplication).noteDao.insertNote(item)
                    }
                    lifecycleScope.launch(Dispatchers.Main) {
                        Navigation.findNavController(requireView())
                            .navigate(R.id.action_add_to_edit)
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            // Handle the back button event
            Navigation.findNavController(requireView()).navigateUp()
        }
    }

    private fun setSwitchOnChangedEvent() {
        binding.swiUseTranslation.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutSelectTranslationLanguage.visibility =
                if (isChecked) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun setPrevNote(id: Long) {
        handler.postDelayed({
            lifecycleScope.launch(Dispatchers.IO) {
                val item =
                    (requireActivity().application as BaseApplication).noteDao.getNoteById(
                        id
                    )
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.etTitle.setText(item.title)
                    binding.etMemo.setText(item.memo)
                    binding.swiUseTranslation.isChecked = item.useTranslation

                    if (item.useTranslation) {
                        binding.layoutSelectTranslationLanguage.visibility = View.VISIBLE
                        val prevKeyIdx =
                            Const.SELECT_LANGUAGE_LIST.filter { v -> v.countryCode == item.keyLanguage }[0].id.toInt()
                        val prevValueIdx =
                            Const.SELECT_LANGUAGE_LIST.filter { v -> v.countryCode == item.valueLanguage }[0].id.toInt()
                        binding.spinnerKey.setSelection(prevKeyIdx)
                        binding.spinnerValue.setSelection(prevValueIdx)
                    }
                    prev = item
                }
            }
        }, Const.DELAY_SHOW_UI)
    }

    private fun setSelectLanguageSpinner() {
        binding.spinnerKey.adapter = SelectLanguageAdapter(Const.SELECT_LANGUAGE_LIST)
        binding.spinnerKey.setSelection(1)
        binding.spinnerValue.adapter = SelectLanguageAdapter(Const.SELECT_LANGUAGE_LIST)
        binding.spinnerValue.setSelection(2)
    }

    private fun isValidate(): Boolean {
        if (binding.etTitle.text == null) return false
        return true
    }

    private fun aboutKeyboard() {
        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
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
}