package com.example.accountbookuisampling.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.FragmentIncomeBinding
import com.example.accountbookuisampling.databinding.RvItemIncomeTextInputBinding
import com.example.accountbookuisampling.fragment.registerinput.RegisterIncomeInputFragment
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.FLAG_CATEGORY
import com.example.accountbookuisampling.util.TEXT_ADD

class IncomeInputTextRVAdapter(
    list: ArrayList<String>,
    private val parentBinding: FragmentIncomeBinding,
    private val inputFragment: RegisterIncomeInputFragment,
    private val flag: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemIncomeTextInputBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncomeTextInputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as IncomeTextInputViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class IncomeTextInputViewHolder(private val binding: RvItemIncomeTextInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.btnInput.text = item
            binding.btnInput.setOnClickListener {
                if (item == TEXT_ADD) {
                    inputFragment.openAddTextInputActivityResultLauncher()
                } else {
                    when (flag) {
                        FLAG_ASSET -> {
                            parentBinding.etAsset.setText(item)
//                            parentBinding.etAsset.clearFocus()
                            parentBinding.etCategory.requestFocus()
                        }
                        FLAG_CATEGORY -> {
                            parentBinding.etCategory.setText(item)
//                            parentBinding.etCategory.clearFocus()
                            parentBinding.etAmount.requestFocus()
                        }
                    }
                    inputFragment.dismiss()
                }
            }
        }
    }
}