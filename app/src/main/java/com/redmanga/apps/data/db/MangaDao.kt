package com.redmanga.apps.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.redmanga.apps.data.db.entities.Manga

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllManga(manga: List<Manga>)

    @Query("SELECT * FROM Manga WHERE judul LIKE :search ORDER BY id_manga DESC")
    fun getNewManga(search: String = "%%"): DataSource.Factory<Int, Manga>

    @Query("SELECT * FROM Manga ORDER BY pengunjung DESC")
    fun getMostViewManga(): DataSource.Factory<Int, Manga>

    @Query("SELECT * FROM Manga ORDER BY strftime('%Y-%m-%d', tgl_release) DESC")
    fun getLastestRealeasManga(): DataSource.Factory<Int, Manga>
}