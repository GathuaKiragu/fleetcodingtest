package com.test.fleettest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.fleettest.BR
import com.test.fleettest.data.adapter.DataAdapter
import com.test.fleettest.data.adapter.LoadMoreAdapter
import com.test.fleettest.databinding.FragmentMainBinding
import com.test.fleettest.viewModel.MainViewModel
import com.test.fleettest.viewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : BaseFragment<FragmentMainBinding>() {

    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        binding.setVariable(BR.viewModel, mainViewModel)
        binding.executePendingBindings()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            adapter = mainViewModel.dataAdapter.withLoadStateFooter(LoadMoreAdapter())
        }


        initObserver()

    }

    private fun initObserver() {

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.repositories.collect {
                    mainViewModel.setAdapterData(it)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            mainViewModel.dataAdapter.loadStateFlow.collect{
                val state = it.refresh
                binding.progressbar.isVisible = state is LoadState.Loading
            }
        }


    }

}