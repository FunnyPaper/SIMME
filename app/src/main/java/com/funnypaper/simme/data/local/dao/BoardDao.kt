package com.funnypaper.simme.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.relation.BoardRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardDao : ICRUDDao<BoardEntity> {
    @Query("SELECT * FROM boards WHERE id = :value")
    fun getBoardById(value: Int): Flow<BoardEntity>

    @Transaction
    @Query("SELECT * FROM boards WHERE id = :value")
    fun getBoardRelationById(value: Int): Flow<BoardRelation>

    @Query("SELECT * FROM boards")
    fun getAllBoards(): Flow<List<BoardEntity>>
}