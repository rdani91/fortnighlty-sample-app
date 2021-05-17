package com.rdani.fortnightly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected var binding: VB? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract fun initView()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}