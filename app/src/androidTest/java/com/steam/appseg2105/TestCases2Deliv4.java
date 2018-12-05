package com.steam.appseg2105;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestCases2Deliv4 {
    @Rule
    public ActivityTestRule<HomeOwner> mActivityTestRule = new ActivityTestRule<HomeOwner>(HomeOwner.class);
    private HomeOwner mActivity = null;
    private EditText text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void setRating() throws Exception {
        text = mActivity.findViewById(R.id.serviceProviderRating);
        text.setText("3");
        String title = text.getText().toString();
        assertEquals("3", title);

    }

    @Test
    @UiThreadTest
    public void checkRatingInt() throws Exception {
        text = mActivity.findViewById(R.id.serviceProviderRating);
        text.setText("3");
        try {
            int texty = Integer.parseInt(text.getText().toString());
        } catch (Exception e) {
            assertFalse(false);
        }

    }
    @Test
    @UiThreadTest
    public void checkComment() throws Exception {
        text = mActivity.findViewById(R.id.comment);
        text.setText("This service is bad");
        String title = text.getText().toString();
        assertEquals("This service is bad", title);
    }
    @Test
    @UiThreadTest
    public void changeServiceTitle() throws Exception {
        text = mActivity.findViewById(R.id.serviceTitleHO);
        text.setText("Electrician");
        String title = text.getText().toString();
        assertEquals("Electrician", title);
    }
    @Test
    @UiThreadTest
    public void checkNoIntServiceTitle() throws Exception {
        text = mActivity.findViewById(R.id.serviceTitleHO);
        text.setText("Electrician");
        String title = text.getText().toString();
        Boolean b = title.matches("\\D+");
        assertEquals(true,b);
    }
    @Test
    @UiThreadTest
    public void checkIntRating() throws Exception {
        text = mActivity.findViewById(R.id.serviceTitleHO);
        text.setText("1");
        String title = text.getText().toString();
        Boolean b = title.matches("\\D+");
        assertEquals(false,b);
    }
    @Test
    @UiThreadTest
    public void checkIntTitle() throws Exception {
        text = mActivity.findViewById(R.id.comment);
        text.setText("1");
        String title = text.getText().toString();
        Boolean b = title.matches("\\D+");
        assertEquals(false,b);
    }





}
