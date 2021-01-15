package com.lwj.memorizer.ui.cardbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lwj.memorizer.R
import com.lwj.memorizer.data.entities.Cardbook
import com.lwj.memorizer.databinding.ItemCardbookBinding

class CardbookAdapter(private val viewModel: CardbookViewModel) :
    RecyclerView.Adapter<CardbookAdapter.CardbookViewHolder>() {

    private val cardbookList = arrayListOf<Cardbook>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardbookViewHolder {
        return CardbookViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_cardbook, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CardbookViewHolder, position: Int) {
        holder.onBind(viewModel, 0)
    }

    override fun getItemCount() = cardbookList.size


    class CardbookViewHolder(private val binding: ItemCardbookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(viewModel: CardbookViewModel, contentCount: Int) {
            binding.apply {
                vm = viewModel
                count = contentCount
                position = adapterPosition
            }
        }
    }
}