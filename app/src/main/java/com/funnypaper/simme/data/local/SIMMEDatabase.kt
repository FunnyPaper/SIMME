package com.funnypaper.simme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.funnypaper.simme.data.local.converter.PointConverter
import com.funnypaper.simme.data.local.converter.SplineConverter
import com.funnypaper.simme.data.local.dao.BoardDao
import com.funnypaper.simme.data.local.dao.MetaDataDao
import com.funnypaper.simme.data.local.dao.NoteDao
import com.funnypaper.simme.data.local.dao.ProjectDao
import com.funnypaper.simme.data.local.dao.RankDao
import com.funnypaper.simme.data.local.dao.SplineDao
import com.funnypaper.simme.data.local.entity.BoardEntity
import com.funnypaper.simme.data.local.entity.MetaDataEntity
import com.funnypaper.simme.data.local.entity.NoteEntity
import com.funnypaper.simme.data.local.entity.ProjectEntity
import com.funnypaper.simme.data.local.entity.RankEntity
import com.funnypaper.simme.data.local.entity.SplineEntity

@Database(
    entities = [
        BoardEntity::class,
        MetaDataEntity::class,
        NoteEntity::class,
        ProjectEntity::class,
        RankEntity::class,
        SplineEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    PointConverter::class,
    SplineConverter::class
)
abstract class SIMMEDatabase : RoomDatabase() {
    abstract fun boardDao(): BoardDao
    abstract fun noteDao(): NoteDao
    abstract fun projectDao(): ProjectDao
    abstract fun rankDao(): RankDao
    abstract fun splineDao(): SplineDao
    abstract fun metaDataDao(): MetaDataDao

    companion object {
        const val NAME = "simme_database"
    }
}