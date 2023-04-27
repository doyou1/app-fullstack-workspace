package com.example.vocabularynote.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.FragmentMainAddBinding
import com.google.android.material.textfield.TextInputEditText

class MainAddFragment : Fragment() {

    private var _binding: FragmentMainAddBinding? = null
    private val binding get() = _binding!!
    private var _navController: NavController? = null
    private val navController get() = _navController!!

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainAddBinding.inflate(inflater, container, false)
        _navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutKeyboard()
        setBackPressEvent()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSave.setOnClickListener {
            if (isValidate()) {
                val title = binding.etTitle.text!!.toString()
                val memo = binding.etMemo.text.toString()

                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("memo", memo)
                navController.navigate(R.id.action_add_to_edit, bundle)
            }
        }
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