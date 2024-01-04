package com.funnypaper.simme.data.repository.board

import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.relation.BoardRelation
import kotlinx.coroutines.flow.Flow

interface IDataBoardRepository {
    fun getBoard(id: Int): Flow<BoardEntity>
    fun getBoardRelation(id: Int): Flow<BoardRelation>
    suspend fun updateBoard(value: BoardEntity)
    suspend fun insertBoard(value: BoardEntity): Long
    suspend fun deleteBoard(value: BoardEntity)
}