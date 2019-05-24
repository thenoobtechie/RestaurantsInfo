package com.example.restaurantsinfo

import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule

class MainActivityTest {

    @Rule
    public var activityTestRule : ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
}