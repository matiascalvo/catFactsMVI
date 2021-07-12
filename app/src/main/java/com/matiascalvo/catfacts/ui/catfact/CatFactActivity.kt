package com.matiascalvo.catfacts.ui.catfact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.matiascalvo.catfacts.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatFactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CatFactViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.observableState.observe(
            this,
            { state ->
                renderState(state)
            }
        )

        binding.getFactButton.setOnClickListener {
            viewModel.dispatch(CatFactAction.GetFactButtonClicked)
        }
    }

    private fun renderState(state: CatFactState) {
        with(state) {
            binding.loadingIndicator.isVisible = loading
            binding.getFactButton.isEnabled = !loading
            binding.errorView.isVisible = displayError
            if (fact.isNotEmpty()) {
                binding.catFactView.text = fact
            }
        }
    }
}
