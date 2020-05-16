package cn.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.time_count.R;
import cn.type.TimeCount;

public class MonthCountAdapter extends RecyclerView.Adapter<MonthCountAdapter.ViewHolder>{

    private List<TimeCount> timeCountList;
    private String month;
    private String count;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dayDate;
        private TextView beginTime;
        private TextView endTime;
        private TextView countTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayDate = itemView.findViewById(R.id.date_day);
            beginTime = itemView.findViewById(R.id.text_begin);
            endTime = itemView.findViewById(R.id.text_end);
            countTime = itemView.findViewById(R.id.text_count);
        }
    }

    MonthCountAdapter(List<TimeCount> timeCountList,String month,String count){
        this.timeCountList = timeCountList;
        this.month = month;
        this.count = count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month_count,parent,false);
        return new MonthCountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthCountAdapter.ViewHolder holder, int position) {
        if(position==0){
            holder.dayDate.setText(R.string.month);
            holder.beginTime.setText(month);
            holder.endTime.setText(R.string.time_month);
            holder.countTime.setText(count);
        }
        else if(position == 1){
            holder.dayDate.setText(R.string.day_date);
            holder.beginTime.setText(R.string.begin_time);
            holder.endTime.setText(R.string.end_time);
            holder.countTime.setText(R.string.time_work);
        }
        else{
            TimeCount timeCount = timeCountList.get(position-2);
            holder.dayDate.setText(timeCount.getDay());
            holder.beginTime.setText(timeCount.getBegin_time());
            holder.endTime.setText(timeCount.getEnd_time());
            holder.countTime.setText(timeCount.getCount_time());
        }
    }

    @Override
    public int getItemCount() {
        return timeCountList.size() + 2;
    }
}
