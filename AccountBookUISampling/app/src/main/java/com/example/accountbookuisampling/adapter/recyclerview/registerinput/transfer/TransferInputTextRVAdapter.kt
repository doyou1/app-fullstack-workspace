package com.example.accountbookuisampling.adapter.recyclerview.registerinput.transfer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.FragmentTransferBinding
import com.example.accountbookuisampling.databinding.RvItemTextInputBinding
import com.example.accountbookuisampling.fragment.registerinput.transfer.RegisterTransferInputTextFragment
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.FLAG_CATEGORY
import com.example.accountbookuisampling.util.TEXT_ADD

class TransferInputTextRVAdapter(
    list: ArrayList<String>,
    private val parentBinding: FragmentTransferBinding,
    private val inputFragment: RegisterTransferInputTextFragment,
    private val flag: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemTextInputBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TextInputViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as TextInputViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class TextInputViewHolder(private val binding: RvItemTextInputBinding) :
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
                            if(parentBinding.etCategory.text.toString().isEmpty()) parentBinding.etCategory.requestFocus()
                            else if(parentBinding.etAmount.text.toString().isEmpty()) parentBinding.etAmount.requestFocus()
                            else if(parentBinding.etDetail.text.toString().isEmpty()) parentBinding.etDetail.requestFocus()
                        }
                        FLAG_CATEGORY -> {
                            parentBinding.etCategory.setText(item)
                            if(parentBinding.etAmount.text.toString().isEmpty()) parentBinding.etAmount.requestFocus()
                            else if(parentBinding.etDetail.text.toString().isEmpty()) parentBinding.etDetail.requestFocus()
                        }
                    }
                    inputFragment.dismiss()
                }
            }
        }
    }
}