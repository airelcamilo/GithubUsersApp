package com.airelcamilo.githubusersapp.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.presentation.model.UserDetailModel
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.githubusersapp.R
import com.airelcamilo.githubusersapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("RedundantWith", "RedundantSuppression")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var username: String? = ""
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        username = intent.getStringExtra(USERNAME)

        detailViewModel.getDetailUser(username ?: "")
        detailViewModel.userDetail.observe(this) { detailUser ->
            if (detailUser != null) {
                when (detailUser) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        detailUser.data?.let { setDetailUser(it) }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username ?: ""
        val viewPager: ViewPager2 = binding.viewPager
        val tabs = binding.tabs
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(detailUser: UserDetailModel) {
        with(binding.itemDetail) {
            Glide.with(view.context)
                .load(detailUser.avatarUrl)
                .into(imgAvatar)
            tvUsername.text = detailUser.login
            tvName.text = detailUser.name
            tvFollowers.text = detailUser.followers.toString()
            tvFollowing.text = detailUser.following.toString()

            val fab = binding.fab
            detailViewModel.getFavoriteUserByUsername(detailUser.login)
                .observe(this@DetailActivity) { user ->
                    if (user.login == "") {
                        fab.setImageResource(R.drawable.baseline_favorite_border_24)
                        fab.setOnClickListener {
                            detailViewModel.setFavorite(
                                UserModel(
                                login = detailUser.login,
                                avatarUrl = detailUser.avatarUrl,
                                isFavorite = true), true)
                        }
                    } else {
                        fab.setImageResource(R.drawable.baseline_favorite_24)
                        fab.setOnClickListener {
                            detailViewModel.setFavorite(user, false)
                        }
                    }
                }

            binding.itemDetail.btnShare.setOnClickListener {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, detailUser.login)
                sharingIntent.putExtra(Intent.EXTRA_TEXT, detailUser.htmlUrl)

                startActivity(Intent.createChooser(sharingIntent, "Share via"))
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding.itemDetail) {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
        const val USERNAME = "username"
    }
}