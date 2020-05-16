package cn.config;

import android.os.Environment;

public class Config {

//    public Config(){ }

//    public Config(Context context){
//    使用私有目录
//        PrivatePath = Objects.requireNonNull(context.getExternalFilesDir("database")).getPath();
//    }
//    public String PrivatePath;
    public String regular_time = "((1\\d)|(2[0-3])|\\d):(([1-5]\\d)|\\d)|24:00";//时间正则式，允许24点
    public String DBSite = Environment.getExternalStorageDirectory().getPath()+"/a_time_count/database";
    public String DBName = "time.db";
    public String CreateTable = "create table if not exists ";
    //数据库表，id为标识，tid为时间，date为日期，begintime为上班时间，endtime为下班时间，timecount为工作时间*2
    public String ListMes = "(id integer primary key autoincrement,tid integer,date text,begintime text,endtime text,timecount integer)";
}
