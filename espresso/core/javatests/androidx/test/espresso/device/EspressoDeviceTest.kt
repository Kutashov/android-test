/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.test.espresso.device

import android.content.res.Configuration
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.device.action.ScreenOrientation
import androidx.test.espresso.device.action.setFlatMode
import androidx.test.espresso.device.action.setScreenOrientation
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ui.app.LargeViewActivity
import androidx.test.ui.app.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EspressoDeviceTest {
  @get:Rule
  val activityScenario: ActivityScenarioRule<LargeViewActivity> =
    ActivityScenarioRule(LargeViewActivity::class.java)

  @Test
  fun setFlatModeWithPerform_returnsDeviceInteraction() {
    val deviceInteraction = EspressoDevice.onDevice().perform(setFlatMode())

    assertTrue(deviceInteraction is DeviceInteraction)
  }

  @Test
  fun setFlatMode_returnsDeviceInteraction() {
    val deviceInteraction = EspressoDevice.onDevice().setFlatMode()

    assertTrue(deviceInteraction is DeviceInteraction)
  }

  @Test
  fun onDevice_setScreenOrientationToLandscape() {
    EspressoDevice.onDevice().perform(setScreenOrientation(ScreenOrientation.LANDSCAPE))

    assertEquals(
      Configuration.ORIENTATION_LANDSCAPE,
      InstrumentationRegistry.getInstrumentation()
        .getTargetContext()
        .getResources()
        .getConfiguration()
        .orientation
    )

    // Interact with activity.
    onView(withId(R.id.large_view)).check(matches(withText("large view")))
    onView(withId(R.id.large_view)).perform(click())
    onView(withId(R.id.large_view)).check(matches(withText("Ouch!!!")))
  }

  @Test
  fun onDevice_setScreenOrientationToPortrait() {
    EspressoDevice.onDevice().perform(setScreenOrientation(ScreenOrientation.PORTRAIT))

    assertEquals(
      Configuration.ORIENTATION_PORTRAIT,
      InstrumentationRegistry.getInstrumentation()
        .getTargetContext()
        .getResources()
        .getConfiguration()
        .orientation
    )

    // Interact with activity.
    onView(withId(R.id.large_view)).check(matches(withText("large view")))
    onView(withId(R.id.large_view)).perform(click())
    onView(withId(R.id.large_view)).check(matches(withText("Ouch!!!")))
  }
}
