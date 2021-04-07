package com.example.mydbapp

class UserContract{

    // BaseColumns カラムに自動的に_idがつく
    abstract class Users private constructor() : Sample{
        // 定数を定義する時に必須
        companion object {
            const val TABLE_NAME: String = "users"
            const val COL_NAME: String = "name"
            const val COL_SCORE: String = "score"
        }
    }
    interface Sample{

        val a: String

    }
}