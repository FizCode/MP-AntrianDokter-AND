package stmik.mp.hafiz.antriandokter.ui.navbar.ui.antre


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.common.ChangeDateFormat.changeDate
import stmik.mp.hafiz.antriandokter.common.ChangeDateFormat.changeDay
import stmik.mp.hafiz.antriandokter.databinding.FragmentAntreBinding
import stmik.mp.hafiz.antriandokter.model.CreateBookingModel
import stmik.mp.hafiz.antriandokter.ui.dialog.CustomDialogFragment


@AndroidEntryPoint
class AntreFragment : Fragment() {

    private val viewModel: AntreViewModel by viewModels()
    private var _binding: FragmentAntreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAntreBinding.inflate(inflater, container, false)

        bindView()
        bindViewModel()
        viewModel.onViewLoaded()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindView() {
        val bpjs = resources.getStringArray(R.array.bpjs)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, bpjs)
        binding.actvAntreBpjs.setAdapter(arrayAdapter)

        binding.btnAntreDaftar.setOnClickListener {
            onValidate()
        }

    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.piAntre.visibility = View.VISIBLE
            } else {
                binding.piAntre.visibility = View.INVISIBLE
            }
        }
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvAntreUserName.text = "Halo, ${it.name}"
        }
        viewModel.shouldShowTicketData.observe(viewLifecycleOwner) {

            with(binding) {
                tvAntreBookingId.text = "Booking ID : ${it.id.toString()}"
                tvAntreQueueNumber.text = "${it.queueNumber.toString()}"
                tvAntrePatientName.text = ": ${it.patientName.toString()}"
                tvAntreDateOfVisit.text = ": ${it.dateOfVisit?.let { it1 -> changeDate(it1) }}"
                tvAntreDayOfVisit.text = ": ${it.dateOfVisit?.let { it1 -> changeDay(it1) }}"
            }
        }
        viewModel.shouldOpenTicket.observe(viewLifecycleOwner) {
            if (it) {
                binding.llAntreTicket.visibility = View.VISIBLE
                binding.svAntreForm.visibility = View.GONE
                binding.btnAntreDaftar.visibility = View.GONE
            } else {
                binding.llAntreTicket.visibility = View.GONE
                binding.svAntreForm.visibility = View.VISIBLE
                binding.btnAntreDaftar.visibility = View.VISIBLE
            }
        }
    }

    private fun onValidate() {
        val tvPatientName = binding.tvAntreName
        val tvNik = binding.tvAntreNik
        val tvBpjs = binding.actvAntreBpjs

        if (binding.etAntreName.text!!.isEmpty()) {
            tvPatientName.error = "Nama Pasien harus diisi."
            tvNik.error = null
            tvBpjs.error = null
        } else if (binding.etAntreNik.text!!.length < 16) {
            tvPatientName.error = null
            tvNik.error = "NIK terdidi dari 16 angka"
            tvBpjs.error = null
        } else if (binding.actvAntreBpjs.text!!.isEmpty()) {
            tvPatientName.error = null
            tvNik.error = null
            tvBpjs.error = "Pilihan BPJS atau bukan harus diisi."
        } else {
            tvPatientName.error = null
            tvNik.error = null
            tvBpjs.error = null
            val request = CreateBookingModel(
                name = binding.etAntreName.text.toString(),
                patientNIK = binding.etAntreNik.text.toString(),
                examinationId = binding.actvAntreBpjs.text.toString()
            )
            onClickDaftar(request)
        }
    }

    private fun onClickDaftar(request: CreateBookingModel) {
        CustomDialogFragment(request).show(parentFragmentManager, null)
    }

}