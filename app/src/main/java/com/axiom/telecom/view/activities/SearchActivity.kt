package com.axiom.telecom.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.axiom.telecom.R
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.view.fragments.FilterFragment
import com.axiom.telecom.view.fragments.SearchedHandsetsFragment
import com.axiom.telecom.viewmodel.SearchActivityViewModel

class SearchActivity : BaseActivity(), View.OnClickListener {

    private lateinit var ivFilter: ImageView
    private lateinit var tvResetFilter: TextView

    private lateinit var viewModel: SearchActivityViewModel
    private var mSearchedText: String = Constants.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mSearchedText = intent.getStringExtra(Constants.KEY_SEARCHED_TEXT)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        findViewById<TextView>(R.id.tv_title).text = mSearchedText
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initView()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)
        ivFilter = findViewById(R.id.iv_filter)
        tvResetFilter = findViewById(R.id.tv_clear_filter)

        ivFilter.setOnClickListener(this)
        tvResetFilter.setOnClickListener(this)
    }

    private fun initData() {
        addFragment(SearchedHandsetsFragment.newInstance(mSearchedText))

        viewModel.showFilterOption().observe(this, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    ivFilter.visibility = View.VISIBLE
                } else {
                    ivFilter.visibility = View.GONE
                }
            }
        })

        viewModel.getResetFilterOption().observe(this, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    tvResetFilter.visibility = View.VISIBLE
                } else {
                    tvResetFilter.visibility = View.GONE
                }
            }
        })

        viewModel.resetFilterDataObserver().observe(this, object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value) {
                    onBackPressed()
                }
            }
        })
    }

    fun addFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(FilterFragment::class.java.simpleName) != null) {
            return
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.iv_filter -> {
                addFragment(FilterFragment.newInstance(mSearchedText))
            }

            R.id.tv_clear_filter -> {
                viewModel.resetFilterData()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}
