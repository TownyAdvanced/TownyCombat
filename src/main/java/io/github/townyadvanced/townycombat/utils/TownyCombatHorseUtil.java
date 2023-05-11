package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Translatable;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatHorseUtil {

    private static Map<Player, AbstractHorse> scheduledHorseTeleports = new HashMap<>();
    private static Map<Player, Long> cavalryStrengthBonusRefreshTimes = new HashMap<>();

    /**
     * Teleport the player's horse, if there is one
     * @param event player teleport event
     */
    public static void scheduleMountTeleport(PlayerTeleportEvent event) {
        if(TownyCombatSettings.isCavalryEnhancementsEnabled() 
                && TownyCombatSettings.isTeleportMountWithPlayerEnabled() 
                && scheduledHorseTeleports.containsKey(event.getPlayer())) {
            AbstractHorse horse = scheduledHorseTeleports.get(event.getPlayer());
            //Protect horse until it rejoins player
            double preTeleportAbsorbtion = horse.getAbsorptionAmount();
            horse.setAbsorptionAmount(999);
            horse.setInvisible(true);
            //Schedule Teleport
            Towny.getPlugin().getServer().getScheduler().runTaskLater(Towny.getPlugin(), () -> {
                //Teleport Mount to wherever the player is
                horse.teleport(event.getPlayer());
                //Remove from map (allowing us to mount)
                deregisterPlayerMountForTeleport(event.getPlayer());
                //Mount player on horse
                horse.addPassenger(event.getPlayer());
                //Remove horse health protection
                horse.setAbsorptionAmount(preTeleportAbsorbtion);
                horse.setInvisible(false);
            },100);
        }
    }

    public static void registerPlayerMountForTeleport(Player player, AbstractHorse mount) {
        scheduledHorseTeleports.put(player, mount);
    }

    public static void deregisterPlayerMountForTeleport(Player player) {
        scheduledHorseTeleports.remove(player);
    }

    public static void registerPlayerForCavalryStrengthBonus(Player player) {
        cavalryStrengthBonusRefreshTimes.put(player, System.currentTimeMillis() + TownyCombatSettings.getCavalryChargeCooldownMilliseconds());
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE); //Removed so as not to cause confusion, and/or in preparation for next hit if applicable
    }

    public static void deregisterPlayerForCavalryStrengthBonus(Player player) {
        cavalryStrengthBonusRefreshTimes.remove(player);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE); //Remove so as not to cause confusion
    }

    public static boolean isHorseTeleportScheduled(AbstractHorse horse) {
        return scheduledHorseTeleports.containsValue(horse);
    }

    public static void refreshAllCavalryStrengthBonuses() {
        long now = System.currentTimeMillis();
        long nextRefreshTime = System.currentTimeMillis() + TownyCombatSettings.getCavalryChargeCooldownMilliseconds();
        int effectDurationTicks = TownyCombatSettings.getCavalryChargeEffectDurationTicks();

        for(Map.Entry<Player, Long> playerTimeEntry: (new HashMap<>(cavalryStrengthBonusRefreshTimes)).entrySet()) {
            //Verify player is still on horse
            if(!playerTimeEntry.getKey().isInsideVehicle() || !(playerTimeEntry.getKey().getVehicle() instanceof AbstractHorse)) {
                cavalryStrengthBonusRefreshTimes.remove(playerTimeEntry.getKey());
                continue; //Player no longer on horse
            }
            if(now > playerTimeEntry.getValue()) {
                //Refresh charge
                TownyCombat.getPlugin().getServer().getScheduler().runTask(TownyCombat.getPlugin(), ()-> applyPlaceholderStrengthEffectToPlayer(playerTimeEntry.getKey(), effectDurationTicks));
                //Arrange next refresh time
                cavalryStrengthBonusRefreshTimes.put(playerTimeEntry.getKey(), nextRefreshTime);
            }
        }
    }

    /**
     * Apply a "placeholder" strength effect to the player
     * This has no effect on its own, but is used to indicate that the strength bonus is ready for use.
     */
    private static void applyPlaceholderStrengthEffectToPlayer(Player player, int effectDurationTicks) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDurationTicks,-1));
    }

    public static @Nullable Player getPlayerRider(Entity entity) {
        if(entity.getPassengers().size() > 0 && entity.getPassengers().get(0) instanceof Player) {
            return (Player)entity.getPassengers().get(0);
        } else {
            return null;
        }
    }

    public static @Nullable Horse getMount(Player player) {
        if(player.isInsideVehicle() && player.getVehicle() instanceof Horse) {
            return (Horse)player.getVehicle();
        } else {
            return null;
        }
    }

    public static void cancelStrengthEffectsOnPlayerRiders(PotionSplashEvent event) {
        for (PotionEffect potionEffect : event.getPotion().getEffects()) {
            if (potionEffect.getType() == PotionEffectType.INCREASE_DAMAGE) {
                for (LivingEntity entity : event.getAffectedEntities()) {
                    if (entity instanceof Player && getMount((Player)entity) != null) {
                        event.setIntensity(entity, 0);
                        Messaging.sendMsg(event.getEntity(), Translatable.of("msg_warning_potion_strength_effect_blocked_for_rider"));
                    }
                }
            }
        }
    }

    public static void cancelDrinkingStrengthPotionIfPlayerIsRider(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.POTION) {
            PotionMeta potionMeta= (PotionMeta)event.getItem().getItemMeta();
            if(potionMeta == null)
                return;
            PotionEffectType potionEffectType = potionMeta.getBasePotionData().getType().getEffectType();
            if(potionEffectType == null)
                return;
            if(potionEffectType.equals(PotionEffectType.DAMAGE_RESISTANCE)
                    && TownyCombatHorseUtil.getMount(event.getPlayer()) != null) {
                event.setCancelled(true);
                Messaging.sendMsg(event.getPlayer(), Translatable.of("msg_warning_potion_strength_effect_blocked_for_rider"));
            }
        }
    }
}


