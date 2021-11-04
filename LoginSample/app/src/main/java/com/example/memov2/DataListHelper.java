package com.example.memov2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataListHelper extends SQLiteOpenHelper {
    private static final int DB_VER = 1;
    private static final String DB_NAME = "LoginDB.db";
    private static final String TBL_NAME = "tbl_userMaster";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERID = "userid";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    public DataListHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<RowData> selectAll() {  //TOP画面へ表示するリストの読み込み
        SQLiteDatabase db = getReadableDatabase();  //データベースを作るクラスのインスタンス化
        Cursor cursor;
        if (db == null) {   //データがない場合は、そのままnullを返す
            return null;
        }
        cursor = db.query(TBL_NAME, new String[]{COLUMN_ID,COLUMN_USERID, COLUMN_PASSWORD,
                COLUMN_EMAIL}, null, null, null, null, null);
        int numRows = cursor.getCount();
        cursor.moveToFirst();
        List<RowData> dataList = new ArrayList<RowData>();

        for (int i = 0; i < numRows; i++) {
            RowData rowData = new RowData();
            rowData.setUserid(cursor.getString(0));
            rowData.setPassWord((cursor.getString(1)));
            rowData.setEmail(cursor.getString(2));
            dataList.add(rowData);
            cursor.moveToNext();
        }
        cursor.close();

        return dataList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TBL_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                COLUMN_USERID + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_EMAIL + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void dataInsert(RowData data) {  //FloatingActionボタンで、登録ボタンを押したときの処理(データベースへの登録処理)
        SQLiteDatabase db = getWritableDatabase();  //データベースへの書き込みメソッドを用意
        ContentValues val = new ContentValues();

        val.put(COLUMN_USERID, data.getUserid());  //リスト(1列)からタイトル、詳細を取得
        val.put(COLUMN_PASSWORD, data.getPassword());  //リスト(1列)からタイトル、詳細を取得
        val.put(COLUMN_EMAIL, data.getEmail());
        db.insert(TBL_NAME, null, val); //dbに固定テーブルへ、追加した値をインサート
    }

    public List<RowData> selectDetail(String userid) { //選択したリストの詳細を取得
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        String[] selectArgs = new String[0];
        if (db == null) {   //dbの中身がnullかをチェック
            return null;
        }
        //リストの検索
        cursor = db.query(TBL_NAME, new String[]{COLUMN_ID,COLUMN_USERID, COLUMN_PASSWORD,
                        COLUMN_EMAIL},COLUMN_USERID + "=?", new String[]{userid}, null,
                null, null, "1");
//        cursor = db.rawQuery("select * FROM" + TBL_NAME + "WHERE content like '%' ||  ? || '%' order by created desc;", selectArgs);
        int numRows = cursor.getCount();    //データ件数の取得
        cursor.moveToFirst();   //リストの先頭に移動
        List<RowData> dataList = new ArrayList<>();

        for (int i = 0; i < numRows; i++) { //データの取得とデータをArrayへ入れる
            RowData rowData = new RowData();
            rowData.setId(cursor.getInt(0));
            rowData.setUserid(cursor.getString(1));
            rowData.setPassWord(cursor.getString(2));
//            rowData.setEmail(cursor.getString(3));
            dataList.add(rowData);
            cursor.moveToNext();
        }
        cursor.close();

        return dataList; //リストに入れたデータを返す
    }
}
