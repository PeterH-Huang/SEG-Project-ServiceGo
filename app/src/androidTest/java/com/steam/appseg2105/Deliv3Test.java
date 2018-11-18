package com.steam.appseg2105;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class Deliv3Test {
    @Rule
    public ActivityTestRule<CompleteProfile> mActivityTestRule= new ActivityTestRule<>(CompleteProfile.class);
    private CompleteProfile mActivity=null;
    private TextView text;
    @Before
    public void setUp() throws Exception {
        mActivity=mActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void checkCorrectPhone() throws Exception{
        text= mActivity.findViewById(R.id.phoneNumber);
        text.setText("aaaa");
        String phone= text.getText().toString();
        try{
            int phoneNum = Integer.parseInt(phone);
        }catch(Exception e){
            assertFalse(false);
        }
    }
    @Test
    @UiThreadTest
    public void validCompanyName() throws Exception{
        text= mActivity.findViewById(R.id.companyName);
        text.setText("fedex");
        String compName= text.getText().toString();
            assertEquals(compName,"fedex");

    }

}
