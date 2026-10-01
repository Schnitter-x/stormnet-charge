package com.stormnet.charge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "families")
data class Family(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
