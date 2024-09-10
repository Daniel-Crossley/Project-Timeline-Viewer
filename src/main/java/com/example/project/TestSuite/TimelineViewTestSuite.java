package com.example.project.TestSuite;

import com.example.project.Cards.cardEntry;
import com.example.project.Timeline.TimelineView;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;

public class TimelineViewTestSuite {
    TimelineView projectTimeline;

    @BeforeEach
    void setup(){
        List<cardEntry> emptyCardList = Collections.<cardEntry>emptyList();
        projectTimeline = new TimelineView(0,"blankTitle","testDescription","23/11/1963", "23/11/2013", "#003865", 0, emptyCardList);

    }
}
