package net.kav.kav_better_death.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kav.kav_better_death.Kav_better_death;
import net.kav.kav_better_death.utils.IEntityDataSaver;
import net.kav.kav_better_death.utils.Soul_counterData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class RenderOverlayMixin extends DrawableHelper {

    private static final Identifier DEATH_SCREEN= new Identifier(Kav_better_death.MODID,"textures/overlay/screen_death.png");

    @Inject(at = @At("TAIL"), method = "renderStatusBars")
    private void renderStatusBars(CallbackInfo info) {
        MatrixStack matrixStack = new MatrixStack();
        MinecraftClient client = MinecraftClient.getInstance();
        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();
        int x =0;
        int y= 0;
        if(client!=null)
        {
            int width =client.getWindow().getScaledWidth();
            int heigth =client.getWindow().getScaledHeight();
            x=width/2;
            y=heigth;
        }


        if(Soul_counterData.getsoul(((IEntityDataSaver) client.player)))
        {

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
            RenderSystem.setShaderTexture(0, DEATH_SCREEN);

            // Draw the image to cover the entire screen
            drawTexture(new MatrixStack(), 0, 0, 0, 0, 1920, 1080, 1920, 1080);
        }

    }

    }


