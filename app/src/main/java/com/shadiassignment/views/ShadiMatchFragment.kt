package com.shadiassignment.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mymvvmdemo.room.ShadiMatchDatabase
import com.shadiassignment.R
import com.shadiassignment.adapter.ShadiMatchAdapter
//import com.shadiassignment.adapter.ShadiMatchAdapter
import com.shadiassignment.base.BaseFragment
import com.shadiassignment.databinding.FragmentShadiBinding
import com.shadiassignment.extensions.isNetworkAvailable
import com.shadiassignment.models.ShadiMatchDBModel
import com.shadiassignment.models.ShadiMatchRequestModel
import com.shadiassignment.utils.ShadiEvent
import com.shadiassignment.viewmodel.ShadiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShadiMatchFragment : BaseFragment() {
    private var fragmentShadiBinding: FragmentShadiBinding? = null
    private val viewModel: ShadiViewModel by viewModels()
    lateinit var usersList: List<ShadiMatchDBModel>
    private var shadiMatchAdapter: ShadiMatchAdapter? = null

    companion object {
        fun newInstance() = ShadiMatchFragment()
    }

    override fun setUpToolbar() {
        updateToolbar(getString(R.string.shadi_screen_title))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShadiBinding = FragmentShadiBinding.inflate(inflater, container, false)
        return fragmentShadiBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        observeUserList()
        fetchList()
    }

    private fun observeUserList() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is ShadiEvent.Loading -> {
                        fragmentShadiBinding?.progressBar?.visibility = View.VISIBLE
                    }
                    is ShadiEvent.Failure -> {
                        fragmentShadiBinding?.progressBar?.visibility = View.GONE
                        Toast.makeText(requireContext(), it.errorText, Toast.LENGTH_LONG).show()
                    }
                    is ShadiEvent.Success -> {
                        fragmentShadiBinding?.progressBar?.visibility = View.GONE
                        usersList = it.usersList
                        initialization()
                    }
                }
            }
        }

    }

    private var acceptDeclineEvent: (shadiMatchDbModel: ShadiMatchDBModel) -> Unit =
        { shadiDbModel ->
            lifecycleScope.launch {
                activity?.let { ShadiMatchDatabase.get(it?.application) }?.let {
                    it.getShadiMatchDao().update(shadiDbModel)
                    fragmentShadiBinding?.shadiMatchRecycler?.adapter?.notifyDataSetChanged()
                }
            }
        }

    private fun fetchList() {
        activity?.let { ShadiMatchDatabase.get(it?.application) }?.let {
            viewModel.getShadiMatchList(
                it, requireContext().isNetworkAvailable(), ShadiMatchRequestModel(results = 10)
            )
        }
    }

    private fun initialization() {
        Log.e("usersList", "${usersList.size}")
        shadiMatchAdapter = ShadiMatchAdapter(usersList, requireContext())
        shadiMatchAdapter?.acceptDeclineClickListener = acceptDeclineEvent
        fragmentShadiBinding?.shadiMatchRecycler?.adapter = shadiMatchAdapter
    }
}