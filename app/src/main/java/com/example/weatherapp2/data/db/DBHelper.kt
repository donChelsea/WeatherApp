package com.example.weatherapp2.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID_COL INTEGER PRIMARY KEY," +
                "$CITY_COL TEXT)"

        db.execSQL(query)
    }

    fun addCity(city: String) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(CITY_COL, city)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun readCourses(): MutableList<String> {
        val db = this.readableDatabase

        val projection = arrayOf(ID_COL, CITY_COL)

        val cursor = db.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val cityNames = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val cityName = getString(getColumnIndexOrThrow(CITY_COL))
                cityNames.add(cityName)
            }
        }
        cursor.close()

        return cityNames
    }

    fun deleteEntry(city: String) {
        val selection = "$CITY_COL LIKE ?"
        val selectionArgs = arrayOf(city)
        val deletedRows = this.readableDatabase.delete(TABLE_NAME, selection, selectionArgs)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "city_db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "city_table"
        private const val ID_COL = "id"
        private const val CITY_COL = "city"
    }
}

