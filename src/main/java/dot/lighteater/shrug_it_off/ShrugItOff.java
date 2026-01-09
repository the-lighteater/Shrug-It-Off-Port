package dot.lighteater.shrug_it_off;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ShrugItOff.MODID)
public class ShrugItOff {

    public static final String MODID = "shrugitoff";

    public ShrugItOff() {
        ModConfig.register();
        MinecraftForge.EVENT_BUS.register(LivingHurtEventHandler.class);
    }
}
