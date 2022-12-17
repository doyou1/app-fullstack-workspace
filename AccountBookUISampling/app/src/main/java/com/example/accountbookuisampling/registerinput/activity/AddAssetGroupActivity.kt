package com.example.accountbookuisampling.registerinput.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.databinding.ActivityAddAssetGroupBinding
import com.example.accountbookuisampling.registerinput.adapter.AddAssetGroupAdapter
import com.example.accountbookuisampling.util.INIT_ASSET_LIST
import com.example.accountbookuisampling.util.TEXT_ADD
import com.example.accountbookuisampling.util.TEXT_GROUP

class AddAssetGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAssetGroupBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAssetGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
        setRecyclerView()
    }

    private fun setClickEvent() {
        binding.layoutWrap.setOnClickListener {
            cancel()
        }
        // layoutContent, layoutButton's Click Eventが設定されていると
        // layoutContent, layoutButtonをクリックしてもlayoutWrap's Click Eventを呼び出さない
        binding.layoutContent.setOnClickListener {
//            Log.e(TAG, "layoutContent")
        }
        binding.layoutButton.setOnClickListener {
//            Log.e(TAG, "layoutButton")
        }
    }

    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun click(value: String) {

        val intent = Intent()
        intent.putExtra(TEXT_GROUP, value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setRecyclerView() {
        setLayoutManager()
        val list = INIT_ASSET_LIST
        if(list.last().name == TEXT_ADD) {
            list.removeLast()
        }
        binding.recyclerView.adapter = AddAssetGroupAdapter(list, this)
    }

    private fun setLayoutManager() {
        val layoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(this, 1)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LinearLayoutManager(this)
            }
            else -> throw NotImplementedError()
        }

        binding.recyclerView.layoutManager = layoutManager
    }

}