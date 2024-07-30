package com.airelcamilo.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.ui.UserAdapter
import com.airelcamilo.favorite.databinding.ActivityFavoriteBinding
import com.airelcamilo.favorite.di.favoriteModule
import com.airelcamilo.githubusersapp.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        loadKoinModules(favoriteModule)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvUsers?.layoutManager = layoutManager

        favoriteViewModel.getAllFavorites().observe(this) { favorites ->
            if (favorites != null) {
                setUsersData(favorites)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun setUsersData(favorites: List<UserModel>) {
        showDataUnavailable(favorites.isEmpty())
        val adapter = UserAdapter()
        adapter.submitList(favorites)
        binding?.rvUsers?.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.USERNAME, username)
                startActivity(moveIntent)
            }
        })
    }

    private fun showDataUnavailable(isDataUnavailable: Boolean) {
        with(binding) {
            if (isDataUnavailable) {
                this?.tvIsEmpty?.visibility = View.VISIBLE
            } else {
                this?.tvIsEmpty?.visibility = View.GONE
            }
        }
    }
}