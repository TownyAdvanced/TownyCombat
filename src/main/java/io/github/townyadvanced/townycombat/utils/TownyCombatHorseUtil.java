package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatHorseUtil {

    private static Map<Player, AbstractHorse> scheduledHorseTeleports = new HashMap<>();

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
        scheduledHorseTeleports.put(player, mount);
    }

    public static void deregisterPlayerMount(Player player) {
        scheduledHorseTeleports.remove(player);
    }

    public static boolean isHorseTeleportScheduled(AbstractHorse horse) {
        return scheduledHorseTeleports.containsValue(horse);
    }
}


