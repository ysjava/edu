package com.sandgrains.edu.student.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hi.dhl.binding.viewbind
import com.sandgrains.edu.student.R
import com.sandgrains.edu.student.databinding.ActivityMainBinding
import com.sandgrains.edu.student.ui.home.HomeFragment
import com.sandgrains.edu.student.ui.user.UserFragment
import com.sandgrains.edu.student.utils.initWindow

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var homeFragment: HomeFragment
    private lateinit var userFragment: UserFragment
    private val binding: ActivityMainBinding by viewbind()

    //当前显示的fragment
    private lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWindow(window)
        initFragment()

        if (savedInstanceState != null) {
            savedInstanceState.apply {
                homeFragment =
                        supportFragmentManager.findFragmentByTag(getString("f1")) as HomeFragment
                userFragment =
                        supportFragmentManager.findFragmentByTag(getString("f2")) as UserFragment
                val c = supportFragmentManager.findFragmentByTag(getString("key")) as Fragment
                showFragment(c)
            }

        } else {
            supportFragmentManager.beginTransaction()
                    .add(R.id.lay_test, homeFragment, HomeFragment::class.simpleName)
                    .add(R.id.lay_test, userFragment, UserFragment::class.simpleName)
                    .show(homeFragment)
                    .hide(userFragment)
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
        } else {
            val home = binding.navigation.menu.findItem(R.id.mi_home)
            home.setIcon(R.drawable.ic_home_full)
        }
    }


    private fun initFragment() {
        homeFragment = HomeFragment()
        userFragment = UserFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        resetToDefaultIcon()
        when (item.itemId) {
            R.id.mi_home -> {
                showFragment(homeFragment)
                item.setIcon(R.drawable.ic_home_full)
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
        val user = binding.navigation.menu.findItem(R.id.mi_user)
        home.setIcon(R.drawable.ic_home)
        user.setIcon(R.drawable.ic_user)
    }

    private fun showFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
                .show(fragment)
                .hide(if (fragment is HomeFragment) userFragment else homeFragment)
                .commit()

        currentFragment = fragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString("key", currentFragment.javaClass.simpleName)
            putString("f1", homeFragment.javaClass.simpleName)
            putString("f2", userFragment.javaClass.simpleName)
        }
    }
}