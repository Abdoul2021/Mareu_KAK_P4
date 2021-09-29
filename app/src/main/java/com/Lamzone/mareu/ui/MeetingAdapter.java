package com.Lamzone.mareu.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Lamzone.mareu.R;
import com.Lamzone.mareu.di.DI;
import com.Lamzone.mareu.model.Meeting;
import com.Lamzone.mareu.service.ApiService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> Meetings;
    private Context mContext;
    public ApiService apiService = DI.getApiService();

    public MeetingAdapter(List<Meeting> meetings, Context context) {
        Meetings = meetings;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new ViewHolder(view);
    }

    public int getColor(int color) {
        if (color == 1) { return R.drawable.ic_baseline_circle_blanc;
        } else if (color == 2) { return R.drawable.ic_baseline_circle_bleu ;
        } else if (color == 3) { return R.drawable.ic_baseline_circle_gris;
        } else if (color == 4) { return R.drawable.ic_baseline_circle_jaune;
        } else if (color == 5) { return R.drawable.ic_baseline_circle_noir;
        } else if (color == 6) { return R.drawable.ic_baseline_circle_rouge;
        } else if (color == 7) { return R.drawable.ic_baseline_circle_vert;
        } else { return R.drawable.ic_baseline_circle_violet;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Meeting meeting = Meetings.get(position);
        Glide.with(holder.mColor.getContext())
                .load(getColor(meeting.getColors()))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mColor);
        holder.mTopic.setText(meeting.getTopic());
        holder.mDate.setText("Le " + meeting.getDate());
        holder.mRoom.setText(meeting.getRoom());
        holder.mStartTime.setText("De " + meeting.getStartTime());
        holder.mEndTime.setText("à " + meeting.getEndTime());
        holder.mGuests.setText(meeting.getGuests());
        holder.mDeleteButton.setOnClickListener(v -> {
            Meetings.remove(meeting);
            apiService.deleteMeeting(meeting);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "La réunion " + meeting.getTopic() + "a été supprimée", Toast.LENGTH_SHORT).show();
        });

        //Access to details
        holder.mMeetingListItem.setOnClickListener((view ->  {
            Intent intent = new Intent (mContext, MeetingDetailsActivity.class);
            intent.putExtra("position", position);
            mContext.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() { return Meetings.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.color_item)  public ImageView mColor;
        @BindView(R.id.topic)  public TextView mTopic;
        @BindView(R.id.date)  public TextView mDate;
        @BindView(R.id.room)  public TextView mRoom;
        @BindView(R.id.start_time) public TextView mStartTime;
        @BindView(R.id.end_time)  public TextView mEndTime;
        @BindView(R.id.guests) public TextView mGuests;
        @BindView(R.id.delete_button)  public ImageButton mDeleteButton;
        @BindView(R.id.meeting_list_item_layout) ConstraintLayout mMeetingListItem;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

