package de.jardateien.worldicon;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class WorldIconAddon extends LabyAddon<WorldIconConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    this.logger().info("Enabled the Addon");
  }

  @Override
  protected Class<WorldIconConfiguration> configurationClass() {
    return WorldIconConfiguration.class;
  }
}
