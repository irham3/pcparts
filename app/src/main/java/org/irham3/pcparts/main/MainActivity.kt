package org.irham3.pcparts.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.irham3.core.data.Resource
import org.irham3.core.ui.ComponentAdapter
import org.irham3.pcparts.R
import org.irham3.pcparts.databinding.ActivityMainBinding
import org.irham3.pcparts.detail.DetailComponentActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = getString(R.string.app_name)
        setContentView(binding.root)

        showComponentList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                val uri = Uri.parse("pcparts://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> true
        }
    }

    private fun showComponentList() {
        val componentAdapter = ComponentAdapter()
        componentAdapter.onItemClick = { selectedComponent ->
            val intent = Intent(this, DetailComponentActivity::class.java)
            intent.putExtra(DetailComponentActivity.EXTRA_DATA, selectedComponent)
            startActivity(intent)
        }

        mainViewModel.component.observe(this@MainActivity) { component ->
            if (component != null) {
                when (component) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        componentAdapter.setData(component.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            component.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }

        with(binding.rvComponent) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = componentAdapter
        }
    }

}