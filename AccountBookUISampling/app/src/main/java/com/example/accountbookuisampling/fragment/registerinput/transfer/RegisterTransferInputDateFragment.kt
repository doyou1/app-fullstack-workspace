package com.example.accountbookuisampling.fragment.registerinput.transfer

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.income.IncomeInputDateRVAdapter
import com.example.accountbookuisampling.adapter.recyclerview.registerinput.transfer.TransferInputDateRVAdapter
import com.example.accountbookuisampling.databinding.*
import com.example.accountbookuisampling.dataclass.DateItem
import com.example.accountbookuisampling.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList
import kotlin.math.abs

class RegisterTransferInputDateFragment(
    private val parentBinding: FragmentTransferBinding,
) : BottomSheetDialogFragment(), GestureDetector.OnGestureListener {

    private lateinit var binding: FragmentRegisterDateInputBinding
    private val TAG = this::class.java.simpleName

    // yyyy/MM/DD
    private var date = parentBinding.etDate.text.toString().substring(0, 10)

    private var gestureDetector: GestureDetector? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterDateInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    // config layout full size
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }


    override fun onResume() {
        super.onResume()
        setDateView()
    }

    fun changeDate(value: String) {
        date = value
        parentBinding.etDate.setText(DateUtil.getDateText(date))
        setDateView()
    }

    private fun setDateView() {
        // set title text
        binding.tvTitle.text = TEXT_DATE

        binding.tvMonth.text = "${DateUtil.getMonth(date)}$TEXT_MONTH"
        // set click event
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnLeft.setOnClickListener {
            changeDate(DateUtil.getPrevMonthDate(date))
        }
        binding.btnRight.setOnClickListener {
            changeDate(DateUtil.getNextMonthDate(date))
        }
        binding.btnToday.setOnClickListener {
            changeDate(DateUtil.getTodayText().substring(0, 10))
        }

        binding.recyclerView.layoutManager = getLayoutManager(CALENDAR_VIEW_SPAN_COUNT)

        val dateList = DateUtil.getDateList(date)
        val contentList = ArrayList<DateItem>()
        dateList.forEachIndexed { index, date ->
            contentList.add(
                DateItem(TYPE_CALENDAR_CONTENT, index % 7, date)
            )
        }

        val list = ArrayList<DateItem>()
        list.addAll(DATE_HEAD_LIST)
        list.addAll(contentList)

        binding.recyclerView.adapter =
            TransferInputDateRVAdapter(date, list, parentBinding, this)

        gestureDetector = GestureDetector(this)
        binding.recyclerView.setOnTouchListener { _, motionEvent ->
            gestureDetector?.onTouchEvent(motionEvent)
            true
        }
    }


    private fun getLayoutManager(span: Int): GridLayoutManager {
        // set recyclerview layout like grid view
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), span)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), span)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                layoutManager
            }
            else -> throw NotImplementedError()
        }
    }

    override fun onFling(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        var result = false

        moveEvent?.let {
            downEvent?.let {
                val diffY = moveEvent!!.y - downEvent!!.y
                val diffX = moveEvent!!.x - downEvent!!.x

                if (abs(diffX) > abs(diffY)) {
                    // right or left swipe
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else {
                    // up or down swipe
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
//                            onSwipeBottom()
                        } else {
//                            onSwipeTop()
                        }
                        result = true
                    }
                }
            }
        }

        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        parentBinding.etDate.clearFocus()
    }

    private fun onSwipeLeft() {
        changeDate(DateUtil.getNextMonthDate(date))
    }

    private fun onSwipeRight() {
        changeDate(DateUtil.getPrevMonthDate(date))
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {}
    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {}

}