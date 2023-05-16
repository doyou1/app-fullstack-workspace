package com.example.vocabularynote.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.databinding.FragmentMainViewBinding
import com.example.vocabularynote.main.adapter.EditNoteRvAdapter
import com.example.vocabularynote.main.adapter.ViewNoteRvAdapter
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewFragment : Fragment() {

    private var _binding: FragmentMainViewBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainViewBinding.inflate(inflater, container, false)
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
                    lifecycleScope.launch(Dispatchers.Main) {
                        val merged = arrayListOf<NoteItem>()
                        // add title
                        merged.add(NoteItem(-1, -1, "Key", "Value"))
                        merged.addAll(list)
                        setRecyclerView(merged)
                    }
                }
            }, Const.DELAY_SHOW_UI)
        }

        setBackPressEvent()
    }

    private fun setRecyclerView(list: List<NoteItem>) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ViewNoteRvAdapter(list)
        binding.showUI = true
    }

    private fun setBackPressEvent() {
        // This callback will only be called when MyFragment is at least Started.
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