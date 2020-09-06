package com.axiom.telecom.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.adapter.MobileHandsetAdapter
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.viewholder.OnItemClickListener
import com.axiom.telecom.viewmodel.SearchActivityViewModel

class SearchedHandsetsFragment : BaseFragment(), OnItemClickListener<MobileHandsetEntity> {

    private lateinit var rvHandsets: RecyclerView
    private lateinit var tvNoData: TextView

    private lateinit var viewModel: SearchActivityViewModel
    private lateinit var gridLayoutManager : GridLayoutManager
    private lateinit var adapter: MobileHandsetAdapter

    companion object {
        fun newInstance(searchedText: String): SearchedHandsetsFragment {
            val bundle = Bundle()
            bundle.putString(Constants.KEY_SEARCHED_TEXT, searchedText)
            val frag = SearchedHandsetsFragment()
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_searched_handsets, container, false);
        return baseView
    }

    override fun initView() {
        viewModel = ViewModelProviders.of(requireActivity()).get(SearchActivityViewModel::class.java)

        rvHandsets = baseView.findViewById(R.id.rv_mobiles)
        tvNoData = baseView.findViewById(R.id.tv_no_data)
        gridLayoutManager = GridLayoutManager(requireContext(), 2);
        rvHandsets.layoutManager = gridLayoutManager

        initData()
    }

    private fun initData() {
        viewModel.showError().observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(errMsg: String) {
                updateView(false)
            }
        })

        viewModel.getSearchedHandsets(arguments!!.getString(Constants.KEY_SEARCHED_TEXT, Constants.EMPTY)).observe(viewLifecycleOwner,
            object : Observer<ArrayList<MobileHandsetEntity>> {
                override fun onChanged(lst: ArrayList<MobileHandsetEntity>) {
                    updateView(true)
                    initAdapter(lst)
                }
            })
    }

    private fun initAdapter(lst: ArrayList<MobileHandsetEntity>) {
        adapter = MobileHandsetAdapter(requireContext())
        adapter.setData(lst)
        adapter.setListener(this)
        rvHandsets.adapter = adapter
    }

    private fun updateView(hasData: Boolean) {
        if (hasData) {
            tvNoData.visibility = View.GONE
            rvHandsets.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.VISIBLE
            rvHandsets.visibility = View.GONE
        }
    }

    override fun onItemClick(position: Int, obj: MobileHandsetEntity, actionType: Int) {
        adapter.notifyItemChanged(position, obj)
    }
}