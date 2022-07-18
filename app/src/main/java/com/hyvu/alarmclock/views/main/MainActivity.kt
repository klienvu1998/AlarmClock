package com.hyvu.alarmclock.views.main

import android.os.Bundle
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.findNavController
import com.hyvu.alarmclock.R
import com.hyvu.alarmclock.databinding.ActivityMainBinding
import com.hyvu.alarmclock.models.Alarm
import com.hyvu.alarmclock.models.WeekDays
import com.hyvu.alarmclock.utils.logD
import com.hyvu.alarmclock.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mBinding: ActivityMainBinding
    private val mMainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView() {
        initBottomBar()
        initBottomView()
    }


    private fun initBottomView() {

    }

    private fun initBottomBar() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu)
        val menu = popupMenu.menu
        val navController = findNavController(R.id.mainFragment)
        mBinding.bottomBar.setupWithNavController(menu, navController)
    }

}