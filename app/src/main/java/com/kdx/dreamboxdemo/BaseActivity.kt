package com.kdx.dreamboxdemo

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Base Activity with databinding support
 */
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    internal lateinit var bindView: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = DataBindingUtil.setContentView(this,getLayoutID())
        initView()
    }

    @LayoutRes
    abstract fun getLayoutID(): Int

    abstract fun initView()
}
