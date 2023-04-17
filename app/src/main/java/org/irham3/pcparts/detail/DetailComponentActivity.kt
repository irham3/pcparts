package org.irham3.pcparts.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.irham3.core.domain.model.Component
import org.irham3.pcparts.R
import org.irham3.pcparts.databinding.ActivityDetailComponentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailComponentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailComponentBinding
    private val detailComponentViewModel: DetailComponentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailComponentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailComponent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Component::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }
        showDetailComponent(detailComponent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDetailComponent(detailComponent: Component?) {
        detailComponent?.let {
            supportActionBar?.title = detailComponent.name
            var favoStatus = detailComponent.isFavorite
            setFavoStatus(favoStatus)

            with(binding) {
                content.tvDetailDescription.text = detailComponent.description
                Glide.with(this@DetailComponentActivity)
                    .load(detailComponent.image)
                    .into(ivDetailImage)
                fab.setOnClickListener {
                    favoStatus = !favoStatus
                    detailComponentViewModel.setFavouriteComponent(detailComponent, favoStatus)
                    setFavoStatus(favoStatus)
                }
                btnBuy.setOnClickListener {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(detailComponent.url)
                    startActivity(openURL)
                }
            }

        }
    }

    private fun setFavoStatus(favoStatus: Boolean) {
        if (favoStatus) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}