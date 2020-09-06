package com.axiom.telecom.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.adapter.FilterContentAdapter
import com.axiom.telecom.adapter.FilterTypeAdapter
import com.axiom.telecom.model.FilterTypeBean
import com.axiom.telecom.model.FilterTypeContentBean
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.view.activities.SearchActivity
import com.axiom.telecom.viewholder.OnItemClickListener
import com.axiom.telecom.viewmodel.SearchActivityViewModel

class FilterFragment : BaseFragment() {

    private lateinit var viewModel: SearchActivityViewModel
    private lateinit var mRvFilter: RecyclerView
    private lateinit var mRvFilterContent: RecyclerView
    private lateinit var mTvFilterByTitle: TextView

    private lateinit var mAdapter: FilterTypeAdapter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var mAdapterContentFilter: FilterContentAdapter
    private lateinit var linearLayoutManagerContent : LinearLayoutManager

    companion object {
        fun newInstance(searchedText: String): FilterFragment {
            val bundle = Bundle()
            bundle.putString(Constants.KEY_SEARCHED_TEXT, searchedText)
            val frag = FilterFragment()
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_filter, container, false);
        return baseView
    }

    override fun initView() {
        viewModel = ViewModelProviders.of(requireActivity()).get(SearchActivityViewModel::class.java)

        mTvFilterByTitle = baseView.findViewById(R.id.tv_filter_by_title)
        mRvFilter = baseView.findViewById(R.id.rv_filter)
        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRvFilter.layoutManager = linearLayoutManager

        mRvFilterContent = baseView.findViewById(R.id.rv_filter_content)
        linearLayoutManagerContent = LinearLayoutManager(requireContext())
        linearLayoutManagerContent.orientation = LinearLayoutManager.VERTICAL
        mRvFilterContent.layoutManager = linearLayoutManagerContent

        initData()
    }

    private fun initData() {
        viewModel.getFilterDataForSearchedText(arguments!!.getString(Constants.KEY_SEARCHED_TEXT, Constants.EMPTY)).observe(viewLifecycleOwner, object : Observer<ArrayList<FilterTypeBean>> {
            override fun onChanged(lst: ArrayList<FilterTypeBean>) {
                initAdapter(lst)
            }
        })
    }

    private fun initAdapter(typeLst: ArrayList<FilterTypeBean>) {
        // Setting Filter Type adapter
        mAdapter = FilterTypeAdapter(requireContext())
        mAdapter.setData(typeLst)
        mAdapter.setListener(object : OnItemClickListener<FilterTypeBean> {
            override fun onItemClick(position: Int, obj: FilterTypeBean, actionType: Int) {
                mTvFilterByTitle.text = requireContext().getString(R.string.text_filter_by) + " " + obj.typeName
                mTvFilterByTitle.visibility = View.VISIBLE
                notifyContentAdapter(obj.entityList)
            }

        })
        mRvFilter.adapter = mAdapter


        // Setting Filter Content adapter
        mAdapterContentFilter = FilterContentAdapter(requireContext())
        mAdapterContentFilter.setData(ArrayList())
        mAdapterContentFilter.setListener(object : OnItemClickListener<FilterTypeContentBean> {
            override fun onItemClick(position: Int, obj: FilterTypeContentBean, actionType: Int) {
                obj.isSelected = !obj.isSelected
                mAdapterContentFilter.notifyItemChanged(position, obj)
            }

        })
        mRvFilterContent.adapter = mAdapterContentFilter
    }

    private fun notifyContentAdapter(lst: ArrayList<FilterTypeContentBean>) {
        mAdapterContentFilter.setData(lst)
        mAdapterContentFilter.notifyDataSetChanged()
    }

}