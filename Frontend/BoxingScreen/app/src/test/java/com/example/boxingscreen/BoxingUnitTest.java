package com.example.boxingscreen;


import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import androidx.lifecycle.viewmodel.CreationExtras;


import com.example.boxingscreen.MainActivity;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;




import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class BoxingUnitTest {



    @Test
    public void JabisCorrect() {
        MainActivity activity = new MainActivity();

        activity.moves = "Jab";
        activity.oppMoves = "Block";

        assertEquals(activity.lose, true);

        activity.moves = "Block";
        activity.oppMoves = "Jab";

        assertEquals(activity.win, true);

    }
    @Test
    public void HealthisCorrect() {
        MainActivity activity = new MainActivity();

        activity.moves = "Jab";
        activity.oppMoves = "Block";

        assertEquals(activity.oppHealth, 960);

        activity.moves = "Block";
        activity.oppMoves = "Jab";
        assertEquals(activity.health, 960);

    }

    @Test
    public void BlockisCorrect() {
        MainActivity activity = new MainActivity();

        activity.moves = "Block";
        activity.oppMoves = "Jab";

        assertEquals(activity.oppHealth, 960);

        activity.moves = "Jab";
        activity.oppMoves = "Block";
        assertEquals(activity.health, 960);

    }

    @Test
    public void UpperisCorrect() {
        MainActivity activity = new MainActivity();

        activity.moves = "Upper";
        activity.oppMoves = "Block";

        assertEquals(activity.oppHealth, 960);

        activity.moves = "Block";
        activity.oppMoves = "Upper";
        assertEquals(activity.health, 960);

        onView(withId(R.id.Upper))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.submit2)).perform(click());


    }

}

