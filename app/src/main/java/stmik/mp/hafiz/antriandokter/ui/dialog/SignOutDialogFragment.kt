package stmik.mp.hafiz.antriandokter.ui.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.databinding.FragmentCustomDialogBinding
import stmik.mp.hafiz.antriandokter.ui.navbar.ui.profil.ProfilViewModel
import stmik.mp.hafiz.antriandokter.ui.signin.SignInActivity

@AndroidEntryPoint
class SignOutDialogFragment: DialogFragment() {
    private var _binding: FragmentCustomDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfilViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCustomDialogBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
        with(binding) {
            tvDialogTitle.text = "Keluar Aplikasi"
            tvDialogConfirmation.text = "Apakah Anda yakin ingin keluar aplikasi?"
            tvDialogError.visibility = View.GONE
            llDialogPatientData.visibility = View.GONE
            btnDialogPositive.text = "Keluar"

            btnDialogPositive.setOnClickListener {
                viewModel.signOut()
            }

            btnDialogNegative.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    private fun bindViewModel() {
        viewModel.shouldOpenSignIn.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}