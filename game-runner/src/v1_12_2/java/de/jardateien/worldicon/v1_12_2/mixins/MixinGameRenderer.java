package de.jardateien.worldicon.v1_12_2.mixins;

import de.jardateien.worldicon.WorldIconAddon;
import de.jardateien.worldicon.WorldIconConfiguration;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinGameRenderer {

  @Unique
  private boolean updateWorldIcon$screenshot = false;

  @Redirect(
      method = "updateCameraAndRender",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/integrated/IntegratedServer;isWorldIconSet()Z")
  )
  private boolean tryTakeScreenshotIfNeeded(IntegratedServer server) {
    return this.updateWorldIcon$screenshot;
  }

  @Redirect(
      method = "createWorldIcon",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/integrated/IntegratedServer;isWorldIconSet()Z")
  )
  private boolean hasWorldScreenshot(IntegratedServer instance) {
    return this.updateWorldIcon$screenshot && WorldIconAddon.instance.configuration().enabled().get();
  }

  @Inject(
      method = "createWorldIcon",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ScreenShotHelper;createScreenshot(IILnet/minecraft/client/shader/Framebuffer;)Ljava/awt/image/BufferedImage;")
  )
  private void takeAutoScreenshot(CallbackInfo ci) {
    this.updateWorldIcon$screenshot = true;
  }

  @Inject(
      method = "resetData",
      at = @At("HEAD")
  )
  private void resetData(CallbackInfo ci) {
    this.updateWorldIcon$screenshot = false;
  }


}