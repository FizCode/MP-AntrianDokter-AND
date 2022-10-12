package stmik.mp.hafiz.antriandokter.ui.ticket

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.common.ChangeDateFormat.changeDate
import stmik.mp.hafiz.antriandokter.common.ChangeDateFormat.changeDay
import stmik.mp.hafiz.antriandokter.databinding.ActivityTicketBinding
import stmik.mp.hafiz.antriandokter.ui.navbar.MenuActivity

@AndroidEntryPoint
class TicketActivity : AppCompatActivity() {
    private val viewModel: TicketViewModel by viewModels()
    private lateinit var binding: ActivityTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindView()
        bindViewModel()
        viewModel.onViewLoaded()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backPressed()
    }

    private fun bindView() {
        binding.ivTicketBack.setOnClickListener {
            backPressed()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this@TicketActivity) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowLoading.observe(this@TicketActivity) {
            if (it) {
                binding.piTicket.visibility = View.VISIBLE
            } else {
                binding.piTicket.visibility = View.INVISIBLE
            }
        }
        viewModel.shouldShowTicketData.observe(this@TicketActivity) {
            with(binding) {
                tvTicketBookingId.text = "Booking ID : ${it.id.toString()}"
                tvTicketQueueNumber.text = it.queueNumber.toString()
                tvTicketPatientName.text = ": ${it.patientName.toString()}"
                tvTicketDateOfVisit.text = ": ${it.dateOfVisit?.let { it1 -> changeDate(it1) }}"
                tvTicketDayOfVisit.text = ": ${it.dateOfVisit?.let { it1 -> changeDay(it1) }}"
            }
        }
    }

    private fun backPressed() {
        val intent = Intent(this@TicketActivity, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}