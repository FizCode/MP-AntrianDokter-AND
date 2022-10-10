package stmik.mp.hafiz.antriandokter.ui.navbar.ui.antre


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.data.api.queue.CreateBookingRequest
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
        // viewModel.onViewLoaded()
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

        /*with(binding) {
            etAntreName.doAfterTextChanged { viewModel.onChangePatientName(it.toString()) }
            etAntreNik.doAfterTextChanged { viewModel.onChangeNIK(it.toString()) }
            actvAntreBpjs.doAfterTextChanged {
                if (it.toString() == "BPJS") {
                    viewModel.onChangeBpjs(1)
                } else {
                    viewModel.onChangeBpjs(2)
                }
            }
        }*/
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvAntreUserName.text = "Halo, ${it.name}"
            val userId = it.id

        }
        viewModel.shouldShowTicketData.observe(viewLifecycleOwner) {
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