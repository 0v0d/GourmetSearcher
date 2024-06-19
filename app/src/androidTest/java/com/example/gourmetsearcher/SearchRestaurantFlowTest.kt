package com.example.gourmetsearcher

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.example.gourmetsearcher.ui.viewholder.RangeListViewHolder
import com.example.gourmetsearcher.ui.viewholder.RestaurantListViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** レストラン検索のフローをテストする */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchRestaurantFlowTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
        )

    /** テキストを入力し、範囲を選択して、レストランの画像が表示されることを確認する*/
    @Test
    fun testInputTextSelectRangeAndCheckImageDisplayed() {
        val text = "ラーメン"
        // InputKeyWordFragmentでテキストを入力する
        onView(withId(R.id.searchInputEditText)).perform(
            replaceText(text),
        )

        // RangeListをクリックして、SearchLocationFragmentに遷移する
        onView(withId(R.id.rangeListRecyclerView))
            .perform(
                actionOnItemAtPosition<RangeListViewHolder>(
                    4,
                    click(),
                ),
            )

        // RestaurantFragmentに遷移し、リストのアイテムをクリックする
        onView(withId(R.id.resultListRecyclerView))
            .waitShown(R.id.icon).perform(
                actionOnItemAtPosition<RestaurantListViewHolder>(
                    1,
                    click(),
                ),
            )

        // RestaurantDetailFragmentに遷移し、画像が表示されることを確認する
        onView(withId(R.id.restaurantImageView))
            .check(
                matches(isDisplayed()),
            ).waitShown(R.id.restaurantImageView)
    }

    /**
     * ViewInteractionが表示されるまで待機する
     * @param resId リソースID
     * @param timeout タイムアウト時間
     * @return ViewInteraction
     */
    private fun ViewInteraction.waitShown(
        @IdRes resId: Int,
        timeout: Long = 50_000,
    ): ViewInteraction {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val bySelector =
            By.res(context.packageName, context.resources.getResourceEntryName(resId))
        val searchCondition = Until.findObject(bySelector)
        uiDevice.wait(searchCondition, timeout)
        return this
    }
}
