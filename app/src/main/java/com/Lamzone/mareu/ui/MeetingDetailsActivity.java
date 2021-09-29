package com.Lamzone.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Lamzone.mareu.R;
import com.Lamzone.mareu.di.DI;
import com.Lamzone.mareu.model.Meeting;
import com.Lamzone.mareu.service.ApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingDetailsActivity extends AppCompatActivity {


    @BindView(R.id.return_button) ImageView mReturnButton;
    @BindView(R.id.detail_topic)  TextView mTopic;
    @BindView(R.id.detail_room)  TextView mRoom;
    @BindView(R.id.detail_date)  TextView mDate;
    @BindView(R.id.detail_meet_time)  TextView mMeetTime;
    @BindView(R.id.detail_guests) TextView mGuests;

    private Meeting mMeeting;
    private ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        ButterKnife.bind(this);

        int position = getIntent().getIntExtra("position", 0);
        mApiService = DI.getApiService();

        mMeeting = mApiService.getMeetingByPosition(position);
        mTopic.setText(mMeeting.getTopic());
        mRoom.setText(mMeeting.getRoom());
        mDate.setText(mMeeting.getDate());
        mMeetTime.setText(mMeeting.getStartTime() + " à " + mMeeting.getEndTime());
        mGuests.setText(mMeeting.getGuests());


        //Return button
        mReturnButton.setOnClickListener(v -> {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "Tu quittes la page de la réunion " + mMeeting.getTopic(), Toast.LENGTH_SHORT).show();
        });
    }

}