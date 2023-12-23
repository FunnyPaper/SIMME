package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.relation.BoardRelation

@Dao
interface BoardDao : ICRUDDao<BoardEntity> {
    @Query("SELECT * FROM boards WHERE id = :value")
    suspend fun getBoardById(value: Int): BoardEntity

    @Transaction
    @Query("SELECT * FROM boards WHERE id = :value")
    suspend fun getBoardRelationById(value: Int): BoardRelation

    @Query("SELECT * FROM boards")
    suspend fun getBoards(): List<BoardEntity>
}