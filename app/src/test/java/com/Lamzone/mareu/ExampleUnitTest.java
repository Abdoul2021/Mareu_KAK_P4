package com.Lamzone.mareu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import com.Lamzone.mareu.di.DI;
import com.Lamzone.mareu.model.Meeting;
import com.Lamzone.mareu.service.ApiService;
import com.Lamzone.mareu.service.DummyApiServiceGenerator;

import java.util.List;

/**
 * Unit test on meeting service
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {

    private ApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService(); }

    // Get a meeting with success
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyApiServiceGenerator.DUMMY_MEETINGS;
        assertEquals(meetings.size(), expectedMeetings.size());
        assertEquals(meetings.get(1), expectedMeetings.get(1));
    }

    //Add a meeting with success
    @Test
    public void addMeetingWithSuccess() {
        Meeting meeting = service.getMeetings().get(0);
        service.createMeeting(meeting);
        assertFalse(service.getMeetings().isEmpty());
    }

    // Delete a meeting by position with success
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    // Get a meeting by position with success
    @Test
    public void getMeetingByPositionWithSuccess(){
        int position = 1;
        Meeting expectedNeighbour = service.getMeetingByPosition(position);
        Meeting meeting = service.getMeetings().get(position);
        assertEquals(expectedNeighbour.getTopic(), meeting.getTopic());
    }

    //Filter meetings by room with success
    @Test
    public void filterByRoomMeetingsWithSuccess() {
        Meeting mareu_salle = new Meeting(0xffffff00, "Mareu_salle", "Salle UN", "7/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; ");
        service.createMeeting(mareu_salle);
        assertTrue(service.getMeetingByRoom(mareu_salle.getRoom()).contains(mareu_salle));
    }

    //Filter meetings by date with success
    @Test
    public void filterByDateMeetingsWithSuccess() {
        Meeting mareu_date = new Meeting(0xffffff00, "Mareu_date", "Salle UN", "8/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; " );
        service.createMeeting(mareu_date);
        assertTrue(service.getMeetingByDate(mareu_date.getDate()).contains(mareu_date));
    }

    //Reset filter with success
    @Test
    public void resetFilterWithSuccess() {
        Meeting mareu_date = new Meeting(0xffffff00, "Mareu_date", "Salle UN", "8/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; " );
        service.createMeeting(mareu_date);
        Meeting mareu_salle = new Meeting(0xffffff00, "Mareu_salle", "Salle UN", "7/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; ");
        service.createMeeting(mareu_salle);
        service.resetFilter();
        assertFalse(service.getMeetingByRoom(mareu_salle.getRoom()).contains(mareu_salle));
        assertFalse(service.getMeetingByDate(mareu_date.getDate()).contains(mareu_date));

    }

}
