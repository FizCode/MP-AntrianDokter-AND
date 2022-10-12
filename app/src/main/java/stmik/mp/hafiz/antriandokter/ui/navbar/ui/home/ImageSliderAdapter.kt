package stmik.mp.hafiz.antriandokter.ui.navbar.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import stmik.mp.hafiz.antriandokter.databinding.Vp2ImageSliderBinding

class ImageSliderAdapter(
    private val imageList: ArrayList<Int>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: Vp2ImageSliderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            Vp2ImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.ivImage.setImageResource(imageList[position])
        if (position == imageList.size-1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}