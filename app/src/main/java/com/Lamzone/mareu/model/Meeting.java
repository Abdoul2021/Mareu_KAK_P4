package com.Lamzone.mareu.model;


/**
 * Model object representing a Meeting
 */
public class Meeting {

    /** Colors */
    private int colors;

    /** Topic */
    private String topic;

    /** Room */
    private String room;

    /** Date */
    private String date;

    /** Start time */
    private String startTime;

    /** End time  */
    private String endTime;

    /** Guests */
    private String guests ;

    /** Constructor */
    public Meeting(int colors, String topic, String room, String date, String startTime, String endTime, String guests) {
        this.colors= colors;
        this.topic = topic;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.guests = guests;
    }

    public int getColors() { return colors; }
    public void setColors(int colors) { this.colors = colors; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getGuests() { return guests; }
    public void setGuests(String guests) { this.guests = guests; }
}


