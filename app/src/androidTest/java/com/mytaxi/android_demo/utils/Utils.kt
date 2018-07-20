package com.mytaxi.android_demo.utils

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.helpers.Data
import com.mytaxi.android_demo.helpers.Helper
import org.hamcrest.Matchers
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Utils {

    val values = Data()
    val helper = Helper()

    fun Login(username: String, password: String) {

        onView(withId(R.id.edt_username))
                .perform(ViewActions.replaceText(username))

        onView(withId(R.id.edt_password))
                .perform(ViewActions.replaceText(password))

        onView(withId(R.id.btn_login))
                .perform(click())
    }
    fun Logout(){

        onView(withId(R.id.toolbar))
                .check(matches(ViewMatchers.isDisplayed()))

        onView(ViewMatchers.withContentDescription("Open navigation drawer"))
                .perform(click())

        val navigationMenuItemView = onView(
                Matchers.allOf(helper.childAtPosition(
                        Matchers.allOf(withId(R.id.design_navigation_view),
                                helper.childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        1),
                        ViewMatchers.isDisplayed()))
        navigationMenuItemView.perform(click())


        onView(withId(R.id.btn_login))
                .check(matches(withText("Login")))

    }
}