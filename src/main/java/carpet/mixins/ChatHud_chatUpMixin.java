package carpet.mixins;

import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChatHud.class)
public class ChatHud_chatUpMixin
{
    private static final int CHAT_UP_OFFSET = 20;

    @ModifyArg(method = "render", index = 1, at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;translatef(FFF)V",
            ordinal = 0
    ))
    private float offsetY(float y)
    {
        return y- CHAT_UP_OFFSET;
    }

    @ModifyConstant(method = "getText", constant = @Constant(doubleValue = 40.0), expect = 1)
    private double textBottomOffset(double original)
    {
        return original+ CHAT_UP_OFFSET;
    }
}
