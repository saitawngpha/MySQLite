package com.saitawngpha.mysqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_PATH= "";
    private static String DB_NAME= "account.db";
    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 11);
        this.mContext = context;
        if(Build.VERSION.SDK_INT >= 17){
            this.DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
            Log.e("Path:", DB_PATH);
        }else {
            this.DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
            Log.e("Path:", DB_PATH);
        }

    }

    @Override
    public synchronized void close() {
        if(mDatabase != null){
            mDatabase.close();
        }
        super.close();
    }

    private boolean checkDatabase(){
        //Todo: Check Database
        SQLiteDatabase tempDB = null;
        try {
            String path = DB_PATH+DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }catch (Exception ex){}
        if(tempDB!=null){
            tempDB.close();
        }
        return tempDB!=null?true:false;
    }

    public void copyDatabase(){
        //Todo: Copy Database
        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase(){
        //Todo: Open Database
        String path = DB_PATH+DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDatabase(){
        //Todo: Create Database
        boolean isDBExist = checkDatabase();
        if (isDBExist){
        }else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            }catch (Exception e){throw new Error("Copy Database Error");}
        }
    }

    public List<Account>getAllUsers(){
        //Todo: Get All Users
        List<Account> temp = new ArrayList<Account>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Account", null);
            if (c==null) return null;
            c.moveToFirst();
            do {
                Account account = new Account(c.getString(c.getColumnIndex("name")),(c.getString(c.getColumnIndex("email"))));
                temp.add(account);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){}
        db.close();
        return temp;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
