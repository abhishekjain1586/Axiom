package com.axiom.telecom.view.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.axiom.telecom.R
import com.axiom.telecom.adapter.CategoriesTabPagerAdapter
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.viewmodel.CatalogActivityViewModel
import com.google.android.material.tabs.TabLayout

class CatalogActivity : BaseActivity(), View.OnClickListener {

    private lateinit var ivDrawer: ImageView
    private lateinit var tvNoData: TextView
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var drawer: DrawerLayout
    private lateinit var edtSearch: EditText

    private lateinit var viewModel: CatalogActivityViewModel

    private var tabAdapter: CategoriesTabPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.screen_title_my_store)

        initView()
        initData()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(CatalogActivityViewModel::class.java)
        ivDrawer = findViewById(R.id.iv_drawer)
        drawer = findViewById(R.id.drawer)
        tvNoData = findViewById(R.id.tv_no_data)
        tabs = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        edtSearch = findViewById(R.id.edt_search)

        initListeners()
    }

    private fun initListeners() {
        ivDrawer.setOnClickListener(this)
        edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val searchText = edtSearch.text.toString()
                if (actionId == EditorInfo.IME_ACTION_SEARCH && searchText.trim().length >= 3) {
                    launchSearchScreen()
                    hideKeyboard()
                    return true
                } else {
                    showToastMsg(getString(R.string.search_handset_invalid_characters))
                }
                return false
            }

        })
    }

    private fun initData() {
        viewModel.showLoader().observe(this, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    showProgressDialog()
                } else {
                    dismissProgressDialog()
                }
            }
        })

        viewModel.showError().observe(this, object : Observer<String> {
            override fun onChanged(errMsg: String) {
                updateView(false)
                showToastMsg(errMsg)
            }
        })

        viewModel.getMobileBrandsLst().observe(this, object : Observer<ArrayList<MobileHandsetEntity>> {
            override fun onChanged(lst: ArrayList<MobileHandsetEntity>) {
                updateView(true)
                initAdapter(lst)
            }
        })

    }

    private fun initAdapter(lst: ArrayList<MobileHandsetEntity>) {
        tabAdapter?.let {
            it.setData(lst)
            it.notifyDataSetChanged()
        } ?: run {
            tabAdapter = CategoriesTabPagerAdapter(supportFragmentManager)
            val adapter = tabAdapter!!
            adapter.setData(lst)
            viewPager.adapter = adapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun updateView(hasData: Boolean) {
        if (hasData) {
            tvNoData.visibility = View.GONE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.VISIBLE
            tabs.visibility = View.GONE
            viewPager.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed()
        }

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.iv_drawer -> {
                openCloseDrawr();
            }
        }
    }

    private fun launchSearchScreen() {
        val intent = Intent(this@CatalogActivity, SearchActivity::class.java)
        intent.putExtra(Constants.KEY_SEARCHED_TEXT, edtSearch.text.toString())
        startActivity(intent)
    }

    private fun openCloseDrawr() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
