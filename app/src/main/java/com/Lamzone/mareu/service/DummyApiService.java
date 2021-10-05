package com.Lamzone.mareu.service;

import com.Lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyApiService implements ApiService {

    private List<Meeting> mMeetings = DummyApiServiceGenerator.generateMeetings();

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    /**
     * {@inheritDoc}
     * @param position
     */
    @Override
    public Meeting getMeetingByPosition(int position) {
        return mMeetings.get(position);
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) { mMeetings.remove(meeting); }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    /**
     * {@inheritDoc}
     * @param location
     */
    @Override
    public List<Meeting> getMeetingByRoom(String location) {
        List<Meeting> roomFilter = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (meeting.getRoom().equals(location)) {
                roomFilter.add(meeting);
            }
        }
        return roomFilter;
    }

    /**
     * {@inheritDoc}
     * @param datePosition
     */
    @Override
    public List<Meeting> getMeetingByDate(String datePosition) {
        List<Meeting> dateFilter = new ArrayList<>();
        for (Meeting meeting : mMeetings){
            if (meeting.getDate().equals(datePosition)) {
                dateFilter.add(meeting);
            }
        }
        return dateFilter ;
    }

    @Override
    public void resetFilter() {
        mMeetings = DummyApiServiceGenerator.generateMeetings();
    }
}
