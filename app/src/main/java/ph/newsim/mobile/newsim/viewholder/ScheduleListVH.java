package ph.newsim.mobile.newsim.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.ScheduleListAdapter;
import ph.newsim.mobile.newsim.model.Schedule;

public class ScheduleListVH extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ScheduleListAdapter.OnScheduleClickListener mOnScheduleClickListener;

    private Schedule mSchedule;
    private View mItemView;

    private TextView mDuration;
    private TextView mCourseCode;
    private TextView mTime;
    private TextView mDate;

    public ScheduleListVH(View itemView) {
        super(itemView);
        mItemView = itemView;
        mDuration = (TextView) itemView.findViewById(R.id.label_schedule_duration);
        mCourseCode = (TextView) itemView.findViewById(R.id.label_schedule_code);
        mTime = (TextView) itemView.findViewById(R.id.label_schedule_time);
        mDate = (TextView) itemView.findViewById(R.id.label_schedule_date);
    }

    public void bindData(Schedule schedule){
        mSchedule = schedule;
        mDuration.setText(schedule.getIntDuration() + "");
        mCourseCode.setText(schedule.getCourseCode());
        mTime.setText(schedule.getFormattedTimeStart() + " - " + schedule.getFormattedTimeEnd());
        mDate.setText(schedule.getFormattedDateStart("MMM d") + " - " + schedule.getFormattedDateEnd("MMM d yyyy"));
        mItemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        assert mOnScheduleClickListener != null;
        mOnScheduleClickListener.onScheduleClick(mSchedule);
    }

    public void setOnScheduleClickListener(ScheduleListAdapter.OnScheduleClickListener onScheduleClickListener) {
        mOnScheduleClickListener = onScheduleClickListener;
    }
}
