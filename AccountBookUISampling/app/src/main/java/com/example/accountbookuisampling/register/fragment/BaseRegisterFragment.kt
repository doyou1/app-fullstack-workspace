package com.example.accountbookuisampling.register.fragment

import androidx.fragment.app.Fragment

abstract class BaseRegisterFragment: Fragment() {

    open fun changeDateFromChild(value: String) {}
    open fun closeInputLayout() {}

}