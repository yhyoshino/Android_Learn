package cn.type;

import cn.time_count.R;

public class TimeCount {
    private long tid;
    private String Date;
    private String begin_time;
    private String end_time;
    private String count_time;
    private int image_id;

    public TimeCount(long tid,String begin_time, String end_time, String count_time, int image_id){
        this.tid = tid;
        this.begin_time=begin_time;
        this.end_time=end_time;
        this.count_time=count_time;
        this.image_id=image_id;
    }

    public TimeCount(long tid,String begin_time, String end_time, int count_time,String Date){
        this.tid = tid;
        this.Date = Date;
        this.begin_time=begin_time;
        this.end_time=end_time;
        float f_time = (float)count_time/2;
        this.count_time=String.valueOf(f_time);
        this.image_id= R.mipmap.ic_remove;
    }

    public long getTid(){
        return tid;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getCount_time() {
        return count_time;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getDay(){
        return Date;
    }

}
