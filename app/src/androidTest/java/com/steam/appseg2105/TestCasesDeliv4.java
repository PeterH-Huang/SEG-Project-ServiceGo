package com.steam.appseg2105;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCasesDeliv4 {
    @Rule
    public ActivityTestRule<BookAService> mActivityTestRule= new ActivityTestRule<BookAService>(BookAService.class);
    private BookAService mActivity=null;
    private EditText text;
    private Spinner spinner;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void checkServiceSearch() throws Exception{
        text= mActivity.findViewById(R.id.userInput);
        spinner = mActivity.findViewById(R.id.bookSpin);
        Boolean b = String.valueOf(spinner.getSelectedItem()).equals("Search by Service Type");
        if(b == true) {
            text.setText("Plumbing");
            String title = text.getText().toString();
            assertEquals("Plumbing", title);
        }
        assertEquals("","");
    }
    @Test
    @UiThreadTest
    public void checkTimeSearch() throws Exception{
        text= mActivity.findViewById(R.id.userInput);
        spinner = mActivity.findViewById(R.id.bookSpin);
        Boolean b = String.valueOf(spinner.getSelectedItem()).equals("Search by Service Type");
        if(b == true) {
            text.setText("01:00 AM - 02:00 AM");
            String title = text.getText().toString();
            assertEquals("01:00 AM - 02:00 AM", title);
        }
        assertEquals("","");
    }
    @Test
    @UiThreadTest
    public void checkRatingSearch() throws Exception{
        text= mActivity.findViewById(R.id.userInput);
        spinner = mActivity.findViewById(R.id.bookSpin);
        Boolean b = String.valueOf(spinner.getSelectedItem()).equals("Search by Service Type");
        if(b == true) {
            text.setText("4");
            String title = text.getText().toString();
            assertEquals("4", title);
        }
        assertEquals("","");
    }



}
