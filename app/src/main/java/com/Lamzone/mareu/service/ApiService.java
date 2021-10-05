package com.Lamzone.mareu.service;

import com.Lamzone.mareu.model.Meeting;

import java.util.List;

public interface ApiService {
    /**
     * Get all my meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Delete a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Get by position
     * @param position
     */
    Meeting getMeetingByPosition(int position);

    /**
     * Filter by room
     * @param location
     */

    List<Meeting> getMeetingByRoom(String location);

    /**
     * Filter by room
     * @param date
     */

    List<Meeting> getMeetingByDate(String date);

    void resetFilter();
}

