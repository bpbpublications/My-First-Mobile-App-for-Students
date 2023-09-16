package com.example.inspireme.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.inspireme.adapters.FirePostAdapter.FirePostViewHolder
import com.example.inspireme.databinding.PostCardBinding
import com.example.inspireme.models.FirePost

class FirePostAdapter(
    private val onPostClick: (FirePost) -> Unit
) : ListAdapter<FirePost, FirePostViewHolder>(PostDiffCallback()) {

    class FirePostViewHolder(
        private val binding: PostCardBinding
    ) : ViewHolder(binding.root) {
        fun bind(firePost: FirePost, onPostClick: (FirePost) -> Unit) {
            binding.post = firePost
            binding.root.setOnClickListener { onPostClick(firePost) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirePostViewHolder {
        val postCardBinding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirePostViewHolder(postCardBinding)
    }

    override fun onBindViewHolder(holder: FirePostViewHolder, position: Int) {
        val firePost = getItem(position)
        holder.bind(firePost, onPostClick)
    }

    class PostDiffCallback : DiffUtil.ItemCallback<FirePost>() {
        override fun areItemsTheSame(oldItem: FirePost, newItem: FirePost): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: FirePost, newItem: FirePost): Boolean {
            return oldItem == newItem
        }
    }
}