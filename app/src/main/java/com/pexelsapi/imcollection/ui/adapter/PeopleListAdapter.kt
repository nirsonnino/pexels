package com.pexelsapi.imcollection.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pexelsapi.imcollection.databinding.ItemListPeopleBinding
import com.pexelsapi.imcollection.model.PexelsApiResponse


class PeopleListAdapter(
    private val photos: List<PexelsApiResponse.Photos>?,
    private val listener: (PexelsApiResponse.Photos?, Int) -> Unit
) : RecyclerView.Adapter<PeopleListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val data = photos?.get(position)
            bind(data).apply {
                itemView.setOnClickListener {
                    listener(data, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return photos?.size ?: 0
    }

    class ViewHolder(private var binding: ItemListPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photos: PexelsApiResponse.Photos?) = with(binding) {
            tvName.text = photos?.photographer
        }
    }
}