package com.funnypaper.simme.data.repository.board

import com.funnypaper.simme.data.local.dao.BoardDao
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.relation.BoardRelation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineDataBoardRepository @Inject constructor(
    private val boardDao: BoardDao
) : IDataBoardRepository {
    override fun getBoard(id: Int): Flow<BoardEntity> =
        boardDao.getBoardById(id)
    override fun getBoardRelation(id: Int): Flow<BoardRelation> =
        boardDao.getBoardRelationById(id)
    override suspend fun updateBoard(value: BoardEntity) =
        boardDao.update(value)
    override suspend fun insertBoard(value: BoardEntity) =
        boardDao.insert(value)
    override suspend fun deleteBoard(value: BoardEntity) =
        boardDao.delete(value)
}