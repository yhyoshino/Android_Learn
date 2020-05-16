package cn.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.time_count.R;
import cn.type.TimeCount;

public class DayCountAdapter extends RecyclerView.Adapter<DayCountAdapter.ViewHolder> {
    private List<TimeCount> timeCountList;
    private OnItemClickListener myListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView begin_time;
        TextView end_time;
        TextView count_time;
        ImageView count_remove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            begin_time=itemView.findViewById(R.id.text_begin);
            end_time=itemView.findViewById(R.id.text_end);
            count_time=itemView.findViewById(R.id.text_count);
            count_remove=itemView.findViewById(R.id.count_remove);
        }
    }

    DayCountAdapter(List<TimeCount> timeCountList){
        this.timeCountList = timeCountList;
    }

    DayCountAdapter(List<TimeCount> timeCountList,OnItemClickListener myListener){
        this.myListener = myListener;
        this.timeCountList = timeCountList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_count,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeCount timeCount = timeCountList.get(position);
        holder.begin_time.setText(timeCount.getBegin_time());
        holder.end_time.setText(timeCount.getEnd_time());
        holder.count_time.setText(timeCount.getCount_time());
        holder.count_remove.setImageResource(timeCount.getImage_id());
        holder.count_remove.setOnClickListener(v ->{
            if(myListener==null){
                holder.count_remove.setVisibility(View.GONE);
            }
            else myListener.onCLick(timeCount.getTid());
            notifyDataSetChanged();
        } );
    }

    @Override
    public int getItemCount() {
        return timeCountList.size();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    public interface OnItemClickListener{
        void onCLick(long tid);
    }
}
