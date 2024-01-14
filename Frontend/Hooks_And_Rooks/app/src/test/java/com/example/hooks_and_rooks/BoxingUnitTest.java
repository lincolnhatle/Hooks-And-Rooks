package com.example.hooks_and_rooks;

import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.hooks_and_rooks.api.*;

public class BoxingUnitTest {
    @Test
    public void BlockIsCorrect() {
        BoxingActivity activity = new BoxingActivity();
        activity.move = "Block";
        activity.oppmove = "Jab";

        assertEquals(activity.win, true);

        activity.move = "Jab";
        activity.oppmove = "Block";

        assertEquals(activity.lose, true);

    }

    @Test
    public void JabIsCorrect() {
        BoxingActivity activity = new BoxingActivity();
        activity.move = "Jab";
        activity.oppmove = "Upper";

        assertEquals(activity.win, true);

        activity.move = "Upper";
        activity.oppmove = "Jab";

        assertEquals(activity.lose, true);
    }
    @Test
    public void UpperIsCorrect() {
        BoxingActivity activity = new BoxingActivity();
        activity.move = "Upper";
        activity.oppmove = "Block";

        assertEquals(activity.win, true);

        activity.move = "Block";
        activity.oppmove = "Upper";

        assertEquals(activity.lose, true);
    }

    @Test
    public void GameHpIsCorrect() {
        BoxingActivity activity = new BoxingActivity();
        activity.win = true;
        activity.lose = false;

        assertEquals(activity.oppHealth, 960);

        activity.win = false;
        activity.lose = true;

        assertEquals(activity.health, 960);
    }
}

