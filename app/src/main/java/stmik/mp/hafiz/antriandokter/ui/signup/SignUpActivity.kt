package stmik.mp.hafiz.antriandokter.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.ActivitySignUpBinding
import stmik.mp.hafiz.antriandokter.ui.signin.SignInActivity
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }

    override fun onResume() {
        super.onResume()
        bindView()
    }

    private fun bindView() {

        binding.etSignupDob.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal")
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener{
                binding.etSignupDob.setText(datePicker.headerText)
            }
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this@SignUpActivity, R.layout.dropdown_item, gender)
        binding.actvSignupGender.setAdapter(arrayAdapter)

        binding.llSignupDaftar.setOnClickListener {
            val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
}