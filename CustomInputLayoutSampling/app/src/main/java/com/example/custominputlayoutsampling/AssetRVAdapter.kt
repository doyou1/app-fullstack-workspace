package com.example.custominputlayoutsampling

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.RvItemAssetBinding

class AssetRVAdapter(private val list: ArrayList<String>, private val parentBinding: ActivityMainBinding, private val parentFragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _list = list
    private val TAG = this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvItemAssetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = _list[position]
        (holder as ViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return _list.size
    }

    inner class ViewHolder(private val binding: RvItemAssetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(str: String) {
            binding.btnAsset.text = str

            binding.btnAsset.setOnClickListener {
                parentBinding.etAsset.setText(str)
                parentBinding.etAsset.clearFocus()

                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.replace(parentBinding.layoutInputContent.id, InputCategoryFragment.newInstance(parentBinding))
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

                parentBinding.isShowAsset = false

            }



        }
    }

}