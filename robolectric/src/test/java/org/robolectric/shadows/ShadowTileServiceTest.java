package org.robolectric.shadows;

import static androidx.test.ext.truth.content.IntentSubject.assertThat;
import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

/** Test for {@link org.robolectric.shadows.ShadowTileService}. */
@RunWith(AndroidJUnit4.class)
@Config(sdk = Build.VERSION_CODES.N)
public final class ShadowTileServiceTest {

  private MyTileService tileService;

  @Before
  public void setUp() {
    tileService = Robolectric.setupService(MyTileService.class);
  }

  @Test
  public void getTile() {
    Tile tile = tileService.getQsTile();
    assertThat(tile).isNotNull();
  }

  @Test
  public void startActivityAndCollapse() {
    tileService.startActivityAndCollapse(
        new Intent().setComponent(new ComponentName("foo.bar", "Activity")));

    assertThat(
            shadowOf((Application) ApplicationProvider.getApplicationContext())
                .getNextStartedActivity())
        .hasComponent(new ComponentName("foo.bar", "Activity"));
  }

  /**
   * A subclass of {@link TileService} for testing, To mimic the way {@link TileService} is used in
   * production.
   */
  static class MyTileService extends TileService {}
}
