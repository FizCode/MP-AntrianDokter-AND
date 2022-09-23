package stmik.mp.hafiz.antriandokter.ui.signin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.databinding.ActivitySignInBinding
import stmik.mp.hafiz.antriandokter.ui.navbar.MenuActivity
import stmik.mp.hafiz.antriandokter.ui.signup.SignUpActivity

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.etSiginEmail.doAfterTextChanged { viewModel.onChangeEmail(it.toString()) }
        binding.etSiginPassword.doAfterTextChanged { viewModel.onChangePassword(it.toString()) }

        binding.btnSignin.setOnClickListener {
            onValidate()
        }

        binding.llSigninDaftar.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                binding.piSignin.visibility = View.VISIBLE
            } else {
                binding.piSignin.visibility = View.INVISIBLE
            }
        }
        viewModel.shouldOpenMenuPage.observe(this) {
            val intent = Intent(this, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun onValidate() {
        val tvEmail = binding.tvSigninEmail
        val tvPassword = binding.tvSigninPassword

        if (binding.etSiginEmail.text!!.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.etSiginEmail.toString()).matches()) {
            tvEmail.error = "Email tidak valid!"
            tvPassword.error = null
        } else if (binding.etSiginPassword.text!!.isEmpty() && binding.etSiginPassword.text!!.length < 6) {
            tvEmail.error = null
            tvPassword.error = "Password tidak valid!"
        } else {
            tvEmail.error = null
            tvPassword.error = null
            viewModel.onClickSignIn()
        }
    }

}