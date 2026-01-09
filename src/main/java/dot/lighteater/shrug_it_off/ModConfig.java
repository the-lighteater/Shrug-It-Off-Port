package dot.lighteater.shrug_it_off;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

import java.util.List;

public class ModConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(Type.COMMON, COMMON_SPEC);
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue logDamageSources;
        public final ForgeConfigSpec.BooleanValue logChances;
        public final ForgeConfigSpec.BooleanValue logLogic;

        public final ForgeConfigSpec.BooleanValue useWhitelist;
        public final ForgeConfigSpec.BooleanValue useEntityWhitelist;
        public final ForgeConfigSpec.BooleanValue ignoreUnblockableDamage;
        public final ForgeConfigSpec.BooleanValue ignoreAbsoluteDamage;

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> damageSourceWhitelist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> damageSourceBlacklist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> itemBlacklist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> entityBlacklist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> smallDamageSources;

        public final ForgeConfigSpec.BooleanValue disableSound;

        public final ForgeConfigSpec.BooleanValue enableNewFormula;
        public final ForgeConfigSpec.DoubleValue oldFormulaBase;
        public final ForgeConfigSpec.DoubleValue oldFormulaCap;
        public final ForgeConfigSpec.DoubleValue newFormulaToughnessFactor;

        Common(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            logDamageSources = builder.define("logDamageSources", false);
            logChances = builder.define("logChances", false);
            logLogic = builder.define("logLogic", false);

            useWhitelist = builder.define("useWhitelist", false);
            useEntityWhitelist = builder.define("useEntityWhitelist", false);

            ignoreUnblockableDamage = builder.define("ignoreUnblockableDamage", false);
            ignoreAbsoluteDamage = builder.define("ignoreAbsoluteDamage", false);

            damageSourceWhitelist = builder.defineListAllowEmpty(
                    "damageSourceWhitelist",
                    List.of("mob", "player", "arrow"),
                    o -> o instanceof String
            );

            damageSourceBlacklist = builder.defineListAllowEmpty(
                    "damageSourceBlacklist",
                    List.of("fall", "lava", "drown"),
                    o -> o instanceof String
            );

            itemBlacklist = builder.defineListAllowEmpty(
                    "itemBlacklist",
                    List.of(),
                    o -> o instanceof String
            );

            entityBlacklist = builder.defineListAllowEmpty(
                    "entityBlacklist",
                    List.of(),
                    o -> o instanceof String
            );

            smallDamageSources = builder.defineListAllowEmpty(
                    "smallDamageSources",
                    List.of("cactus", "thorns"),
                    o -> o instanceof String
            );

            disableSound = builder.define("disableSound", false);

            enableNewFormula = builder.define("enableNewFormula", true);

            oldFormulaBase = builder.defineInRange("oldFormulaBase", 0.1, 0.01, 10.0);
            oldFormulaCap = builder.defineInRange("oldFormulaCap", 1.0, 0.01, 1.0);
            newFormulaToughnessFactor =
                    builder.defineInRange("newFormulaToughnessFactor", 20.0, 0.01, 100.0);

            builder.pop();
        }
    }
}
