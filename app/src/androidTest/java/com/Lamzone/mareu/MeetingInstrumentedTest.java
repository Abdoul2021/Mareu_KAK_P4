package com.Lamzone.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.Lamzone.mareu.RecyclerViewItemCountAssertion.withItemCount;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.Lamzone.mareu.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    private static final int ITEMS_COUNT = 2;


    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 1
        onView(ViewMatchers.withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 1
        onView(ViewMatchers.withId(R.id.recyclerview)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_ShouldNotBeEmpty() {
        onView(withId(R.id.recyclerview)).check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we click on an item, the details screen is launched
     */
    @Test
    public void myMeetingDetailsActivity_isLaunchedWhenWeClickOnAnItem() {
        // When perform a click on an item
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : MeetingDetailsActivity is launched
        onView(withId(R.id.activity_meeting_details));
    }

    /**
     * When starting the new screen, the TextView indicating the topic of the meeting must be correctly filled
     */
    @Test
    public void textViewIndicatingTheTopic_isCorrectlyFilled() {
        // When perform a click on an item
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Then : MeetingDetailsActivity is launched
        onView(withId(R.id.activity_meeting_details));
        //The TextView is correctly filled
        onView(withId(R.id.detail_topic))
                .check(matches(withText("Mareu_0")));
    }

    /**
     * When we add an item, the item is shown
     */
    @Test
    public void checkIfAddMeetingIsRunning() {
        onView(withId(R.id.add_meeting_activity)).perform(click());
        onView(withId(R.id.meeting_color)).perform(click());
        onView(withId(R.id.meeting_room)).perform(click());
        onView(withText("Salle DEUX")).perform(click());
        onView(withId(R.id.meeting_topic)).perform(typeText("Mareu_2"));
        onView(withId(R.id.date_btn)).perform(click());
        onView(withId(R.id.meeting_date)).perform();
        onView(withText("OK")).perform(click());
        onView(withId(R.id.start_time_btn)).perform(click());
        onView(withId(R.id.meeting_start_time)).perform();
        onView(withText("OK")).perform(click());
        onView(withId(R.id.end_time_btn)).perform(click());
        onView(withId(R.id.meeting_end_time)).perform();
        onView(withText("OK")).perform(click());
        onView(withId(R.id.meeting_guests)).perform(typeText("zip@gmail.com ; zap@live.fr"));
        onView(withId(R.id.meeting_add_button)).check(matches(isDisplayed()));
    }

    @Test
    public void myMeetingList_shouldShowMeeting_basedOnFilters() {
        onView(ViewMatchers.withId(R.id.recyclerview)).check(matches(hasChildCount(1)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par salle")).perform(click());
        onView(withText("Salle UN")).perform(click());
        onView(withText("VALIDER")).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview)).check(matches(hasChildCount(1)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtrer par date")).perform(click());
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview)).check(matches(hasChildCount(0)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Aucun filtre")).perform(click());
        onView(ViewMatchers.withId(R.id.recyclerview)).check(matches(hasChildCount(1)));
    }

}