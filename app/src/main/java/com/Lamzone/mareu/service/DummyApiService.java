package com.Lamzone.mareu.service;

import com.Lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyApiService implements ApiService {

    private List<Meeting> Meetings = DummyApiServiceGenerator.generateMeetings();

    @Override
    public void createMeeting(Meeting meeting) {
        Meetings.add(meeting);
    }

    @Override
    public Meeting getMeetingByPosition(int position) {
        return Meetings.get(position);
    }

    @Override
    public void deleteMeeting(Meeting meeting) { Meetings.remove(meeting); }

    @Override
    public List<Meeting> getMeetings() {
        return Meetings;
    }

    @Override
    public List<Meeting> getMeetingByRoom(String location) {
        List<Meeting> roomFilter = new ArrayList<>();
        for (Meeting meeting : Meetings) {
            if (meeting.getRoom().equals(location)) {
                roomFilter.add(meeting);
            }
        }
        return roomFilter;
    }

    @Override
    public List<Meeting> getMeetingByDate(String DatePosition) {
        List<Meeting> dateFilter = new ArrayList<>();
        for (Meeting meeting : Meetings){
            if (meeting.getDate().equals(DatePosition)) {
                dateFilter.add(meeting);
            }
        }
        return dateFilter ;
    }


}
