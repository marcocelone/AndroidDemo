package com.mytaxi.android_demo.activities


import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.SdkSuppress
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.helpers.Data
import com.mytaxi.android_demo.helpers.Helper
import com.mytaxi.android_demo.utils.Utils
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SdkSuppress(minSdkVersion = 18)
class DriverProfile {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule <MainActivity> (MainActivity::class.java)

    val values = Data()
    var utils = Utils()


    @Test
    fun A_findDriver() {

        utils.Login(values.username,values.password)

        //SystemClock.sleep(1000)

        onView(withId(R.id.textSearch))
                .check(matches(isDisplayed()))
                .perform(click())


        onView(ViewMatchers.withId(R.id.textSearch))
                .perform(replaceText(values.searhDriverString))


        onView(withText(values.driver))
                .inRoot(withDecorView(not(`is`(activityTestRule.activity.getWindow().getDecorView()))))
                .perform(click())

        onView(withId(R.id.fab)).perform(click())


        val contact_screen = values.contactScreen
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.wait(Until.hasObject(By.text(contact_screen)), 2000)
        val UiObject = mDevice.findObject(By.text(contact_screen))
        assertEquals(contact_screen, UiObject.getText())
        mDevice.pressHome()


    }

    @Test
    fun LgOut() {
        utils.Logout()

    }

}
