package com.example.vocabularynote.util

import android.app.Activity
import com.devspark.appmsg.AppMsg
import com.example.vocabularynote.R

class AppMsgUtil {

    companion object {
        fun showMsg(text: String, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, text, AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.teal_700))
            appMsg.duration = 500
            appMsg.show()
        }

        fun showErrMsg(text: String, activity: Activity) {
            val appMsg =
                AppMsg.makeText(activity, text, AppMsg.Style(AppMsg.LENGTH_SHORT, com.devspark.appmsg.R.color.alert))
            appMsg.duration = 500
            appMsg.show()
        }
    }
}