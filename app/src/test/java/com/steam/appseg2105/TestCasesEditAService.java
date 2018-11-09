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

public class TestCasesEditAService {
    @Rule
    public ActivityTestRule<EditAServiceActivity> mActivityTestRule= new ActivityTestRule<EditAServiceActivity>(EditAServiceActivity.class);
    private EditAServiceActivity mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void checkServiceTitle() throws Exception{
        text= mActivity.findViewById(R.id.editServiceHourly);
        text.setText("14.50");
        String hourly= text.getText().toString();
        assertNotEquals("14.50",hourly);
    }
    @Test
    @UiThreadTest
    public void checkHourlyWage() throws Exception{
        text= mActivity.findViewById(R.id.serviceTitleEdit);
        text.setText("electrician");
        String title= text.getText().toString();
        assertNotEquals("electrician",title);
    }
    @Test
    @UiThreadTest
    public void checkCorrectHourly() throws Exception{
        text= mActivity.findViewById(R.id.editServiceHourly);
        text.setText("aaaa");
        String hourly= text.getText().toString();
        try{
            Double numberHourly = Double.parseDouble(hourly);
        }catch(Exception e){
            assertFalse(false);
        }
    }



}
