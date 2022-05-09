package io.github.townyadvanced.townycombat.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that marks a special 'bonus' hit by cavalry
 */
public class TownyCombatSpecialCavalryHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player damager;
    private final Entity victim;
    private boolean specialHit;

    public TownyCombatSpecialCavalryHitEvent(Player damager, Entity victim, boolean specialHit){
        this.damager = damager;
        this.victim = victim;
        this.specialHit = specialHit;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getDamager() {
        return damager;
    }

    public Entity getVictim() {
        return victim;
    }

    public boolean isSpecialHit() {
        return specialHit;
    }

    public void setSpecialHit(boolean specialHit) {
        this.specialHit = specialHit;
    }
}
