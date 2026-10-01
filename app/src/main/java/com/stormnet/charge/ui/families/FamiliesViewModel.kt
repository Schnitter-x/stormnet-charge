package com.stormnet.charge.ui.families

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stormnet.charge.data.AppDatabase
import com.stormnet.charge.data.Family
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FamiliesViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.get(app).familyDao()

    val families: StateFlow<List<Family>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addFamily(name: String, phone: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val trimmed = name.trim()
            if (trimmed.isEmpty()) {
                onResult(false, "الرجاء إدخال اسم العائلة")
                return@launch
            }
            val existing = dao.findByName(trimmed)
            if (existing != null) {
                onResult(false, "يوجد عائلة بنفس الاسم")
                return@launch
            }
            dao.insert(Family(name = trimmed, phone = phone.trim()))
            onResult(true, "تمت الإضافة")
        }
    }

    fun deleteFamily(family: Family) {
        viewModelScope.launch {
            dao.delete(family)
        }
    }
}
