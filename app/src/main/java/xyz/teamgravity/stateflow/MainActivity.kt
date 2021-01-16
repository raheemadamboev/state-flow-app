package xyz.teamgravity.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import xyz.teamgravity.stateflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            // login button
            loginB.setOnClickListener {
                viewModel.login(loginField.text.toString().trim(), passwordField.text.toString().trim())
            }

            // collect data and respond
            lifecycleScope.launchWhenCreated {
                viewModel.loginUIState.collect {
                    when (it) {
                        is MainViewModel.LoginUIState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }

                        is MainViewModel.LoginUIState.Success -> {
                            Snackbar.make(parentLayout, "Successfully logged in", Snackbar.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }

                        is MainViewModel.LoginUIState.Error -> {
                            Snackbar.make(parentLayout, it.message, Snackbar.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}