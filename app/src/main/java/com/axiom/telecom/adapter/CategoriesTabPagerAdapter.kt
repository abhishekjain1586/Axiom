package com.axiom.telecom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.view.fragments.BrandFragment

class CategoriesTabPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val categoriesLst = ArrayList<MobileHandsetEntity>()
    fun setData(lst: ArrayList<MobileHandsetEntity>) {
        categoriesLst.clear()
        categoriesLst.addAll(lst)
    }

    override fun getItem(position: Int): Fragment {
        return BrandFragment.newInstance(categoriesLst.get(position).brand!!)
    }

    override fun getCount(): Int {
        return categoriesLst.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = categoriesLst.get(position).brand ?: Constants.EMPTY
        return title
    }

}