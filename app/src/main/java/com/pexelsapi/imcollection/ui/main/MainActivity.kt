package com.pexelsapi.imcollection.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pexelsapi.imcollection.databinding.ActivityMainBinding
import com.pexelsapi.imcollection.model.PexelsApiResponse
import com.pexelsapi.imcollection.ui.DetailsActivity
import com.pexelsapi.imcollection.ui.adapter.ImageListAdapter
import com.pexelsapi.imcollection.ui.adapter.PeopleListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var imageListAdapter: ImageListAdapter? = null
    private var peopleListAdapter: PeopleListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        observe()
        initViews()
    }

    private fun observe() = viewModel.run {
        getIsLoading().observe(this@MainActivity) {
            handleLoading(it)
        }
        getPhotos().observe(this@MainActivity) {
            renderList(it)
        }
        getPeople().observe(this@MainActivity) {
            renderPeople(it)
        }
        getEvent().observe(this@MainActivity) {
            it?.getContentIfNotHandled()?.let { event ->
                handleEvent(event)
            }
        }
    }

    private fun initViews() = with(binding) {
        swipeRefresh.setOnRefreshListener { viewModel.getNewPhotos() }

        rvPhotographers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        rvImageList.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun renderList(data: List<PexelsApiResponse.Photos>?) = with(binding) {
        imageListAdapter = ImageListAdapter(this@MainActivity, data) { photos, _ ->
            viewModel.onClickImage(photos)
        }

        rvImageList.adapter = imageListAdapter
    }

    private fun renderPeople(data: List<PexelsApiResponse.Photos>?) = with(binding) {
        peopleListAdapter = PeopleListAdapter(data?.distinct()) { photos, _ ->

        }
        rvPhotographers.adapter = peopleListAdapter
    }

   private fun handleLoading(isLoading: Boolean) = with(binding) {
       vfMain.displayedChild = if (isLoading) VIEW_INDEX_LOADER else {
           swipeRefresh.isRefreshing = false
           VIEW_INDEX_CONTENT
       }
   }

    private fun handleEvent(event: MainState.Event) {
        when (event) {
            is MainState.Event.Error -> Toast.makeText(this, event.error, Toast.LENGTH_SHORT).show()
            is MainState.Event.ShowPhotoDetail -> startActivity(Intent(this, DetailsActivity::class.java).apply {
                putExtra(EXTRA_DATA, event.data)
            })
        }
    }

    companion object {
        private const val VIEW_INDEX_LOADER = 0
        private const val VIEW_INDEX_CONTENT = 1

        const val EXTRA_DATA = "extra_data"
    }
}