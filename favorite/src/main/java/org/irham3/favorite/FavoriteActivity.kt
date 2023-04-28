package org.irham3.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.irham3.core.ui.ComponentAdapter
import org.irham3.favorite.databinding.ActivityFavoriteBinding
import org.irham3.favorite.di.favoriteModule
import org.irham3.pcparts.detail.DetailComponentActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
        supportActionBar?.title = getString(org.irham3.core.R.string.title_favorite)

        showFavoriteList()
    }

    private fun showFavoriteList() {
        val componentAdapter = ComponentAdapter()
        componentAdapter.onItemClick = { selectedComponent ->
            val intent = Intent(this, DetailComponentActivity::class.java)
            intent.putExtra(DetailComponentActivity.EXTRA_DATA, selectedComponent)
            startActivity(intent)
        }

        favoriteViewModel.favoriteComponent.observe(this@FavoriteActivity) { component ->
            if (component != null) {
                componentAdapter.onItemClick = { selectedData ->
                    val intent = Intent(this@FavoriteActivity, DetailComponentActivity::class.java)
                    intent.putExtra(DetailComponentActivity.EXTRA_DATA, selectedData)
                    startActivity(intent)
                }
            }
        }

        favoriteViewModel.favoriteComponent.observe(this@FavoriteActivity) { componentData ->
            componentAdapter.setData(componentData)
            binding.viewEmpty.root.visibility = if (componentData.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvComponent) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = componentAdapter
        }
    }
}