package com.example.vocabularynote.main.adapter.itemfragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.vocabularynote.databinding.FragmentGameNoteVpBinding
import com.example.vocabularynote.room.viewmodel.GameNoteItemViewModel


class GameNoteVPFragment(private val item: GameNoteItemViewModel) : Fragment() {
    private var _binding: FragmentGameNoteVpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameNoteVpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.item = item
        binding.showKey = item.showKey
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.layoutWrap.setOnClickListener {
            val backAnimation =
                if (item.showKey) ObjectAnimator.ofFloat(binding.layoutValue, "scaleX", 0f, 1f)
                else ObjectAnimator.ofFloat(binding.layoutKey, "scaleX", 0f, 1f)
            backAnimation.interpolator = AccelerateDecelerateInterpolator()
            backAnimation.start()
            item.showKey = !item.showKey
            binding.showKey = item.showKey
        }
    }

}