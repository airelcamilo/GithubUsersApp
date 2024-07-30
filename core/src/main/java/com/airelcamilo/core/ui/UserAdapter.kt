package com.airelcamilo.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airelcamilo.core.databinding.ItemUserBinding
import com.airelcamilo.core.presentation.model.UserModel
import com.bumptech.glide.Glide

class UserAdapter : ListAdapter<UserModel, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, onItemClickCallback)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel, onItemClickCallback: OnItemClickCallback) {
            with(binding) {
                tvItem.text = user.login
                Glide.with(cardView.context)
                    .load(user.avatarUrl)
                    .into(imgAvatar)
                cardView.setOnClickListener {
                    onItemClickCallback.onItemClicked(user.login)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}