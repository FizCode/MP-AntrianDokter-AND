package stmik.mp.hafiz.antriandokter.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.ActivitySplashBinding
import stmik.mp.hafiz.antriandokter.ui.navbar.MenuActivity
import stmik.mp.hafiz.antriandokter.ui.signin.SignInActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                viewModel.onViewLoaded()
            }

        }
        timer.start()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.shouldOpenSignIn.observe(this) {
            if (it) {
                val intent = Intent(this, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        viewModel.shouldOpenMenuPage.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}