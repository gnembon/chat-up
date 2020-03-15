package carpet.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatHud.class)
public class ChatHud_chatUpMixin
{
    public final int OFFSET = 10;

    @Redirect(method = "render", remap = false, at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;translatef(FFF)V",
            ordinal = 0
    ))
    private void moveUp(float x, float y, float z)
    {
        RenderSystem.translatef(x, y-OFFSET, z);
    }

    @ModifyConstant(method = "getText", constant = @Constant(doubleValue = 40.0), expect = 1)
    private double textBottomOffset(double original)
    {
        return original+OFFSET;
    }
}
