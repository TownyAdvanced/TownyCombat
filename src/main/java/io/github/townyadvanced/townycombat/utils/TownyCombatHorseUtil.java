package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if(TownyCombatSettings.isTeleportMountWithPlayerEnabled() && scheduledHorseTeleports.containsKey(event.getPlayer())) {
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
    }

    public static void deregisterPlayerForCavalryStrengthBonus(Player player) {
        cavalryStrengthBonusRefreshTimes.remove(player);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE); //Remove strength effect if any
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
     * This has no effect on its own, but is used to indicate the strength bonus is ready for use.
     */
    private static void applyPlaceholderStrengthEffectToPlayer(Player player, int effectDurationTicks) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDurationTicks,-1));
    }

}


