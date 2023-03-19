package com.mvvm.fanny.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mvvm.fanny.R
import com.mvvm.fanny.databinding.ActivityMainBinding
import com.mvvm.fanny.lib_base.base.BaseViewModelActivity

class MainActivity : BaseViewModelActivity<ActivityMainBinding,TestViewModel>() {
    override fun createViewModel(): TestViewModel = TestViewModel()

    override fun initialize(savedInstanceState: Bundle?) {
        mBinding.viewModel = getActivityViewModel()
        getActivityViewModel().loadData()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

}