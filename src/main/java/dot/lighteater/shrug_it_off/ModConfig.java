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

        public final ForgeConfigSpec.ConfigValue<List<? extends String>> soundeventString;

        public final ForgeConfigSpec.ConfigValue<Float> volumeModifier;
        public final ForgeConfigSpec.ConfigValue<Float> pitchModifier;

        public final ForgeConfigSpec.DoubleValue soundChance;

        Common(ForgeConfigSpec.Builder builder) {

            builder.push("general");

            logDamageSources = builder.comment("Logs all Damage Sources when event fires.")
                    .define("logDamageSources", false);
            logChances = builder.comment("Logs all chances when event fires.")
                    .define("logChances", false);
            logLogic = builder.comment("Logs all logic when event fires.")
                    .define("logLogic", false);

            useWhitelist = builder.comment("Toggles using whitelist")
                    .define("useWhitelist", false);
            useEntityWhitelist = builder.comment("Toggles Entity Whitelist")
                    .define("useEntityWhitelist", false);

            ignoreUnblockableDamage = builder.comment("Toggles ignoring unblockable damage")
                    .define("ignoreUnblockableDamage", false);
            ignoreAbsoluteDamage = builder.comment("Toggles ignoring absolute damage")
                    .define("ignoreAbsoluteDamage", false);

            damageSourceWhitelist = builder.comment("Damage Source Whitelist")
                    .defineListAllowEmpty(
                    "damageSourceWhitelist",
                    List.of("mob", "player", "arrow"),
                    o -> o instanceof String
            );

            damageSourceBlacklist = builder.comment("Damage Source Blacklist")
                    .defineListAllowEmpty(
                    "damageSourceBlacklist",
                    List.of("fall", "lava", "drown"),
                    o -> o instanceof String
            );

            itemBlacklist = builder.comment("Item Blacklist")
                    .defineListAllowEmpty(
                    "itemBlacklist",
                    List.of(),
                    o -> o instanceof String
            );

            entityBlacklist = builder.comment("Entity Blacklist")
                    .defineListAllowEmpty(
                    "entityBlacklist",
                    List.of(),
                    o -> o instanceof String
            );

            smallDamageSources = builder.comment("Small Damage Sources")
                    .defineListAllowEmpty(
                    "smallDamageSources",
                    List.of("cactus", "thorns"),
                    o -> o instanceof String
            );

            disableSound = builder.comment("Disables Sound")
                    .define("disableSound", false);

            enableNewFormula = builder.comment("Uses new formula")
                    .define("enableNewFormula", true);

            oldFormulaBase = builder.comment("Old formula base")
                    .defineInRange("oldFormulaBase", 0.1, 0.01, 10.0);
            oldFormulaCap = builder.comment("Old formula cap")
                    .defineInRange("oldFormulaCap", 1.0, 0.01, 1.0);
            newFormulaToughnessFactor =
                    builder.comment("New formula toughness factor")
                            .defineInRange("newFormulaToughnessFactor", 20.0, 0.01, 100.0);

            soundeventString = builder.comment("Define a specific sound to play during deflection.")
                            .defineListAllowEmpty(
                                    "soundEventString",
                                    List.of("minecraft:block.anvil.fall"),
                                    o -> o instanceof String
                                    );

            volumeModifier = builder.comment("The inverse intensity of the volume, lower value means higher sound")
                            .define("volumeModifier", 5.0f);

            pitchModifier = builder.comment("The intensity of the pitch, higher value means higher pitch")
                    .define("pitchModifier", 5.0f);

            soundChance = builder.comment("The chance of a sound not playing")
                    .defineInRange("soundChance", 0.0, 0.0, 1.0);

            builder.pop();
        }
    }
}