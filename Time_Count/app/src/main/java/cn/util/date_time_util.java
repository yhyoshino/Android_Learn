package cn.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.config.Config;

import static java.lang.Math.abs;

public class date_time_util {

    /**
     * 检查输入是否符合正则regular_time
     * @param begin_time 起始时间
     * @param end_time 结束时间
     * @return 是否符合正则,是为true，否为false
     */
    public boolean input_check(String begin_time, String end_time){
        boolean is_check;
        Config config = new Config();
        Pattern pattern= Pattern.compile(config.regular_time);
        Matcher matcher_begin = pattern.matcher(begin_time);
        Matcher matcher_end = pattern.matcher(end_time);
        is_check=matcher_begin.matches()&matcher_end.matches();
        return is_check;
    }

    /**
     * 根据输入时间获取工作时间
     * 上班时间分钟在30分钟以上计一小时，0不计时，1-30记半小时
     * 下班时间分钟在30以下舍去，否则计半小时
     * @param begin_time 上班时间
     * @param end_time 下班时间
     * @return float格式工作时间
     */
    public float time_compute(String begin_time, String end_time){
        int begin_time_hour = Integer.parseInt(begin_time.substring(0,begin_time.indexOf(":")));
        float begin_time_minute = Float.parseFloat(begin_time.substring(begin_time.indexOf(":")+1));
        if(abs(begin_time_minute)>1e-6){
            begin_time_minute = begin_time_minute>30 ? 1 : (float) 0.5;
        }
        int end_time_hour = Integer.parseInt(end_time.substring(0,end_time.indexOf(":")));
        float end_time_minute = Integer.parseInt(end_time.substring(end_time.indexOf(":")+1)) >= 30 ? (float) 0.5 :0;
        return end_time_hour-begin_time_hour+end_time_minute-begin_time_minute;
    }

    /**
     * 根据年，月，日生成距元时间的时间
     * @param year 年
     * @param month 月
     * @param dayOfMonth 日
     * @return long类型的时间
     */
    public long Calendar_Date(int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 将输入日期转为年月日的String形式
     * @param date 距元时间的时间长度
     * @return 日期的String格式
     */
    public String date_to_string(long date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sd_format = new SimpleDateFormat("yyyy-MM-dd");
        return sd_format.format(date);
    }
}
