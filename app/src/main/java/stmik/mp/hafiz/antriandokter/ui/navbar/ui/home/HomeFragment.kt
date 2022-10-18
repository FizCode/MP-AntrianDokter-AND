package stmik.mp.hafiz.antriandokter.ui.navbar.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import stmik.mp.hafiz.antriandokter.R
import stmik.mp.hafiz.antriandokter.databinding.FragmentHomeBinding
import kotlin.math.abs


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    private val viewModel: HomeViewModel by viewModels()
    lateinit var antrianListAdapter: AntrianListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.onViewLoaded()
        bindView()
        bindViewModel()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })

        /*// for test Ticket Activity
        binding.tvHomeAntrianNow.setOnClickListener {
            val intent = Intent(context, TicketActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }*/

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 3000)
    }

    private fun bindView() {
        viewPager2 = binding.vp2HomeImageSlider
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.img_vp_1)
        imageList.add(R.drawable.img_vp_2)
        imageList.add(R.drawable.img_vp_3)

        imageSliderAdapter = ImageSliderAdapter(imageList, viewPager2)
        viewPager2.adapter = imageSliderAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        antrianListAdapter = AntrianListAdapter(emptyList())

        binding.rvPatientQueue.adapter = antrianListAdapter
    }

    private fun bindViewModel() {
        viewModel.shouldShowAllBookings.observe(viewLifecycleOwner) { list ->
            val bookingSize = list.data?.data?.size
            val isDoneFalse = list.data?.data!!.filter { it.isDone == false }
            val currentQueue = isDoneFalse[0].queueNumber

            antrianListAdapter.updateAntrianList(isDoneFalse)
            binding.tvHomeAntrianNow.text = currentQueue.toString()
                // isDoneFalse.size.toString()
            binding.tvHomeAntrianTotal.text = bookingSize.toString()
        }

        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            binding.tvHomeUserName.text = "Halo, ${it.name}"
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}