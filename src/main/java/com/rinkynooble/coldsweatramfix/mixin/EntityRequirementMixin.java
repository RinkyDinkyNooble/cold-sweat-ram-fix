package com.rinkynooble.coldsweatramfix.mixin;

import com.momosoftworks.coldsweat.data.codec.requirement.EntityRequirement;
import com.rinkynooble.coldsweatramfix.ColdSweatRamFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Cold Sweat builds its entity requirement codec in levels. {@code addCodecStack()} creates the
 * next level and adds it to a list, and each level refers to the one before it three times
 * (vehicle, passenger and target). This gives every level a short name before it is stored.
 */
@Mixin(value = EntityRequirement.class, remap = false)
public abstract class EntityRequirementMixin {
    // require = 0: if a future Cold Sweat changes this method, the game still loads and the
    // fix simply does nothing. ColdSweatRamFix logs which of the two happened.
    @ModifyArg(
            method = "addCodecStack",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            index = 0,
            require = 0,
            remap = false
    )
    private static Object coldsweatramfix$shortenName(Object codec) {
        return ColdSweatRamFix.wrapLevel(codec);
    }
}
