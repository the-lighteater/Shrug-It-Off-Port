package dot.lighteater.shrug_it_off;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ShrugItOff.MODID)
public class LivingAttackEventHandler {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {

        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide()) return;

        float amount = event.getAmount();
        if (amount <= 0) return;

        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();

        /* ---------------- ENTITY FILTER ---------------- */

        ResourceLocation entityId =
                victim.getType().builtInRegistryHolder().key().location();

        boolean listed = dot.lighteater.shrug_it_off.ModConfig.COMMON.entityBlacklist.get()
                .contains(entityId.toString());

        if (dot.lighteater.shrug_it_off.ModConfig.COMMON.useEntityWhitelist.get() && !listed) return;
        if (!dot.lighteater.shrug_it_off.ModConfig.COMMON.useEntityWhitelist.get() && listed) return;

        /* ---------------- ITEM FILTER ---------------- */

        if (attacker instanceof LivingEntity livingAttacker) {
            ItemStack stack = livingAttacker.getMainHandItem();
            if (!stack.isEmpty()) {
                String itemId = stack.getItem()
                        .builtInRegistryHolder().key().location().toString();
                if (dot.lighteater.shrug_it_off.ModConfig.COMMON.itemBlacklist.get().contains(itemId)) return;
            }
        }

        /* ---------------- DAMAGE SOURCE FILTER ---------------- */

        String damageType = source.getMsgId();

        if ((dot.lighteater.shrug_it_off.ModConfig.COMMON.useWhitelist.get() &&
                !dot.lighteater.shrug_it_off.ModConfig.COMMON.damageSourceWhitelist.get().contains(damageType))
                ||
                (!dot.lighteater.shrug_it_off.ModConfig.COMMON.useWhitelist.get() &&
                        dot.lighteater.shrug_it_off.ModConfig.COMMON.damageSourceBlacklist.get().contains(damageType))) {
            return;
        }

        /* ---------------- TOUGHNESS ---------------- */

        double toughness = victim.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        if (toughness <= 0) return;

        /* ---------------- BLOCK CHANCE ---------------- */

        double blockChance;
        if (dot.lighteater.shrug_it_off.ModConfig.COMMON.enableNewFormula.get()) {
            blockChance =
                    (2 / (1 + Math.exp(
                            (-0.001 * dot.lighteater.shrug_it_off.ModConfig.COMMON.newFormulaToughnessFactor.get() * toughness)
                                    / (0.05 * amount)
                    ))) - 1;
        } else {
            blockChance = Math.min(
                    ModConfig.COMMON.oldFormulaBase.get() * toughness / amount,
                    ModConfig.COMMON.oldFormulaCap.get()
            );
        }

        double rng = victim.getRandom().nextDouble();
        if (rng > blockChance) return;

        /* ---------------- UNBLOCKABLE CHECK ---------------- */

        if ((!ModConfig.COMMON.ignoreUnblockableDamage.get()
                && source.is(DamageTypeTags.BYPASSES_ARMOR))
                || (!ModConfig.COMMON.ignoreAbsoluteDamage.get()
                && source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
            return;
        }

        /* ---------------- BLOCK DAMAGE ---------------- */

        event.setCanceled(true);

        /* ---------------- SOUND ---------------- */

        if (!dot.lighteater.shrug_it_off.ModConfig.COMMON.disableSound.get() &&
                !dot.lighteater.shrug_it_off.ModConfig.COMMON.smallDamageSources.get().contains(damageType)) {

            victim.level().playSound(
                    null,
                    victim.blockPosition(),
                    SoundEvents.ANVIL_LAND,
                    SoundSource.BLOCKS,
                    Math.min(amount / 5.0F, 1.0F),
                    Math.max(Math.min(5.0F / amount, 2.0F), 0.5F)
            );
        }
    }
}
