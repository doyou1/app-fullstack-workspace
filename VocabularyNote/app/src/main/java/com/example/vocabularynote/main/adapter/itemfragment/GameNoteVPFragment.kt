package com.example.vocabularynote.main.adapter.itemfragment

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.FragmentGameNoteVpBinding
import com.example.vocabularynote.room.viewmodel.GameNoteItemViewModel

class GameNoteVPFragment(private val item: GameNoteItemViewModel, private val position: Int) :
    Fragment() {

    private var _binding: FragmentGameNoteVpBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

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
        val frontAnimation = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.game_card_front_animator
        ) as AnimatorSet
        val backAnimation = AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.game_card_back_animator
        ) as AnimatorSet

        binding.layoutWrap.setOnClickListener {

//            (recyclerView?.layoutManager as CarouselLayoutManager).isScrollEnabled = false
            if (item.showKey) {
                frontAnimation.setTarget(binding.layoutKey)
                backAnimation.setTarget(binding.layoutValue)
                frontAnimation.start()
                backAnimation.start()
            } else {
                frontAnimation.setTarget(binding.layoutValue)
                backAnimation.setTarget(binding.layoutKey)
                frontAnimation.start()
                backAnimation.start()
            }
            item.showKey = !item.showKey
            binding.showKey = item.showKey


//            backAnimation.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    super.onAnimationEnd(animation)
//                    (recyclerView?.layoutManager as CarouselLayoutManager).isScrollEnabled =
//                        true
//                }
//            })
        }
    }

}