package com.Lamzone.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.Lamzone.mareu.R;
import com.Lamzone.mareu.di.DI;
import com.Lamzone.mareu.model.Meeting;
import com.Lamzone.mareu.service.ApiService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.add_meeting_activity) FloatingActionButton addMeetingButton;
    @BindView(R.id.refresh) SwipeRefreshLayout refresh;

    //Declaration
    private Configuration def;
    private String location;

    //Initialisation
    private ArrayList<Meeting> mMeetings = new ArrayList<>();
    private ApiService mApiService = DI.getApiService();
    MeetingAdapter mAdapter = new MeetingAdapter(mMeetings, this);
    private static final String[] rooms = new String[]{"Salle UN", "Salle DEUX", "Salle TROIS", "Salle QUATRE", "Salle CINQ", "Salle SIX", "Salle SEPT", "Salle HUIT", "Salle NEUF", "Salle DIX"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        defineRecyclerView();
        defineSwipeRefreshLayout();
        def = getResources().getConfiguration();
        landscape();

    // Button for adding a meeting
        addMeetingButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddMeetingActivity.class)));
    }

    //SwipeRefresh
    private void defineSwipeRefreshLayout() {
        refresh.setOnRefreshListener(() -> resetFilter(mApiService.getMeetings()));
    }

    //RecyclerView
    private void defineRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mAdapter);
    }

    //Filter
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_by_date:
            case R.id.filter_by_room:
            case R.id.filter_reset:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        //Filter by room
    public void roomFilter(MenuItem Item) {
        int chosenRoom = -1;
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this)
                .setTitle("Salle")
                .setNeutralButton("Annuler", (dialog, which) -> {
                })
                .setPositiveButton("Valider", (dialog, which) -> resetFilter(mApiService.getMeetingByRoom(location)))
                .setSingleChoiceItems(rooms, chosenRoom, (dialog, which) -> location = rooms[which]);
        materialAlertDialogBuilder.show();
    }
        //Filter by date
    public void dateFilter(MenuItem Item) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth1) -> {
            String dateToShow = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
            resetFilter(mApiService.getMeetingByDate(dateToShow));
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFilter(mApiService.getMeetings());
    }
    //Remove filter
    public void noFilter(MenuItem Item) {
        resetFilter(mApiService.getMeetings());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetFilter(List<Meeting> meetings) {
        mMeetings.clear();
        mMeetings.addAll(meetings);
        mAdapter.notifyDataSetChanged();
    }

    //Landscape
    public void landscape() {
        if (def.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMeetings.clear();
            mApiService.getMeetings().clear();
        }
    }

}