package io.github.townyadvanced.townycombat.listeners;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatHorseUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatDistanceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatInventoryUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatExperienceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.spigotmc.event.entity.EntityMountEvent;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatBukkitEventListener implements Listener {

	@SuppressWarnings("unused")
	private final TownyCombat plugin;
	
	public TownyCombatBukkitEventListener(TownyCombat instance) {
		plugin = instance;
	}

	@EventHandler
	public void on (PlayerTeleportEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(event.getTo() == null)
			return; // Can't proceed without this
		if(event.getCause() != TeleportCause.PLUGIN && event.getCause() != TeleportCause.COMMAND)
			return; //Don't proceed further for causes like enderpearls
		if(event.isCancelled()) {
			//If something cancelled the player teleport, deregister the mount (to avoid it maybe teleporting later)
			TownyCombatHorseUtil.deregisterPlayerMount(event.getPlayer());		
			return;
		}
		//Teleport horse with player
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled())
			TownyCombatHorseUtil.scheduleMountTeleport(event);
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityMountEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!(event.getEntity() instanceof Player))
			return;
		//Prevent mount if the horse is about to be TP'd to owner
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()
				&& event.getMount() instanceof AbstractHorse
				&& TownyCombatHorseUtil.isHorseTeleportScheduled((AbstractHorse)event.getMount())) {
			event.setCancelled(true);
			event.getEntity().getUniqueId();
		}
		//Apply speed adjustments
		TownyCombatMovementUtil.adjustPlayerAndMountSpeeds((Player)event.getEntity());
	}
	
	@EventHandler (ignoreCancelled = true)
	public void on (PlayerDeathEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatDistanceUtil.isCloseToATown(event.getEntity(), TownyCombatSettings.getKeepStuffOnDeathTownProximityBlocks()))
			return;
		if(TownyCombatSettings.isKeepInventoryOnDeathEnabled()) {
			TownyCombatInventoryUtil.degradeInventory(event);
			TownyCombatInventoryUtil.keepInventory(event);
		}
		if(TownyCombatSettings.isKeepExperienceOnDeathEnabled()) {
			TownyCombatExperienceUtil.keepExperience(event);
		}
	}

	@EventHandler (ignoreCancelled = true)
	public void on (PlayerJoinEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		TownyCombatMovementUtil.adjustPlayerAndMountSpeeds(event.getPlayer());
	}

    @EventHandler (ignoreCancelled = true)
    public void on (EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player
			|| (event.getDamager() instanceof Projectile
				&& ((Projectile)event.getDamager()).getShooter() instanceof Player)) {
			event.setDamage(event.getDamage() + (event.getDamage() * (TownyCombatSettings.getDamageModificationAllWeaponsPercentage() / 100)));
		}
	}
	
	@EventHandler (ignoreCancelled = true)
    public void on (EntityDeathEvent event) {
    	//If the entity was a horse with an owner, delete the metadata
		System.out.println("TODOOOOOO");
	}
	
}
