package com.Lamzone.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.Lamzone.mareu.R;
import com.Lamzone.mareu.di.DI;
import com.Lamzone.mareu.model.Meeting;
import com.Lamzone.mareu.service.ApiService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //EditText
    @BindView(R.id.meeting_topic) EditText topic;
    @BindView(R.id.meeting_guests) EditText guests;
    @BindView(R.id.meeting_date) EditText date;
    @BindView(R.id.meeting_start_time) EditText startTime;
    @BindView(R.id.meeting_end_time) EditText endTime;
    //Button
    @BindView(R.id.meeting_add_button) Button addButton;
    @BindView(R.id.date_btn)  Button dateBtn;
    @BindView(R.id.start_time_btn)  Button startTimeBtn;
    @BindView(R.id.end_time_btn)  Button endTimeBtn;
    @BindView(R.id.meeting_return_button) ImageView mReturnButton;
    //Spinner
    @BindView(R.id.meeting_room)  Spinner room;
    //ImageButton
    @BindView(R.id.meeting_color) ImageButton colorButton;
    //Declaration
    private int mColor = 0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        mApiService = DI.getApiService();

        //Room (spinner)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room.setAdapter(adapter);
        room.setOnItemSelectedListener(this);
        room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Salle")) {
                    Toast.makeText(parent.getContext(), "Aucune salle n'est sélectionnée ", Toast.LENGTH_SHORT).show();
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Salle sélectionnée : " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button (add a meeting)
        addButton.setOnClickListener(v -> createMeeting());

        //Button (add date and time)
        dateBtn.setOnClickListener(this);
        startTimeBtn.setOnClickListener(this);
        endTimeBtn.setOnClickListener(this);

        //Return button
        mReturnButton.setOnClickListener(v -> {
            onBackPressed();
            Toast.makeText(getApplicationContext(), "La réunion n'a pas été créée", Toast.LENGTH_SHORT).show();
        });

        mApiService = DI.getApiService();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        defineColor(mColor);
        Glide.with(this).load(mColor).apply(RequestOptions.circleCropTransform()).placeholder(R.drawable.ic_baseline_ads_click_24).into(colorButton);
        guests.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) { addButton.setEnabled(s.length() > 0);}
        });
    }

    //Color
    @OnClick(R.id.meeting_color)
    void onClickChangeColor(){
        defineColor(new Random().nextInt(8));
    }

    private void defineColor(int color) {
        mColor = color;
        if (color == 1) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_blanc);
        } else if (color == 2) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_bleu);
        } else if (color == 3) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_gris);
        } else if (color == 4) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_jaune);
        } else if (color == 5) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_noir);
        } else if (color == 6) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_rouge);
        } else if (color == 7) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_vert);
        } else if (color == 0) {
            colorButton.setImageResource(R.drawable.ic_baseline_circle_violet);
        }
    }

    //Date and time
    @Override
    public void onClick(View v) {
        if (v == dateBtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == startTimeBtn) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> startTime.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == endTimeBtn) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> endTime.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //Create a meeting
    private void createMeeting() {
        Meeting meeting = new Meeting(
                mColor,
                topic.getEditableText().toString(),
                room.getSelectedItem().toString(),
                date.getEditableText().toString(),
                startTime.getEditableText().toString(),
                endTime.getEditableText().toString(),
                guests.getEditableText().toString());

        if (TextUtils.isEmpty(topic.getText())) { topic.setError("Le sujet doit être renseigné"); return ; }
        if (TextUtils.isEmpty(date.getText())) { date.setError("La date doit être renseignée"); return ; }
        if (TextUtils.isEmpty(startTime.getText())) { startTime.setError("L'heure (début) doit être renseignée"); return ; }
        if (TextUtils.isEmpty(endTime.getText())) { endTime.setError("L'heure(fin) doit être renseignée"); return ; }
        if (TextUtils.isEmpty(guests.getText())) { guests.setError("Les e-mails des participants doivent être renseignés"); return ; }

        mApiService.createMeeting(meeting);
        Toast.makeText(getApplicationContext(), "La réunion a été ajoutée", Toast.LENGTH_SHORT).show();
        finish();
    }

}
