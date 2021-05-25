package carpet.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChatHud.class)
public class ChatHud_chatUpMixin
{
    @Shadow @Final private MinecraftClient client;

    private int getOffset()
    {
        ClientPlayerEntity player = this.client.player;
        if(player == null || player.isCreative() || player.isSpectator()) return 0;
        int offset = player.getArmor()>0?10:0;
        if(player.getAbsorptionAmount()>0) offset += 10;
        return offset;
    }

    @ModifyArg(method = "render", index = 1, at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/systems/RenderSystem;translatef(FFF)V",
            ordinal = 0
    ))
    private float offsetY(float y)
    {
        return y - getOffset();
    }

    @ModifyConstant(method = "getText", constant = @Constant(doubleValue = 40.0), expect = 1)
    private double textBottomOffset(double original)
    {
        return original + getOffset();
    }
}
