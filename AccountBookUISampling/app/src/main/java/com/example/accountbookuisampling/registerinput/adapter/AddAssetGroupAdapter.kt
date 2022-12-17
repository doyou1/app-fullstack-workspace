package com.example.accountbookuisampling.registerinput.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountbookuisampling.databinding.RvItemTextInputBinding
import com.example.accountbookuisampling.registerinput.dataclass.InputDateItem
import com.example.accountbookuisampling.registerinput.fragment.AddAssetGroupDialog
import com.example.accountbookuisampling.registerinput.fragment.BaseRegisterInputFragment
import com.example.accountbookuisampling.room.entities.Asset
import com.example.accountbookuisampling.util.TEXT_ADD

class AddAssetGroupAdapter(private val list: ArrayList<Asset>, private val dialog: AddAssetGroupDialog) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        fun bind(item: Asset) {
            binding.btnInput.text = item.name
            binding.btnInput.setOnClickListener {
                dialog.changeGroup(item.name)
            }
        }
    }

}