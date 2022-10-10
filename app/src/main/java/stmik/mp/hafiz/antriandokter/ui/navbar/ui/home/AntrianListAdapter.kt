package stmik.mp.hafiz.antriandokter.ui.navbar.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stmik.mp.hafiz.antriandokter.data.api.queue.AllBookingsResponse
import stmik.mp.hafiz.antriandokter.databinding.ItemListAntrianBinding

class AntrianListAdapter(
    private var allBookingsUser: List<AllBookingsResponse.Data.Data>
) : RecyclerView.Adapter<AntrianListAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemListAntrianBinding
    ) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateAntrianList(list: List<AllBookingsResponse.Data.Data>) {
        this.allBookingsUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListAntrianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = allBookingsUser[position]

        holder.binding.tvPatientNumber.text = patient.queueNumber.toString()
        holder.binding.tvPatientName.text = patient.patientName.toString()
    }

    override fun getItemCount(): Int {
        return allBookingsUser.size
    }
}