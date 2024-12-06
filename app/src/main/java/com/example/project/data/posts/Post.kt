package com.example.project.data.posts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.project.data.songs.Song
import java.io.Serializable

@Entity(tableName = "posttable")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "body")val body: String,
    @ColumnInfo(name = "song")val songTitle: String,
    @ColumnInfo(name = "artist")val artist: String,
) : Serializable