package com.example.paginationflowremote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paginationflowremote.Api.ResultRickAndMorty
import com.example.paginationflowremote.databinding.CustomLayoutBinding


class CharacterAdapter : PagingDataAdapter<ResultRickAndMorty, CharacterAdapter.CharacterViewHolder>(
    MyUtil()
) {

    class CharacterViewHolder (private val binding: CustomLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultRickAndMorty) {
            item.let{
                binding.characterName.text = it.name

                Glide.with(binding.root)
                    .load(it.image)
                    .into(binding.characterImage)
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CustomLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }


    class MyUtil : DiffUtil.ItemCallback<ResultRickAndMorty>() {
        override fun areItemsTheSame(
            oldItem: ResultRickAndMorty,
            newItem: ResultRickAndMorty
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultRickAndMorty,
            newItem: ResultRickAndMorty
        ): Boolean {
            return oldItem == newItem
        }
    }
}