package com.sandgrains.edu.student.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityMainBinding
import com.sandgrains.edu.student.ui.find.FindFragment
import com.sandgrains.edu.student.ui.home.HomeFragment
import com.sandgrains.edu.student.ui.user.UserFragment
import com.sandgrains.edu.student.utils.setStatusBarColor
import com.sandgrains.edu.student.utils.setStatusBarVisibility

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var homeFragment: HomeFragment
    private lateinit var userFragment: UserFragment
    private lateinit var findFragment: FindFragment
    private val binding: ActivityMainBinding by viewbind()

    //当前显示的fragment
    private lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setStatusBarColor(window,R.color.white)
        setStatusBarVisibility(window, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        initFragment()

        if (savedInstanceState != null) {
            savedInstanceState.apply {
                homeFragment =
                    supportFragmentManager.findFragmentByTag(getString("f1")) as HomeFragment
                userFragment =
                    supportFragmentManager.findFragmentByTag(getString("f2")) as UserFragment
                findFragment =
                    supportFragmentManager.findFragmentByTag(getString("f3")) as FindFragment

                val c = supportFragmentManager.findFragmentByTag(getString("key")) as Fragment
                showFragment(c)
            }

        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.lay_test, homeFragment, HomeFragment::class.simpleName)
                .add(R.id.lay_test, userFragment, UserFragment::class.simpleName)
                .add(R.id.lay_test, findFragment, FindFragment::class.simpleName)
                .show(homeFragment)
                .hide(userFragment)
                .hide(findFragment)
                .commit()

            currentFragment = homeFragment
        }

        with(binding) {
            navigation.itemIconTintList = null
            navigation.isItemHorizontalTranslationEnabled = false
            navigation.setOnNavigationItemSelectedListener(this@MainActivity)
        }



        initBottomNavView(savedInstanceState != null)
    }

    private fun initBottomNavView(b: Boolean) {
        if (b && currentFragment is UserFragment) {
            val user = binding.navigation.menu.findItem(R.id.mi_user)
            user.setIcon(R.drawable.ic_user_full)
        } else if (b && currentFragment is FindFragment) {
            val find = binding.navigation.menu.findItem(R.id.mi_find)
            find.setIcon(R.drawable.ic_scan_fill)
        } else {
            val home = binding.navigation.menu.findItem(R.id.mi_home)
            home.setIcon(R.drawable.ic_home_full)
        }
    }


    private fun initFragment() {
        homeFragment = HomeFragment()
        userFragment = UserFragment()
        findFragment = FindFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        resetToDefaultIcon()
        when (item.itemId) {
            R.id.mi_home -> {
                showFragment(homeFragment)
                item.setIcon(R.drawable.ic_home_full)
            }
            R.id.mi_find -> {
                showFragment(findFragment)
                item.setIcon(R.drawable.ic_scan_fill)
            }
            R.id.mi_user -> {
                showFragment(userFragment)
                item.setIcon(R.drawable.ic_user_full)
            }
        }
        return true
    }

    private fun resetToDefaultIcon() {
        val home = binding.navigation.menu.findItem(R.id.mi_home)
        val find = binding.navigation.menu.findItem(R.id.mi_find)
        val user = binding.navigation.menu.findItem(R.id.mi_user)
        home.setIcon(R.drawable.ic_home)
        find.setIcon(R.drawable.ic_scan_line)
        user.setIcon(R.drawable.ic_user)
    }

    private fun showFragment(fragment: Fragment) {
        var hideFragment1: Fragment = fragment
        var hideFragment2: Fragment = fragment

        when (fragment) {
            is HomeFragment -> {
                hideFragment1 = findFragment
                hideFragment2 = userFragment
            }
            is FindFragment -> {
                hideFragment1 = homeFragment
                hideFragment2 = userFragment
            }
            is UserFragment -> {
                hideFragment1 = findFragment
                hideFragment2 = homeFragment
            }
        }

        supportFragmentManager.beginTransaction()
            .show(fragment)
            .hide(hideFragment1)
            .hide(hideFragment2)
            .commit()

        currentFragment = fragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString("key", currentFragment.javaClass.simpleName)
            putString("f1", homeFragment.javaClass.simpleName)
            putString("f2", userFragment.javaClass.simpleName)
            putString("f3", findFragment.javaClass.simpleName)
        }
    }
}