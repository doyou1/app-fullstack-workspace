package com.example.vocabularynote.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vocabularynote.R
import com.example.vocabularynote.Temp
import com.example.vocabularynote.databinding.FragmentMainEditDetailBinding
import com.example.vocabularynote.databinding.FragmentMainGameBinding
import com.example.vocabularynote.databinding.FragmentMainSettingBinding
import com.example.vocabularynote.main.adapter.EditNoteRvAdapter
import com.example.vocabularynote.main.adapter.NoteRvAdapter
import com.google.android.material.textfield.TextInputEditText

class MainEditDetailFragment : Fragment() {

    private var _binding: FragmentMainEditDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainEditDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
        handler.postDelayed({
            setEditRecyclerView()
        }, Temp.DELAY_SHOW_UI)
        setBackPressEvent()
        aboutKeyboard()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            (binding.recyclerView.adapter as EditNoteRvAdapter).print()
            val result = (binding.recyclerView.adapter as EditNoteRvAdapter).save()
            Log.e(TAG, "result: $result")
        }
        binding.btnItemAdd.setOnClickListener {
            val newSize = (binding.recyclerView.adapter as EditNoteRvAdapter).addEditItem()
            (binding.recyclerView.adapter as EditNoteRvAdapter).notifyItemRangeChanged(newSize, 1)
        }
    }

    private fun setEditRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EditNoteRvAdapter(Temp.TEMP_EDIT_NOTE_LIST)
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
        }
    }

    private fun hideKeyboard() {
        val im =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        requireActivity().currentFocus?.clearFocus()
    }
}