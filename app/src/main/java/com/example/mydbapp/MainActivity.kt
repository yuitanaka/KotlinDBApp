package com.example.mydbapp

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // tableの定義
        // users(name, score)

        // open helper

        // open db
        val userOpenHelper = UserOpenHelper(this)
        val db: SQLiteDatabase = userOpenHelper.writableDatabase

        // transaction
        // ある一連の処理をまとめて行うことを保証してくれる仕組み
        // レコード間の値の受け渡しの時、変な処理が間に入っていないことを証明したい時などに使う
        // 例：fkojiのスコア82から10点をtaguchiのスコアに渡したい時
        try{
            db.beginTransaction()
            db.execSQL("update ${UserContract.Users.TABLE_NAME} " +
                    "set ${UserContract.Users.COL_SCORE} = ${UserContract.Users.COL_SCORE} + 10 " +
                    "where ${UserContract.Users.COL_NAME} = 'taguchi'")
            db.execSQL("update ${UserContract.Users.TABLE_NAME} " +
                    "set ${UserContract.Users.COL_SCORE} = ${UserContract.Users.COL_SCORE} - 10 " +
                    "where ${UserContract.Users.COL_NAME} = 'fkoji'")
            // 上記SQLの処理がうまくいったらDBに反映させる
            db.setTransactionSuccessful()
        } catch (e: SQLException){
            e.printStackTrace()
        } finally {
            // 必ずトランザクションを終わらせる
            db.endTransaction()
        }

        // 処理　select, insert, update, delete
        // スコアが50より大きい人を一人表示する
//        val c: Cursor = db.query(
//            UserContract.Users.TABLE_NAME,
//            null,
//            "${UserContract.Users.COL_SCORE} > ?",
//            arrayOf("50"),
//            null,
//            null,
//            "${UserContract.Users.COL_SCORE} desc",
//            "1"
//        )

        // 値の設定
//        val newUser = ContentValues()
//        newUser.put(UserContract.Users.COL_NAME, "tanaka")
//        newUser.put(UserContract.Users.COL_SCORE, 44)
//        val newId = db.insert(
//            UserContract.Users.TABLE_NAME,
//            null,
//            newUser
//        )
//        //値の更新
//        val newScore = ContentValues()
//        newScore.put(UserContract.Users.COL_SCORE, 100)
//        val  updatedCount = db.update(
//            UserContract.Users.TABLE_NAME,
//            newScore,
//            "${UserContract.Users.COL_NAME} = ?",
//            arrayOf("fkoji")
//
//        )
//        //レコードの削除
//        val  deletedCount = db.delete(
//            UserContract.Users.TABLE_NAME,
//            "${UserContract.Users.COL_NAME} = ?",
//            arrayOf("dotinstall")
//
//        )

        // 全件抽出
        val c: Cursor = db.query(
            UserContract.Users.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        Log.v("DB_TEST", "Count: ${c.count}")

        while(c.moveToNext()){
            val id = c.getInt(c.getColumnIndex(BaseColumns._ID))
            val name = c.getString(c.getColumnIndex(UserContract.Users.COL_NAME))
            val score = c.getInt(c.getColumnIndex(UserContract.Users.COL_SCORE))
            Log.v("DB_TEST", "id: $id name: $name score: $score")
        }

        c.close()

        db.close()

    }
}
