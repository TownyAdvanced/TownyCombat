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
    private static Map<Player, Long> cavalryChargeRefreshTimes = new HashMap<>();

    /**
     * Teleport the player's horse, if there is one
     * @param event player teleport event
     */
    public static void scheduleMountTeleport(PlayerTeleportEvent event) {
        if(TownyCombatSettings.isTeleportMountWithPlayerEnabled() && scheduledHorseTeleports.containsKey(event.getPlayer())) {
            AbstractHorse horse = scheduledHorseTeleports.get(event.getPlayer());
            //Protect horse until it rejoins player
            double preTeleportAbsorbtion = horse.getAbsorptionAmount();
            horse.setAbsorptionAmount(999999);
            horse.setInvisible(true);
            //Schedule Teleport
            Towny.getPlugin().getServer().getScheduler().runTaskLater(Towny.getPlugin(), () -> {
                //Teleport Mount to wherever the player is
                horse.teleport(event.getPlayer());
                //Remove from map (allowing us to mount)
                deregisterPlayerMount(event.getPlayer());
                //Mount player on horse
                horse.addPassenger(event.getPlayer());
                //Remove horse health protection
                horse.setAbsorptionAmount(preTeleportAbsorbtion);
                horse.setInvisible(false);
            },100);
        }
    }

    public static void registerPlayerMount(Player player, AbstractHorse mount) {
        if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()) {
            scheduledHorseTeleports.put(player, mount);
        }
        if(TownyCombatSettings.isCavalryChargeEnabled()) {
            cavalryChargeRefreshTimes.put(player, System.currentTimeMillis() + TownyCombatSettings.getCavalryChargeCooldownMilliseconds());
        }
    }

    public static void deregisterPlayerMount(Player player) {
        if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()) {
            scheduledHorseTeleports.remove(player);
        }
        if(TownyCombatSettings.isCavalryChargeEnabled() && cavalryChargeRefreshTimes.containsKey(player)) {
            cavalryChargeRefreshTimes.remove(player);
        }
    }

    public static boolean isHorseTeleportScheduled(AbstractHorse horse) {
        return scheduledHorseTeleports.containsValue(horse);
    }

    public static void refreshAllCavalryCharges() {
        long now = System.currentTimeMillis();
        long nextRefreshTime = System.currentTimeMillis() + TownyCombatSettings.getCavalryChargeCooldownMilliseconds();
        int effectDurationTicks = TownyCombatSettings.getCavalryChargeEffectDurationTicks();

        for(Map.Entry<Player, Long> playerTimeEntry: (new HashMap<>(cavalryChargeRefreshTimes)).entrySet()) {
            //Verify player is still on horse
            if(!playerTimeEntry.getKey().isInsideVehicle() || !(playerTimeEntry.getKey().getVehicle() instanceof AbstractHorse)) {
                cavalryChargeRefreshTimes.remove(playerTimeEntry.getKey());
                continue; //Player no longer on horse
            }
            if(now > playerTimeEntry.getValue()) {
                //Refresh charge
                TownyCombat.getPlugin().getServer().getScheduler().runTask(TownyCombat.getPlugin(), ()-> applyChargeEffectToPlayer(playerTimeEntry.getKey(), effectDurationTicks));
                //Arrange next refresh time
                cavalryChargeRefreshTimes.put(playerTimeEntry.getKey(), nextRefreshTime);
            }
        }
    }

    private static void applyChargeEffectToPlayer(Player player, int effectDurationTicks) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectDurationTicks, 2));
    }

}


