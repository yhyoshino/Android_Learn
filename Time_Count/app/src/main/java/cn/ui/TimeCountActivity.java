package cn.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.db.DBManager;
import cn.time_count.R;
import cn.type.TimeCount;

public class TimeCountActivity extends BaseActivity {
    private DBManager dbManager;
    private String Date;
    private List<TimeCount> timeCountList = new ArrayList<>();
    private MonthCountAdapter adapter;

    @BindView(R.id.recyclerview_container)
    RecyclerView recyclerView;
    @BindView(R.id.month)
    TextView Month;

    @Override
    protected int ContentLayout() {
        return R.layout.list_time_count;
    }

    @Override
    protected int menu(){
        return R.menu.menu_back;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.count_list_back){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void afterview(){
        Date = getIntent().getStringExtra("Date");
        assert Date != null;
        dbManager = new DBManager();
        float count = dbManager.getDayMonth(timeCountList,Date);

        Month.setText(Date.substring(0,Date.lastIndexOf("-")));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MonthCountAdapter(timeCountList,Date.substring(0,Date.lastIndexOf("-")),String.valueOf(count));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.left_but)
    void left_click(){
        int year = Integer.parseInt(Date.substring(0,Date.indexOf("-")));
        int month = Integer.parseInt(Date.substring(Date.indexOf("-")+1,Date.lastIndexOf("-")));
        month = month - 1;
        if(month<=0){
            year = year - 1;
            month = 12;
        }
        if(month<10)
            Date = year +"-0"+ month +"-01";
        else Date = year +"-"+ month +"-01";
        float count = dbManager.getDayMonth(timeCountList,Date);
        adapter.setCount(String.valueOf(count));
        adapter.setMonth(Date.substring(0,Date.lastIndexOf("-")));
        Month.setText(Date.substring(0,Date.lastIndexOf("-")));
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.right_but)
    void right_click(){
        int year = Integer.parseInt(Date.substring(0,Date.indexOf("-")));
        int month = Integer.parseInt(Date.substring(Date.indexOf("-")+1,Date.lastIndexOf("-")));
        month = month + 1;
        if(month>12){
            year = year + 1;
            month = 1;
        }
        if(month<10)
            Date = year +"-0"+ month +"-01";
        else Date = year +"-"+ month +"-01";
        float count = dbManager.getDayMonth(timeCountList,Date);
        adapter.setCount(String.valueOf(count));
        adapter.setMonth(Date.substring(0,Date.lastIndexOf("-")));
        Month.setText(Date.substring(0,Date.lastIndexOf("-")));
        adapter.notifyDataSetChanged();
    }

}
