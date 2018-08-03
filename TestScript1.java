package com.piranavenselvahotmail.uiautomatornosourcecode;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import com.microsoft.appcenter.espresso.Factory;
import com.microsoft.appcenter.espresso.ReportHelper;

import java.io.Console;
import java.io.File;
import java.sql.Time;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)

public class TestScript1 {

    private UiDevice mDevice;

    @Rule
    public ReportHelper reportHelper = Factory.getReportHelper();

    @After
    public void TearDown(){
        reportHelper.label("Stopping App");
    }

    public void before() {

        //Initialize UiDevice Instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        assertThat(mDevice, notNullValue());

        //Start from the home screen
        mDevice.pressHome();



    }

    @org.junit.Test
    public void test() throws Exception{

        int  TIMEOUT = 10000;

        openApp("ca.edmonton.etslive");

        Thread.sleep(5000);

        //Log Screen dimensions. Used to test Logcat and click by coordinates
        int height = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).getDisplayHeight();
        int width = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).getDisplayWidth();
        Log.d("MYHeight", "height: " + height);
        Log.d("MYWidth", "width: " + width);

        //Navigate through menu
        clickListViewItem("ETS Route Schedules");
        reportHelper.label("myTestStepLabel 1");
        Thread.sleep(1000);
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
        clickListViewItem("ETS Bus Stop Schedules");
        Thread.sleep(1000);
        reportHelper.label("myTestStepLabel 2 ");
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
        clickListViewItem("Saved Locations");
        Thread.sleep(1000);
        reportHelper.label("myTestStepLabel 3");
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
        clickListViewItem("About");
        Thread.sleep(1000);
        reportHelper.label("myTestStepLabel 4");
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
        clickListViewItem("Help");
        Thread.sleep(1000);

         //Click first tab in Help Menu
        UiObject usingHelpTab =  UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .findObject(new UiSelector().resourceId("UsingHelpId"));
        usingHelpTab.waitForExists(TIMEOUT);
        usingHelpTab.click();
        reportHelper.label("myTestStepLabel 5");


        //Grab exposed window of data
        UiObject usingHelpInfo =  UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
                .findObject(new UiSelector().resourceId("UsingHelpId-help-info"));
        usingHelpInfo.waitForExists(TIMEOUT);
        reportHelper.label("myTestStepLabel 6");

        //count children of window
       // int count = usingHelpInfo.getChildCount();

        //Get 2 paragraphs  of window
        UiObject paragraph1 = usingHelpInfo.getChild(new UiSelector().index(0));
        UiObject paragraph2 = usingHelpInfo.getChild(new UiSelector().index(1));

        //Assert paragraph  one has correct info
        System.out.println("Expected: Select from the options below for detailed instructions on using each feature, including ETS Route Schedules, ETS Bus Stop Schedules, Saved Locations and About.");
        System.out.println("Actual:"+paragraph1.getText().toString());
        assertEquals("Select from the options below for detailed instructions on using each feature, including ETS Route Schedules, ETS Bus Stop Schedules, Saved Locations and About.",paragraph1.getText());
        reportHelper.label("myTestStepLabel 7");
        //Assert paragraph two has correct info
        assertEquals("The “three horizontal lines” icon at the top right of the screen will take you back to the main menu.",paragraph2.getText().toString());
        System.out.println("Expected: The “three horizontal lines” icon at the top right of the screen will take you back to the main menu.");
        System.out.println("Actual:"+paragraph2.getText().toString());
    }


    private void openApp(String packageName) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private UiObject2 waitForObject(BySelector selector) throws InterruptedException {
        UiObject2 object = null;
        int timeout = 30000;
        int delay = 1000;
        long time = System.currentTimeMillis();
        while (object == null) {
            object = mDevice.findObject(selector);
            Thread.sleep(delay);
            if (System.currentTimeMillis() - timeout > time) {
                fail();
            }
        }
        return object;
    }

    public void clickListViewItem(String name) throws UiObjectNotFoundException {
        UiScrollable listView = new UiScrollable(new UiSelector());
        listView.setMaxSearchSwipes(100);
        listView.scrollTextIntoView(name);
        listView.waitForExists(5000);
        UiObject listViewItem = listView.getChildByText(new UiSelector()
                .className(android.widget.TextView.class.getName()), ""+name+"");
        listViewItem.click();
        System.out.println("\""+name+"\" ListView item was clicked.");
    }

}
