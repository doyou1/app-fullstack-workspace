package com.example.accountbookuisampling.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.income.IncomeInputTextRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.select.SelectRepeatRVAdapter
import com.example.accountbookuisampling.databinding.ActivityAddAssetTextInputBinding
import com.example.accountbookuisampling.databinding.ActivityAddCategoryTextInputBinding
import com.example.accountbookuisampling.databinding.ActivitySelectRepeatBinding
import com.example.accountbookuisampling.util.FLAG_ASSET
import com.example.accountbookuisampling.util.FLAG_CATEGORY
import com.example.accountbookuisampling.util.INPUT_ITEM_VIEW_SPAN_COUNT
import com.example.accountbookuisampling.util.REPEAT_ITEM_LIST

class SelectRepeatActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectRepeatBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectRepeatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setRecyclerView()
    }

    private fun setClickEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
//            val intent = Intent()
//            intent.putExtra("item", "추가1")
//            setResult(Activity.RESULT_OK, intent)
//            finish()
    }

    private fun setRecyclerView() {
        // set recycler view
        binding.recyclerView.layoutManager = getLayoutManager(1)
        binding.recyclerView.adapter = SelectRepeatRVAdapter(REPEAT_ITEM_LIST, this)

    }

    private fun getLayoutManager(span: Int): GridLayoutManager {
        // set recyclerview layout like grid view
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(this, span)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(this, span)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
    }
}