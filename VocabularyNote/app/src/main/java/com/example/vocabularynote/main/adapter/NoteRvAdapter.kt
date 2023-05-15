package com.example.vocabularynote.main.adapter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularynote.BaseApplication
import com.example.vocabularynote.R
import com.example.vocabularynote.databinding.RvItemNoteBinding
import com.example.vocabularynote.room.entity.Note
import com.example.vocabularynote.room.entity.NoteItem
import com.example.vocabularynote.util.AppMsgUtil
import com.example.vocabularynote.util.Const
import com.example.vocabularynote.util.Const.TEXT_CANCEL
import com.example.vocabularynote.util.Const.TEXT_DELETE
import com.example.vocabularynote.util.Const.TEXT_EDIT
import com.example.vocabularynote.util.Const.TEXT_EXPORT
import com.example.vocabularynote.util.Const.TEXT_KEY
import com.example.vocabularynote.util.Const.TEXT_NO
import com.example.vocabularynote.util.Const.TEXT_NOTE_ID
import com.example.vocabularynote.util.Const.TEXT_RESULT
import com.example.vocabularynote.util.Const.TEXT_VALUE
import com.example.vocabularynote.util.Const.TEXT_YES
import com.example.vocabularynote.util.DateUtil
import kotlinx.coroutines.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream


class NoteRvAdapter(private val _list: List<Note>, private val parentViewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    private val list = _list
    private var _context: Context? = null
    private val context get() = _context!!
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 100
    private val CREATE_FILE = 100

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        _context = parent.context
        return NoteRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteRvViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteRvViewHolder(private val binding: RvItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root), ActivityCompat.OnRequestPermissionsResultCallback {

        fun bind(item: Note) {
            binding.item = item
            setClickEvent(item)
        }

        private fun setClickEvent(item: Note) {
            binding.layoutWrap.setOnClickListener {
                when (parentViewType) {
                    Const.TYPE_EDIT -> {
                        val bundle = Bundle()
                        bundle.putLong(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_edit_to_edit_detail, bundle)
                    }
                    Const.TYPE_GAME -> {
                        val bundle = Bundle()
                        bundle.putLong(TEXT_NOTE_ID, item.id)
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_game_to_game_detail, bundle)
                    }
                }
            }

            binding.btnEdit.setOnClickListener {
                val popup = PopupMenu(context, binding.btnEdit)
                when (parentViewType) {
                    Const.TYPE_EDIT -> {
                        popup.menu.add(TEXT_EDIT)
                        popup.menu.add(TEXT_DELETE)
                        popup.menu.add(TEXT_EXPORT)
                        popup.menu.add(TEXT_CANCEL)
                    }
                    Const.TYPE_GAME -> {
                        popup.menu.add(TEXT_EDIT)
                        popup.menu.add(TEXT_CANCEL)
                    }
                }
                popup.setOnMenuItemClickListener {
                    when (it.title) {
                        TEXT_EDIT -> {
                            val bundle = Bundle()
                            bundle.putLong(TEXT_NOTE_ID, item.id)
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_edit_to_add, bundle)
                        }
                        TEXT_DELETE -> {
                            showDeletePrompt(item)
                        }
                        TEXT_EXPORT -> {
//                            AppMsgUtil.showMsg("please write to export note", (context as Activity))
//                            if (!isExecuteExternalStoragePermission()) {
//                            selectDirectory()
//                            }
                            // android 13 above
                            // android 13 under
                            // android 11 under
                        }
                        TEXT_CANCEL -> {
                            popup.dismiss()
                        }
                    }
                    false
                }
                popup.show()
            }
        }

        private fun showDeletePrompt(item: Note) {
            val builder = AlertDialog.Builder(context)
            val alert = builder
                .setMessage("Do you want to DELETE \"${item.title}\" Note? \n※If deleted, it cannot be reversed.")
                .setCancelable(true)
                .setPositiveButton(TEXT_YES) { _, _ ->
                    GlobalScope.launch(Dispatchers.IO) {
                        ((context as Activity).application as BaseApplication).noteDao.deleteNote(
                            item
                        )
                        GlobalScope.launch(Dispatchers.Main) {
                            AppMsgUtil.showMsg(
                                Const.TEXT_DELETE_NOTE_SUCCESS,
                                (context as Activity)
                            )
                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_refresh_edit)
                        }
                    }

                }
                .setNegativeButton(TEXT_NO) { _, _ ->
                }.create()
            alert.setTitle("Check to delete note")
            alert.show()
        }

        private fun selectDirectory() {
//            val folder = (context as Activity).getExternalFilesDir("JH")
//            if(folder != null) {

            Log.e(TAG, "${binding.item?.id} ${binding.item?.title}")
            binding.item?.id?.let { id ->
                binding.item?.title?.let { title ->
                    processSaveNote(id, title)
                }
            }
//            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private fun processSaveNote(id: Long, title: String) {
            GlobalScope.launch(Dispatchers.IO) {
                val list =
                    ((context as Activity).application as BaseApplication).noteDao.getNoteItemAllByNoteId(
                        id
                    )
                val workbook = writeWorkBook(list)
                val fileName = "$title-${DateUtil.getCurrentTime()}.xlsx"
                val file = File(fileName)
                if (!file.exists()) {
                    file.createNewFile()
                }

                val fout = FileOutputStream(file)
                try {
                    workbook.write(fout)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    fout.flush()
                    fout.close()
                }
            }
        }

        private fun writeWorkBook(list: List<NoteItem>): XSSFWorkbook {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet(TEXT_RESULT)
            var rowIdx = 0
            val row = sheet.createRow(rowIdx++)
            val keyCellIdx = 0
            val valueCellIdx = 1
            val keyCell = row.createCell(keyCellIdx)
            val valueCell = row.createCell(valueCellIdx)
            keyCell.setCellValue(TEXT_KEY)
            valueCell.setCellValue(TEXT_VALUE)
            for (item in list) {
                val itemRow = sheet.createRow(rowIdx++)
                val itemKeyCell = itemRow.createCell(keyCellIdx)
                val itemValueCell = itemRow.createCell(valueCellIdx)
                itemKeyCell.setCellValue(item.key)
                itemValueCell.setCellValue(item.value)
            }
            return workbook
        }

        private fun isExecuteExternalStoragePermission(): Boolean {
            return if (SDK_INT >= Build.VERSION_CODES.R) {
                Log.e(
                    TAG,
                    "Environment.isExternalStorageManager(): ${Environment.isExternalStorageManager()}"
                )
                if (!Environment.isExternalStorageManager()) {
                    Log.e(TAG, "requestPermission")
                    (context as Activity).requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                        ), EXTERNAL_STORAGE_PERMISSION_CODE
                    ) //permission request code is just an int
                    Log.e(TAG, "requestPermission end")

                    true
                } else true
            } else {
                if ((context as Activity).checkSelfPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || (context as Activity).checkSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    (context as Activity).requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        EXTERNAL_STORAGE_PERMISSION_CODE
                    )
                    true
                } else false
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
                if (SDK_INT > Build.VERSION_CODES.S_V2) {
                    selectDirectory()
                } else if (SDK_INT >= Build.VERSION_CODES.R && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    selectDirectory()
                }
            }
        }
    }
}