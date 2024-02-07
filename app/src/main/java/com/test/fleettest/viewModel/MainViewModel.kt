package com.test.fleettest.viewModel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.fleettest.data.GithubPagingSource
import com.test.fleettest.data.GithubRepository
import com.test.fleettest.data.adapter.DataAdapter
import com.test.fleettest.data.model.GithubRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val ITEMS_PER_PAGE = 5
class MainViewModel(private val repository: GithubRepository) : ViewModel() {



    //the first DataAdapter is without list
    var dataAdapter: DataAdapter = DataAdapter()

    val repositories: Flow<PagingData<GithubRepositoryModel>> = Pager(PagingConfig(ITEMS_PER_PAGE, enablePlaceholders = false)) {
        GithubPagingSource(repository)
    }.flow
        .cachedIn(viewModelScope)



    fun setAdapterData(data: PagingData<GithubRepositoryModel>) {
        viewModelScope.launch {
            dataAdapter.submitData(data)
        }
    }
}