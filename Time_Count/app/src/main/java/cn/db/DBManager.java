package cn.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.config.Config;
import cn.type.TimeCount;

public class DBManager{
    private SQLiteDatabase DB;

    public DBManager(){
        DB = getDB();
    }

    public DBManager(String date) {
        DB = getDB();
        creteTable("year"+date.substring(0,date.indexOf("-")));
    }

    //创建或获取数据库
    private SQLiteDatabase getDB(){
        Config config = new Config();
        String pathDB = config.DBSite;
        File filePath = new File(pathDB);
        File fileDb = new File(pathDB+"/"+config.DBName);
//        File filePath = new File(config.PrivatePath);
//        File fileDb = new File(config.PrivatePath+config.DBName);
        try {
            if(!filePath.exists())
                if(!filePath.mkdirs())
                    return null;
            if(!fileDb.exists())
                if(!fileDb.createNewFile())
                    return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(fileDb, null);
        return db;
    }

    //根据输入年份创建表，已存在时忽略
    private void creteTable(String year){
        Config config = new Config();
        DB.execSQL(config.CreateTable+year+config.ListMes);
    }

    public void insertData(String date,long tid,String begintime,String endtime,float timecount){
        int time_ex = (int) (timecount*2);//小数位只有0.5，直接以2倍存储
        String year = "year"+date.substring(0,date.indexOf("-"));
        creteTable(year);
        ContentValues cValues = new ContentValues();
        cValues.put("date",date);
        cValues.put("tid",tid);
        cValues.put("begintime",begintime);
        cValues.put("endtime",endtime);
        cValues.put("timecount",time_ex);
        DB.insert(year,null,cValues);
    }

    public void deleteData(String date,long tid){
        String year = "year"+date.substring(0,date.indexOf("-"));
        String whereClause = "tid=?";
        String[] whereArgs = {String.valueOf(tid)};
        DB.delete(year,whereClause,whereArgs);
    }

    public void getDayData(List<TimeCount>timeCountList,String Date){
        String year = "year"+Date.substring(0,Date.indexOf("-"));
        creteTable(year);
        timeCountList.clear();
        Cursor cursor = DB.query(year,null,null,null,null,null,"begintime");
        if(cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                String date = cursor.getString(2);
                if(!TextUtils.equals(date,Date)){
                    cursor.moveToNext();
                    continue;
                }
                long tid = cursor.getLong(1);
                String begintime = cursor.getString(3);
                String endtime = cursor.getString(4);
                int timecount = cursor.getInt(5);
                TimeCount timeCount = new TimeCount(tid,begintime,endtime,timecount,Date);
                timeCountList.add(timeCount);
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    public float getDayMonth(List<TimeCount>timeCountList,String Date){
        float count = 0;
        String year = "year"+Date.substring(0,Date.indexOf("-"));
        creteTable(year);
        String month = Date.substring(0,Date.lastIndexOf("-"));
        timeCountList.clear();
        Cursor cursor = DB.query(year,null,null,null,null,null,"date,begintime");
        if(cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                String date = cursor.getString(2);
                if(!TextUtils.equals(date.substring(0,date.lastIndexOf("-")),month)){
                    cursor.moveToNext();
                    continue;
                }
                long tid = cursor.getLong(1);
                String begintime = cursor.getString(3);
                String endtime = cursor.getString(4);
                int timecount = cursor.getInt(5);
                TimeCount timeCount = new TimeCount(tid,begintime,endtime,timecount,date);
                timeCountList.add(timeCount);
                count = count + timecount;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return count/2;
    }
}
