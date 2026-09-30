package com.rinkynooble.coldsweatramfix;

import com.mojang.serialization.Codec;
import com.momosoftworks.coldsweat.data.codec.requirement.EntityRequirement;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

@Mod(ColdSweatRamFix.MODID)
public class ColdSweatRamFix {
    public static final String MODID = "coldsweatramfix";
    private static final Logger LOGGER = LogManager.getLogger("Cold Sweat RAM Fix");

    private static final AtomicInteger levelsWrapped = new AtomicInteger();

    public ColdSweatRamFix() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    /** Called by the mixin for each codec level Cold Sweat adds. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object wrapLevel(Object codec) {
        if (!(codec instanceof Codec) || codec instanceof ShortNameCodec) {
            return codec;
        }
        levelsWrapped.incrementAndGet();
        return new ShortNameCodec((Codec) codec, "EntityRequirement");
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        // On the main thread, so reading the codec (which may load Cold Sweat's class for the
        // first time) never races with other mods' parallel setup.
        event.enqueueWork(() -> {
            boolean applied;
            try {
                applied = EntityRequirement.getCodec() instanceof ShortNameCodec;
            } catch (LinkageError e) {
                // A Cold Sweat version without this class or method.
                applied = false;
            }
            if (applied) {
                LOGGER.info("Fix applied: shortened the names of {} Cold Sweat entity requirement codec levels.",
                        levelsWrapped.get());
            } else {
                LOGGER.warn("Fix not applied: this Cold Sweat version builds its entity requirement codec differently. "
                        + "Nothing is changed. If Cold Sweat has fixed its memory use, this mod can be removed.");
            }
        });
    }
}
