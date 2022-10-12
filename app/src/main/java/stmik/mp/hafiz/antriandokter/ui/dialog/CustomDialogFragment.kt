package stmik.mp.hafiz.antriandokter.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.databinding.FragmentCustomDialogBinding
import stmik.mp.hafiz.antriandokter.model.CreateBookingModel
import stmik.mp.hafiz.antriandokter.ui.navbar.ui.antre.AntreViewModel
import stmik.mp.hafiz.antriandokter.ui.ticket.TicketActivity
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CustomDialogFragment(
    var createBookingModel: CreateBookingModel
) : DialogFragment() {

    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AntreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCustomDialogBinding.inflate(inflater, container, false)
        bindView()
        bindViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun bindView() {
        val patientName = createBookingModel.name
        val patientNIK = createBookingModel.patientNIK
        val BPJS = createBookingModel.examinationId

        viewModel.onChangePatientName(patientName.toString())
        viewModel.onChangeNIK(patientNIK.toString())
        if (BPJS == "BPJS") {
            viewModel.onChangeBpjs(1)
        } else {
            viewModel.onChangeBpjs(2)
        }

        val indonesia = Locale("in", "ID")
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", indonesia)
        val dayFormat = SimpleDateFormat("EEEE", indonesia)
        val date = dateFormat.format(Date())
        val day = dayFormat.format(Date())

        binding.tvDialogPatientName.text = ": $patientName"
        binding.tvDialogDate.text = ": $date"
        binding.tvDialogDay.text = ": $day"
        binding.btnDialogPositive.setOnClickListener {
            viewModel.onClickDaftar()
            // dialog?.dismiss()
        }
        binding.btnDialogNegative.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldOpenTicketPage.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), TicketActivity::class.java)
            startActivity(intent)
        }
    }

}