package com.example.mydbapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class UserOpenHelper(c: Context): SQLiteOpenHelper(c, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "myapp.db"
        const val DB_VERSION = 3
        const val CREATE_TABLE: String = "create table ${UserContract.Users.TABLE_NAME} (" +
                "${BaseColumns._ID} integer primary key autoincrement, " +
                "${UserContract.Users.COL_NAME} text," +
                "${UserContract.Users.COL_SCORE} integer)"
        const val INIT_TABLE: String = "insert into ${UserContract.Users.TABLE_NAME}" +
                "(${UserContract.Users.COL_NAME}, ${UserContract.Users.COL_SCORE}) values" +
                "('taguchi', 42), " +
                "('fkoji', 82), " +
                "('dotinstall', 62)"
        const val DROP_TABLE: String = "drop table if exists ${UserContract.Users.TABLE_NAME}"
    }

    // DBが作られた時に呼ばれるメソッド
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
        db?.execSQL(INIT_TABLE)
    }

    // DBのバージョンが上がった時に呼ばれるメソッド
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

}