package stmik.mp.hafiz.antriandokter.ui.navbar.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.databinding.FragmentProfilBinding
import stmik.mp.hafiz.antriandokter.ui.bantuan.BantuanActivity
import stmik.mp.hafiz.antriandokter.ui.dialog.SignOutDialogFragment
import stmik.mp.hafiz.antriandokter.ui.editprofil.EditProfilActivity

@AndroidEntryPoint
class ProfilFragment : Fragment() {

    private val viewModel: ProfilViewModel by viewModels()
    private var _binding: FragmentProfilBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        viewModel.onViewLoaded()

        bindViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewLoaded()
        bindView()
        bindViewModel()

    }

    private fun bindView() {
        binding.ivProfileEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfilActivity::class.java)
            startActivity(intent)
        }
        binding.llBantuan.setOnClickListener {
            val intent = Intent(requireContext(), BantuanActivity::class.java)
            startActivity(intent)
        }
        binding.llKeluar.setOnClickListener {
            onClickSignOut()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvProfileName.text = it.name
            binding.tvProfileEmail.text = it.email
        }
    }

    private fun onClickSignOut() {
        SignOutDialogFragment().show(parentFragmentManager, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}