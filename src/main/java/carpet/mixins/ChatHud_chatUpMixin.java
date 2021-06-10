package carpet.mixins;

import carpet.config.Config;
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

        if(Config.disableMod == true) {
            return 0;
        } else {

            if(Config.dynamicOffset == true) {
                if (player.getArmor() > 0) {
                    return Config.extraOffset + 10;
                } else {
                    return Config.extraOffset;
                }
            } else if(Config.dynamicOffset == false) {
                int offset = Config.extraOffset + 10;
                return offset;

            //TODO: Fix Absorbtion so it gets effected by ANY offset (not just extraOffset)
//                if (player.getAbsorptionAmount()>0){
//                    int offset =+ Config.extraOffset;
//                    return offset;
//                }
            }
        }
        return 0;
    }

    @ModifyArg(method = "render", index = 1, at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V",
            ordinal = 0
    ))
    private double offsetY(double y)
    {
        return y - getOffset();
    }

    @ModifyConstant(method = "getText", constant = @Constant(doubleValue = 40.0))
    private double textBottomOffset(double original)
    {
        return original + getOffset();
    }
}
