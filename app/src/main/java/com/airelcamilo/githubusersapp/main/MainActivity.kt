package com.airelcamilo.githubusersapp.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.ui.UserAdapter
import com.airelcamilo.githubusersapp.R
import com.airelcamilo.githubusersapp.databinding.ActivityMainBinding
import com.airelcamilo.githubusersapp.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        mainViewModel.listUsers.observe(this) { users ->
            if (users != null) {
                when (users) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        setUsersData(users.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showDataUnavailable()
                    }
                }
            }
        }

        setSearchBar()
    }

    private fun setSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu1 -> {
                        val uri = Uri.parse("githubusersapp://favorite")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                        true
                    }
                    else -> false
                }
            }
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    val query = if (searchView.text.toString() == "") {
                        "a"
                    } else {
                        searchView.text.toString()
                    }
                    mainViewModel.search(query)
                    false
                }
        }
    }

    private fun setUsersData(users: List<UserModel>?) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.USERNAME, username)
                startActivity(moveIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                rvUsers.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.GONE
                rvUsers.visibility = View.VISIBLE
            }
        }
    }

    private fun showDataUnavailable() {
        binding.progressBar.visibility = View.VISIBLE
    }
}