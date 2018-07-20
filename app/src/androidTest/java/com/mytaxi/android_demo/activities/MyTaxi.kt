package com.mytaxi.android_demo.activities

import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.helpers.Helper
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import com.mytaxi.android_demo.helpers.Data
import com.mytaxi.android_demo.utils.Utils
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.*

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MyTaxi {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule <MainActivity> (MainActivity::class.java)

    private var username="crazydog335"
    private var password ="venture"
    private var wrong_password = "passme123"
    private var wrong_username = "user123"
    val values = Data()
    val utils = Utils()
    val helper = Helper()



    //Clicking on login Button with Empty fields expecting to see error on screen
    @Test
    fun A_EmptyCredentials() {
        SystemClock.sleep(1000)
        onView(withId(R.id.btn_login))
                .check(matches(withText(values.Login)))
                .perform(click())

        onView(withId(R.id.btn_login))
                .perform(click())

        //SystemClock.sleep(1000)

        onView(withId(R.id.snackbar_text))
                .check(matches(withText(R.string.message_login_fail)))

    }

    // Clicking on Login button with empty username first and password after.
    // Expecting to see error message displaying on screen
    @Test
    fun B_OneEmptyField() {
        onView(withId(R.id.edt_username))
                .check(matches(isFocusable()))

        onView(withId(R.id.edt_username))
                .perform(ViewActions.replaceText(username))

        onView(withId(R.id.btn_login))
                .perform(click())

        onView(withId(R.id.edt_username))
                .perform(ViewActions.clearText())

        onView(withId(R.id.edt_password))
                .perform(ViewActions.replaceText(password))

        onView(withId(R.id.btn_login))
                .perform(click())

        onView(withId(R.id.snackbar_text))
                .check(matches(withText(R.string.message_login_fail)))
    }

    // Clicking on Login button with empty username and password after.
    // Expecting to see error message displaying on screen
    @Test
    fun C_InvalidCredentials() {

        utils.Login(wrong_username,wrong_password)

        //SystemClock.sleep(1000)

        onView(withId(R.id.snackbar_text))
                .check(matches(withText(R.string.message_login_fail)))

    }

    //Login with valid credentials
    @Test
    fun D_LoginDValidCredentials() {

        utils.Login(values.username, values.password)

        onView(withId(R.id.searchContainer))
                .check(matches(isDisplayed()))

       //This is actually loging out the application
       utils.Logout()

        onView(withId(R.id.btn_login))
                .check(matches(withText(values.Login)))

    }

    //Login to the app find the driver name from the list and verifies that the phone app
    // is lounched after the phone button is clicked
    @Test
    fun E_findDriver() {

        utils.Login(values.username,values.password)

        //SystemClock.sleep(1000)

        onView(withId(R.id.textSearch))
                .check(matches(isDisplayed()))
                .perform(click())


        onView(ViewMatchers.withId(R.id.textSearch))
                .perform(ViewActions.replaceText(values.searhDriverString))


        onView(withText(values.driver))
                .inRoot(RootMatchers.withDecorView(CoreMatchers.not(CoreMatchers.`is`(activityTestRule.activity.getWindow().getDecorView()))))
                .perform(click())

        onView(withId(R.id.fab)).perform(click())


        val contact_screen = values.contactScreen
        val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.wait(Until.hasObject(By.text(contact_screen)), 2000)
        val UiObject = mDevice.findObject(By.text(contact_screen))
        Assert.assertEquals(contact_screen, UiObject.getText())
        mDevice.pressHome()

    }

    // Logs out after tests execution
    @Test
    fun F_LgOut() {

        SystemClock.sleep(1000)
        utils.Logout()

    }


}


