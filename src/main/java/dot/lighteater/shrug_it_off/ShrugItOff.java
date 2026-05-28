package dot.lighteater.shrug_it_off;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ShrugItOff.MODID)
public class ShrugItOff {

    public static final String MODID = "shrugitoff";


    public static final Logger LOGGER = LogUtils.getLogger();

    public ShrugItOff() {
        ModConfig.register();
        MinecraftForge.EVENT_BUS.register(LivingAttackEventHandler.class);
    }
}
