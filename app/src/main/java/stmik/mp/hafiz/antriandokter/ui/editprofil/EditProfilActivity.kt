package stmik.mp.hafiz.antriandokter.ui.editprofil

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.ActivityEditProfilBinding

@AndroidEntryPoint
class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private val viewModel: EditProfilViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.transparent)

        viewModel.onViewLoaded()
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.ivEditProfilBack.setOnClickListener {
            onBackPressed()
        }

        binding.etEditProfilDob.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("PILIH TANGGAL")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener{
                binding.etEditProfilDob.setText(datePicker.headerText)
            }
        }

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(this@EditProfilActivity, R.layout.dropdown_item, gender)
        binding.actvEditProfilGender.setAdapter(arrayAdapter)

        with(binding) {
            etEditProfilName.doAfterTextChanged { viewModel.onChangeName(it.toString()) }
            etEditProfilNik.doAfterTextChanged { viewModel.onChangeNIK(it.toString()) }
            etEditProfilDob.doAfterTextChanged { viewModel.onChangeDoB(it.toString()) }
            etEditProfilAlamat.doAfterTextChanged { viewModel.onChangeAddress(it.toString()) }
            actvEditProfilGender.doAfterTextChanged { viewModel.onChangeGender(it.toString()) }
        }

        binding.btnEditProfil.setOnClickListener {
            onValidate()
        }

    }

    private fun bindViewModel() {
        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                binding.piEditProfil.visibility = View.VISIBLE
            } else {
                binding.piEditProfil.visibility = View.INVISIBLE
            }
        }

        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowSuccess.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        viewModel.shouldShowProfile.observe(this) {
            with(binding) {
                etEditProfilName.setText(it.name)
                etEditProfilNik.setText(it.NIK)
                etEditProfilEmail.setText(it.email)
                etEditProfilDob.setText(it.dateOfBirth)
                etEditProfilAlamat.setText(it.address)
                actvEditProfilGender.setText(it.gender)
            }
        }
    }

    private fun onValidate() {
        val tvName = binding.tvEditProfilName
        val tvNik = binding.tvEditProfilNik
        val tvDob = binding.tvEditProfilDob
        val tvAddress = binding.tvEditProfilAlamat
        val tvGender = binding.tvEditProfilGender

        if (binding.etEditProfilName.text!!.isEmpty()) {
            tvName.error = "Nama tidak boleh kosong!"
            tvNik.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
        } else if (binding.etEditProfilNik.text!!.length < 16) {
            tvName.error = null
            tvNik.error = "NIK terdiri dari 16 angka"
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
        } else if (binding.etEditProfilDob.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvDob.error = "Tanggal lahir harus diisi!"
            tvAddress.error = null
            tvGender.error = null
        } else if (binding.etEditProfilAlamat.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvDob.error = null
            tvAddress.error = "Alamat harud diisi!"
            tvGender.error = null
        } else if (binding.actvEditProfilGender.text!!.isEmpty()) {
            tvName.error = null
            tvNik.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = "Jenis kelamin harus dipilih!"
        } else {
            tvName.error = null
            tvNik.error = null
            tvDob.error = null
            tvAddress.error = null
            tvGender.error = null
            viewModel.onClickUpdate()
        }
    }
}