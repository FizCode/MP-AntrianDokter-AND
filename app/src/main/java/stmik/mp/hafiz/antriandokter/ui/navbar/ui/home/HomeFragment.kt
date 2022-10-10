package stmik.mp.hafiz.antriandokter.ui.navbar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.databinding.FragmentHomeBinding


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    lateinit var antrianListAdapter: AntrianListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.onViewLoaded()
        bindViewModel()

        antrianListAdapter =
            AntrianListAdapter(emptyList())

        binding.rvPatientQueue.adapter = antrianListAdapter

        return binding.root
    }

    private fun bindViewModel() {
        viewModel.shouldShowAllBookings.observe(viewLifecycleOwner) { list ->
            val bookingSize = list.data?.data?.size
            val isDoneFalse = list.data?.data!!.filter { it.isDone == false }

            antrianListAdapter.updateAntrianList(isDoneFalse)
            binding.tvHomeAntrianTotal.text = bookingSize.toString()
            binding.tvHomeAntrianNow.text = isDoneFalse.size.toString()
        }

        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvHomeUserName.text = "Halo, ${it.name}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}