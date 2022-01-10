package com.ikymasie.clearscore


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val view = onView(
            allOf(
                withId(R.id.dv_donutView),
                withParent(
                    allOf(
                        withId(R.id.container_outline),
                        withParent(withId(R.id.content_container))
                    )
                ),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tv_topCircleText), withText("Your credit score is"),
                withParent(
                    allOf(
                        withId(R.id.text_container),
                        withParent(withId(R.id.container_outline))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Your credit score is")))

        val view2 = onView(
            allOf(
                withId(R.id.tv_mainCircleText), withContentDescription("514"),
                withParent(
                    allOf(
                        withId(R.id.text_container),
                        withParent(withId(R.id.container_outline))
                    )
                ),
                isDisplayed()
            )
        )
        view2.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_bottomCircleText), withText("out of 700"),
                withParent(
                    allOf(
                        withId(R.id.text_container),
                        withParent(withId(R.id.container_outline))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        val donutProgressView = onView(
            allOf(
                withId(R.id.dv_donutView),
                childAtPosition(
                    allOf(
                        withId(R.id.container_outline),
                        childAtPosition(
                            withId(R.id.content_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        donutProgressView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
