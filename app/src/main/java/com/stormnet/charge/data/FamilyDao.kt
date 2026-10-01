package com.stormnet.charge.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyDao {

    @Query("SELECT * FROM families ORDER BY name ASC")
    fun getAll(): Flow<List<Family>>

    @Query("SELECT * FROM families WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Family?

    @Insert
    suspend fun insert(family: Family): Long

    @Delete
    suspend fun delete(family: Family)
}
