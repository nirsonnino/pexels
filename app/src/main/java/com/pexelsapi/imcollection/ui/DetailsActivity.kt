package com.pexelsapi.imcollection.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pexelsapi.imcollection.R
import com.pexelsapi.imcollection.databinding.ActivityDetailsBinding
import com.pexelsapi.imcollection.model.PexelsApiResponse
import com.pexelsapi.imcollection.ui.main.MainActivity.Companion.EXTRA_DATA
import com.pexelsapi.imcollection.ui.main.MainState
import com.pexelsapi.imcollection.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        initViews()
    }


    private fun initViews() = with(binding) {
        ivBack.setOnClickListener {
            finish()
        }

        getData()?.run {
            Glide.with(this@DetailsActivity)
                .load(src?.portrait)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                )
                .into(ivImage)

            tvAlt.text = alt

            val color = Color.parseColor(avgColor)
            cvProfile.setCardBackgroundColor(color)
            tvName.text = photographer
            tvUrl.text = url

            val topBarDrawable = header.background as GradientDrawable
            topBarDrawable.colors = intArrayOf(color, color, Color.TRANSPARENT)

            header.background = topBarDrawable
        }

    }

    private fun getData() = intent.parcelable<PexelsApiResponse.Photos>(EXTRA_DATA)

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
}