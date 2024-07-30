package com.airelcamilo.githubusersapp.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airelcamilo.core.data.Resource
import com.airelcamilo.core.presentation.model.UserModel
import com.airelcamilo.core.ui.UserAdapter
import com.airelcamilo.githubusersapp.databinding.FragmentListUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUserFragment : Fragment() {
    private var _binding: FragmentListUserBinding? = null
    private val listUserViewModel: ListUserViewModel by viewModel()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        val username = arguments?.getString(USERNAME) ?: ""

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvUsers?.layoutManager = layoutManager

        listUserViewModel.getUsers(index, username).observe(viewLifecycleOwner) { users ->
            if (users != null) {
                when (users) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        showLoading(false)
                        users.data?.let { setUsersData(it) }
                    }
                    is Resource.Error -> {
                        showDataUnavailable()
                    }
                }
            }
        }
    }

    private fun setUsersData(users: List<UserModel>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding?.rvUsers?.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(username: String) {
                val moveIntent = Intent(context, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.USERNAME, username)
                startActivity(moveIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                this?.progressBar?.visibility = View.VISIBLE
                this?.rvUsers?.visibility = View.INVISIBLE
            } else {
                this?.progressBar?.visibility = View.GONE
                this?.rvUsers?.visibility = View.VISIBLE
            }
        }
    }

    private fun showDataUnavailable() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME = DetailActivity.USERNAME
    }
}