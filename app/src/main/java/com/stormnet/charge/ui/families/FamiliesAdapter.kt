package com.stormnet.charge.ui.families

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stormnet.charge.data.Family
import com.stormnet.charge.databinding.ItemFamilyBinding

class FamiliesAdapter(
    private val onDelete: (Family) -> Unit
) : ListAdapter<Family, FamiliesAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Family>() {
            override fun areItemsTheSame(a: Family, b: Family) = a.id == b.id
            override fun areContentsTheSame(a: Family, b: Family) = a == b
        }
    }

    inner class VH(val b: ItemFamilyBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemFamilyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val family = getItem(position)
        holder.b.tvName.text = family.name
        holder.b.tvPhone.text = if (family.phone.isBlank()) "بدون رقم" else family.phone
        holder.b.tvInitial.text = family.name.firstOrNull()?.toString() ?: "؟"
        holder.b.btnDelete.setOnClickListener { onDelete(family) }
    }
}
