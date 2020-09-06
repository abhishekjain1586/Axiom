package com.axiom.telecom.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.adapter.MobileHandsetAdapter
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.utils.Constants
import com.axiom.telecom.viewholder.OnItemClickListener
import com.axiom.telecom.viewmodel.BrandFragmentViewModel

class BrandFragment : BaseFragment(), OnItemClickListener<MobileHandsetEntity> {

    private lateinit var rvHandsets: RecyclerView
    private lateinit var gridLayoutManager : GridLayoutManager
    private lateinit var adapter: MobileHandsetAdapter

    private lateinit var viewModel: BrandFragmentViewModel

    companion object {
        fun newInstance(brandName: String): BrandFragment {
            val bundle = Bundle()
            bundle.putString(Constants.KEY_BRAND_NAME, brandName)
            val frag = BrandFragment()
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseView = inflater.inflate(R.layout.fragment_mobile_handsets, container, false);
        return baseView
    }

    override fun initView() {
        viewModel = ViewModelProviders.of(this).get(BrandFragmentViewModel::class.java)

        rvHandsets = baseView.findViewById(R.id.rv_mobiles)
        gridLayoutManager = GridLayoutManager(requireContext(), 2);
        rvHandsets.layoutManager = gridLayoutManager;

        initData()
    }

    private fun initData() {
        viewModel.getMobileLstByBrand(arguments!!.getString(Constants.KEY_BRAND_NAME, Constants.EMPTY)).observe(this,
            object : Observer<ArrayList<MobileHandsetEntity>> {
                override fun onChanged(lst: ArrayList<MobileHandsetEntity>) {
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

    override fun onItemClick(position: Int, obj: MobileHandsetEntity, actionType: Int) {
        adapter.notifyItemChanged(position, obj)
    }
}