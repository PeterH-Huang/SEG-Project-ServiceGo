package com.steam.appseg2105;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
public class TestCasesAddAService {
    @Rule
    public ActivityTestRule<AddAServiceActivity> mActivityTestRule= new ActivityTestRule<AddAServiceActivity>(AddAServiceActivity.class);
    private AddAServiceActivity mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void checkServiceTitle() throws Exception{
        text= mActivity.findViewById(R.id.serviceTitle);
        text.setText("plumbing");
        String title= text.getText().toString();
        assertEquals("plumbing",title);
    }
    @Test
    @UiThreadTest
    public void checkHourlyWage() throws Exception{
        text= mActivity.findViewById(R.id.hourlyWage);
        text.setText("14.20");
        String hourly= text.getText().toString();
        assertEquals("14.20",hourly);
    }



}



