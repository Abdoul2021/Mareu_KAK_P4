package com.Lamzone.mareu.service;

import com.Lamzone.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyApiServiceGenerator {
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting(0xffffff00, "Mareu_0", "Salle UN", "7/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; " ),
            new Meeting(0xff00ff00, "Mareu_1", "Salle DEUX", "15/8/2021", "14:30", "15:30", "zip@gmail.com ; zap@live.fr ; zia@gmail.com ; " )
    );

}
