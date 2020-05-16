package cn.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.db.DBManager;
import cn.time_count.R;
import cn.type.TimeCount;
import cn.util.date_time_util;


public class MainActivity extends BaseActivity{

    @BindView(R.id.CalendarView)
    CalendarView calendarView;
    @BindView(R.id.begin_time_edit)
    EditText editText_begin;
    @BindView(R.id.end_time_edit)
    EditText editText_end;
    @BindView(R.id.recyclerview_container)
    RecyclerView recyclerView;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private date_time_util dt_util = new date_time_util();
    private DBManager dbManager;
    private String Date;
    private List<TimeCount> timeCountList = new ArrayList<>();

    @Override
    protected int ContentLayout() {
//        return R.layout.activity_main;
        return R.layout.main_container;
    }

    @Override
    protected int menu(){
        return R.menu.menu_main;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.count_list){
            Intent intent = new Intent(this,TimeCountActivity.class);
            intent.putExtra("Date",Date);
            startActivity(intent);
        }
//        Toast.makeText(mContext,"点击菜单",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeview(){
        requestPermission(this,permissions);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void afterview(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OnItemClickListener myListener = new OnItemClickListener();
        DayCountAdapter adapter = new DayCountAdapter(timeCountList,myListener);
//        DayCountAdapter adapter = new DayCountAdapter(timeCountList);
        recyclerView.setAdapter(adapter);//绑定Adapter

        Date = dt_util.date_to_string(calendarView.getDate());
        dbManager = new DBManager(Date);
        dbManager.getDayData(timeCountList,Date);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendarView.setDate(dt_util.Calendar_Date(year,month,dayOfMonth));
            Date = dt_util.date_to_string(calendarView.getDate());
            dbManager.getDayData(timeCountList,Date);
            adapter.notifyDataSetChanged();
            Toast.makeText(mContext,year+"年"+month+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
        });//监听点击事件
    }

    @OnClick(R.id.ensure_button)
    void ensure_button_click(){
        String begin_time = editText_begin.getText().toString();
        String end_time = editText_end.getText().toString();

        if(!dt_util.input_check(begin_time,end_time)) {
            Toast.makeText(mContext,"输入错误",Toast.LENGTH_SHORT).show();
            return;
        }//检查输入格式

        float time_count = dt_util.time_compute(begin_time,end_time);

//        timeCountList.add(new TimeCount(calendarView.getDate(),begin_time,end_time,time_string,R.mipmap.ic_remove));
        Date = dt_util.date_to_string(calendarView.getDate());
        dbManager.insertData(Date,System.currentTimeMillis(),begin_time,end_time,time_count);//加入数据库
        dbManager.getDayData(timeCountList,Date);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

        Toast.makeText(mContext, time_count+" "+Date,Toast.LENGTH_SHORT).show();
        editText_begin.setText("");
        editText_end.setText("");
        hideInputMethod();//控件处理
    }

    public class OnItemClickListener implements DayCountAdapter.OnItemClickListener {
        @Override
        public void onCLick(long tid) {
            dbManager.deleteData(Date,tid);
            dbManager.getDayData(timeCountList,Date);
        }
    }
}
