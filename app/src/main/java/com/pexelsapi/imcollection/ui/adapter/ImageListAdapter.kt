package com.pexelsapi.imcollection.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pexelsapi.imcollection.R
import com.pexelsapi.imcollection.databinding.ItemListImageBinding
import com.pexelsapi.imcollection.model.PexelsApiResponse


class ImageListAdapter(
    private val context: Context,
    private val photos: List<PexelsApiResponse.Photos>?,
    private val listener: (PexelsApiResponse.Photos?, Int) -> Unit
) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val data = photos?.get(position)
            bind(context, data).apply {
                itemView.setOnClickListener {
                    listener(data, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return photos?.size ?: 0
    }

    class ViewHolder(private var binding: ItemListImageBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceType")
        fun bind(context: Context, photos: PexelsApiResponse.Photos?) = with(binding) {
            Glide.with(context)
                .load(photos?.src?.portrait)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                )
                .into(ivImage)

            cvProfile.setCardBackgroundColor(Color.parseColor(photos?.avgColor))
            tvName.text = photos?.photographer
        }
    }
}