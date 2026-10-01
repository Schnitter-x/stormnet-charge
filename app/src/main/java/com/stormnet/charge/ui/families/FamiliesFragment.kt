package com.stormnet.charge.ui.families

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.stormnet.charge.databinding.DialogAddFamilyBinding
import com.stormnet.charge.databinding.FragmentFamiliesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class FamiliesFragment : Fragment() {

    private var _b: FragmentFamiliesBinding? = null
    private val b get() = _b!!
    private val vm: FamiliesViewModel by viewModels()
    private lateinit var adapter: FamiliesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentFamiliesBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FamiliesAdapter { family ->
            confirmDelete(family)
        }

        b.recyclerFamilies.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerFamilies.adapter = adapter

        b.fabAdd.setOnClickListener { showAddDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.families.collect { list ->
                    adapter.submitList(list)
                    b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    b.tvCount.text = "المجموع: ${list.size}"
                }
            }
        }
    }

    private fun showAddDialog() {
        val db = DialogAddFamilyBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("إضافة عائلة")
            .setView(db.root)
            .setPositiveButton("حفظ", null)
            .setNegativeButton("إلغاء", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                vm.addFamily(
                    name = db.etName.text.toString(),
                    phone = db.etPhone.text.toString()
                ) { success, msg ->
                    if (success) {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        db.tilName.error = msg
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        dialog.show()
    }

    private fun confirmDelete(family: com.stormnet.charge.data.Family) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("حذف عائلة")
            .setMessage("هل تريد حذف \"${family.name}\"؟\nلا يمكن التراجع عن هذه العملية.")
            .setPositiveButton("حذف") { _, _ ->
                vm.deleteFamily(family)
                Toast.makeText(requireContext(), "تم الحذف", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("إلغاء", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
