package org.irham3.pcparts.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.irham3.pcparts.databinding.ActivityDetailPartBinding

class DetailPartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}