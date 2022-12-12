package com.example.accountbookuisampling.registerinput.fragment

import androidx.fragment.app.Fragment

abstract class BaseRegisterInputFragment: Fragment() {

    open fun changeDate(value: String) {}
    open fun changeInputText(value: String) {}
    open fun openAddTextInputActivityResultLauncher() {}

}