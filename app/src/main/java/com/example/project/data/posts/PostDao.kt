package com.example.project.data.posts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posttable")
    fun getAll() : Flow<List<Post>>

    @Query("SELECT * from posttable WHERE id = :id")
    fun getPost(id: Int): Flow<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Post)

    @Update
    suspend fun update(todo: Post)

    @Delete
    suspend fun delete(todo: Post)

    @Query("DELETE from posttable")
    suspend fun deleteAll()
}