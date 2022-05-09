package io.github.townyadvanced.townycombat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that marks an attempt by TownyCombat to keep a player's inventory
 */
public class TownyCombatKeepInventoryOnDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private boolean keepInventory;
    private boolean degradeInventory;
    private boolean keepExperience;

    public TownyCombatKeepInventoryOnDeathEvent(Player player, boolean keepInventory, boolean degradeInventory, boolean keepExperience){
        this.player = player;
        this.keepInventory = keepInventory;
        this.degradeInventory = degradeInventory;
        this.keepExperience = keepExperience;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isKeepInventory() {
        return keepInventory;
    }

    public void setKeepInventory(boolean keepInventory) {
        this.keepInventory = keepInventory;
    }

    public boolean isDegradeInventory() {
        return degradeInventory;
    }

    public void setDegradeInventory(boolean degradeInventory) {
        this.degradeInventory = degradeInventory;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isKeepExperience() {
        return keepExperience;
    }

    public void setKeepExperience(boolean keepExperience) {
        this.keepExperience = keepExperience;
    }
}
