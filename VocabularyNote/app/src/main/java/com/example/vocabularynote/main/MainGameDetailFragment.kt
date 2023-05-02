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
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.Temp
import com.example.vocabularynote.databinding.FragmentMainGameDetailBinding
import com.example.vocabularynote.main.adapter.GameNoteRvAdapter
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.util.CarouselLayoutManager
import com.example.vocabularynote.util.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainGameDetailFragment : Fragment() {

    private var _binding: FragmentMainGameDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
        setBackPressEvent()
    }

    private fun setClickEvent() {
        binding.swiRandom.setOnCheckedChangeListener { _, isChecked ->
            binding.isRandom = isChecked
        }
        binding.btnStart.setOnClickListener {
            binding.isStart = true
            arguments?.let {
                val noteId = it.getInt(Const.TEXT_NOTE_ID, -1)
                handler.postDelayed({
                    lifecycleScope.launch(Dispatchers.IO) {
                        val list =
                            (requireActivity().application as BaseApplication).noteDao.getNoteItemAllByNoteId(
                                noteId
                            )
                        lifecycleScope.launch(Dispatchers.Main) {
                            val isRandom: Boolean? = binding.isRandom
                            if (isRandom != null && isRandom) {
                                setRecyclerView(list.shuffled())
                            } else {
                                setRecyclerView(list)
                            }
                        }
                    }
                }, Const.DELAY_SHOW_UI)
            }
        }
    }
    private fun setRecyclerView(list: List<NoteItem>) {
        binding.recyclerView.layoutManager =
            CarouselLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = GameNoteRvAdapter(list)
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
}