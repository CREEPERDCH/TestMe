package xyz.creeperdch.testme.activity

import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import xyz.creeperdch.testme.R
import xyz.creeperdch.testme.base.BaseActivity
import xyz.creeperdch.testme.fragment.GalleryFragment
import xyz.creeperdch.testme.fragment.HomeFragment
import xyz.creeperdch.testme.fragment.SettingsFragment
import xyz.creeperdch.testme.fragment.UserFragment

class MainActivity : BaseActivity() {

    private var homeFragment: HomeFragment? = null
    private var userFragment: UserFragment? = null
    private var galleryFragment: GalleryFragment? = null
    private var settingsFragment: SettingsFragment? = null

    override fun loadLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        homeFragment = HomeFragment()
        userFragment = UserFragment()
        galleryFragment = GalleryFragment()
        settingsFragment = SettingsFragment()
    }

    override fun initData() {
        showFragment(homeFragment)
    }

    private fun showFragment(fragment: Fragment?) {
        if (null == fragment) return
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).commitAllowingStateLoss()
        }
        hideFragment()
        supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
    }

    private fun hideFragment() {
        val bt = supportFragmentManager.beginTransaction()
        if (homeFragment!!.isAdded && !homeFragment!!.isHidden) {
            bt.hide(homeFragment!!)
        }
        if (userFragment!!.isAdded && !userFragment!!.isHidden) {
            bt.hide(userFragment!!)
        }
        if (galleryFragment!!.isAdded && !galleryFragment!!.isHidden) {
            bt.hide(galleryFragment!!)
        }
        if (settingsFragment!!.isAdded && !settingsFragment!!.isHidden) {
            bt.hide(settingsFragment!!)
        }
        bt.commitAllowingStateLoss()
    }

    override fun initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    showFragment(homeFragment)
                    true
                }
                R.id.user -> {
                    showFragment(userFragment)
                    true
                }
                R.id.image -> {
                    showFragment(galleryFragment)
                    true
                }
                R.id.settings -> {
                    showFragment(settingsFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
