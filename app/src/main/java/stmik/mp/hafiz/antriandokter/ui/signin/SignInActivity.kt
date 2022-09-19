package stmik.mp.hafiz.antriandokter.ui.signin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import stmik.mp.hafiz.antriandokter.databinding.ActivitySignInBinding
import stmik.mp.hafiz.antriandokter.ui.signup.SignUpActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()

    }

    private fun bindView() {
        binding.llSigninDaftar.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}