package com.example.englishword4

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.list_activity.*

class ListActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)
        initialize()
    }

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//        setContentView(R.layout.list_activity)
//        initialize()


    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initViewPager2()
        initTabLayout()
    }

    private fun initViewPager2() {
        viewPager2.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(tabLayout, viewPager2) { tab: TabLayout.Tab, position: Int ->
            tab.text = viewPagerAdapter.items[position].language
        }.attach()
    }

    class Item(val fragment: Fragment, val language: String)

    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

        val items: List<Item> = listOf(Item(JapFragment(), "日本語"), Item(EngFragment(), "English"))

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position].fragment
    }
}
