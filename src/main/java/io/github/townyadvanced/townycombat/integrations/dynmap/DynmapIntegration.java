package io.github.townyadvanced.townycombat.integrations.dynmap;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.utils.TownyCombatMapUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dynmap.DynmapAPI;

public class DynmapIntegration {
    
    private final DynmapAPI dynmapAPI;

    public DynmapIntegration() {
        dynmapAPI = (DynmapAPI) TownyCombat.getPlugin().getServer().getPluginManager().getPlugin("dynmap");
        TownyCombat.info("Dynmap support enabled.");
    }

    /**
     * This method hides players who have the map hiding metadata tag.
     * It also un-hides players who do not have it.
     */
    public void applyTacticalInvisibilityToPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            dynmapAPI.assertPlayerInvisibility(player, player.hasMetadata(TownyCombatMapUtil.TACTICALLY_INVISIBLE_METADATA_ID), TownyCombat.getPlugin());
        }
    }
}
