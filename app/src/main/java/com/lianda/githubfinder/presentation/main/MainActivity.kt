package com.lianda.githubfinder.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lianda.githubfinder.R
import com.lianda.githubfinder.domain.model.EndlessUser
import com.lianda.githubfinder.presentation.adapter.UserAdapter
import com.lianda.githubfinder.presentation.viewmodel.UserViewModel
import com.lianda.githubfinder.utils.common.ResultState
import com.lianda.githubfinder.utils.custom.CustomEndlessGridLayoutManager
import com.lianda.githubfinder.utils.extentions.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), UserAdapter.OnLoadMoreListener {
    private val userViewModel: UserViewModel by viewModel()

    private var userAdapter: UserAdapter? = null

    private var isLoadMore = false

    private var currentPage = 1
    private var totalPages = 0

    private var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showUser()

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                onQuerySubmitted(text)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                onQuerySubmitted(newText)
                return false
            }
        })

        searchView.setOnCloseListener {
            onQuerySubmitted()
            return@setOnCloseListener true
        }

    }

    private fun onQuerySubmitted(text: String?=null) {
        userAdapter?.clear()
        if (text.isNullOrEmpty()) {
            currentPage = 1
            totalPages = 1
            userAdapter?.notifyAddOrUpdateChanged(emptyList())
            showEmptyView()
        } else {
            query = text
            observeUser()
        }
    }

    private fun observeUser() {
        observe(
            liveData = userViewModel.getUsers(query,currentPage),
            action = {
                manageGetUserState(it)
            }
        )
    }

    private fun manageGetUserState(result:ResultState<EndlessUser>){
        when (result) {
            is ResultState.Loading -> {
                msvUser.showLoadingView()
                isLoadMore = true
                userAdapter?.setLoadMoreProgress(true)
            }
            is ResultState.Empty -> {
                if (isLoadMore) {
                    isLoadMore = false
                    userAdapter?.setLoadMoreProgress(false)
                    userAdapter?.removeScrollListener()
                } else {
                    userAdapter?.clear()
                    showEmptyView()
                }
            }
            is ResultState.Success -> {
                msvUser.showContentView()
                isLoadMore = false
                userAdapter?.setLoadMoreProgress(false)
                totalPages = result.data.totalPage
                userAdapter?.totalPage = totalPages
                userAdapter?.notifyAddOrUpdateChanged(result.data.user)
            }
            is ResultState.Error -> {
                msvUser.showErrorView(
                    icon = R.drawable.ic_error,
                    message = result.message,
                    action = getString(R.string.action_retry),
                    actionListener = {
                        observeUser()
                    }
                )
            }
        }
    }

    private fun showEmptyView() {
        msvUser.showEmptyView(
            icon = R.drawable.ic_empty,
            message = getString(R.string.label_no_result)
        )
    }

    private fun showUser() {
        if (userAdapter == null) {
            val gridLayoutManager = CustomEndlessGridLayoutManager(this@MainActivity,2)
            userAdapter = UserAdapter(this, mutableListOf())

            userAdapter?.apply {
                page = currentPage
                totalPage = totalPages
                layoutManager = gridLayoutManager
                onLoadMoreListener = this@MainActivity
                recyclerView = rvUser
            }

            rvUser.apply {
                layoutManager = gridLayoutManager
                adapter = userAdapter
            }
        }
    }

    override fun onLoadMore() {
        isLoadMore = true
        userAdapter?.setLoadMoreProgress(true)
        currentPage += 1
        userAdapter?.page = currentPage
        observeUser()
    }
}