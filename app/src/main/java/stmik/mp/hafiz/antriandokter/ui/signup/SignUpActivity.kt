package stmik.mp.hafiz.antriandokter.ui.signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.ActivitySignUpBinding
import stmik.mp.hafiz.antriandokter.ui.signin.SignInActivity
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.etSignupDob.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("PILIH TANGGAL")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener{
                binding.etSignupDob.setText(datePicker.headerText)
            }
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this@SignUpActivity, R.layout.dropdown_item, gender)
        binding.actvSignupGender.setAdapter(arrayAdapter)

        with(binding) {
            etSigupName.doAfterTextChanged { viewModel.onChangeName(it.toString()) }
            etSigupNik.doAfterTextChanged { viewModel.onChangeNIK(it.toString()) }
            etSigupEmail.doAfterTextChanged { viewModel.onChangeEmail(it.toString()) }
            etSignupDob.doAfterTextChanged { viewModel.onChangeDoB(it.toString()) }
            etSignupDob.doAfterTextChanged { viewModel.onChangeDoB(it.toString()) }
            etSigupAlamat.doAfterTextChanged { viewModel.onChangeAddress(it.toString()) }
            actvSignupGender.doAfterTextChanged { viewModel.onChangeGender(it.toString()) }
            actvSignupGender.doAfterTextChanged { viewModel.onChangeGender(it.toString()) }
            etSigupPassword.doAfterTextChanged { viewModel.onChangePassword(it.toString()) }
        }

        binding.btnSignup.setOnClickListener {
            onValidate()
        }

        binding.llSignupDaftar.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
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
                binding.piSignup.visibility = View.VISIBLE
            } else {
                binding.piSignup.visibility = View.INVISIBLE
            }
        }
        viewModel.shouldOpenSignIn.observe(this) {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun onValidate() {
        val tvName = binding.tvSignupName
        val tvNik = binding.tvSignupNik
        val tvEmail = binding.tvSignupEmail
        val tvDob = binding.tvSignupDob
        val tvAddress = binding.tvSignupAlamat
        val tvGender = binding.tvSignupGender
        val tvPassword = binding.tvSignupPassword

        if (binding.etSigupName.text!!.isEmpty()) {
            tvName.error = "Nama tidak boleh kosong!"
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = null
        } else if (binding.etSigupNik.text!!.length < 12) {
            tvName.error = null
            tvNik.error = "NIK terdiri dari 12 angka"
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = null
        } else if (binding.etSigupEmail.text!!.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(binding.etSigupEmail.text.toString()).matches()) {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = "Email tidak valid!"
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = null
        } else if (binding.etSignupDob.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = "Tanggal lahir harus diisi!"
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = null
        } else if (binding.etSigupAlamat.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = "Alamat harud diisi!"
            tvGender.error = null
            tvPassword.error = null
        } else if (binding.actvSignupGender.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = "Jenis kelamin harus dipilih!"
            tvPassword.error = null
        } else if (binding.etSigupPassword.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = "Password tidak boleh kosong!"
        } else {
            tvName.error = null
            tvNik.error = null
            tvEmail.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            tvPassword.error = null
            viewModel.onClickSignUp()
        }
    }
}