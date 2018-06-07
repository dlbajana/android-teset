package ph.newsim.mobile.newsim.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.model.Schedule;

public class ReservationActivity extends AppCompatActivity {

    private Schedule mSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        mSchedule = getIntent().getParcelableExtra(Schedule.KEY);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ReservationActivity.this.finish();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView duration = (TextView) findViewById(R.id.label_schedule_duration);
        duration.setText(mSchedule.getIntDuration() + "");
        TextView courseTitle = (TextView) findViewById(R.id.label_schedule_course_title);
        courseTitle.setText(mSchedule.getCourseDescription());
        TextView courseCode = (TextView) findViewById(R.id.label_schedule_course_code);
        courseCode.setText(mSchedule.getCourseCode());
        TextView venue = (TextView) findViewById(R.id.label_schedule_venue);
        venue.setText(mSchedule.getVenue());
        TextView room = (TextView) findViewById(R.id.label_schedule_room);
        room.setText(mSchedule.getRoom());
        TextView dateSpan = (TextView) findViewById(R.id.label_schedule_date_span);
        dateSpan.setText(mSchedule.getFormattedDateStart("MMM d") + " - " + mSchedule.getFormattedDateEnd("MMM d, yyyy"));
        TextView time = (TextView) findViewById(R.id.label_schedule_time);
        time.setText(mSchedule.getFormattedTimeStart() + " - " + mSchedule.getFormattedTimeEnd());
        TextView instructor = (TextView) findViewById(R.id.label_schedule_instructor);
        instructor.setText(mSchedule.getInstructor());
        TextView assessor = (TextView) findViewById(R.id.label_schedule_assessor);
        assessor.setText(mSchedule.getAssessor());
        TextView batch = (TextView) findViewById(R.id.label_schedule_batch);
        batch.setText(mSchedule.getBatch());

        ImageView coverImage = (ImageView) findViewById(R.id.schedule_cover_image);
        Glide.with(this).load(R.drawable.cover_marconi).centerCrop().into(coverImage);
        coverImage.setColorFilter(Color.argb(180, 33, 150, 243));

    }

}
