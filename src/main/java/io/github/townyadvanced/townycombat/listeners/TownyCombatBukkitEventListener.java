package io.github.townyadvanced.townycombat.listeners;

import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.TownyCombatHorseUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatDistanceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatInventoryUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatExperienceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatItemUtil;

import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
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
			TownyCombatHorseUtil.deregisterPlayerMountForTeleport(event.getPlayer());		
			return;
		}
		//Teleport horse with player
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled())
			TownyCombatHorseUtil.scheduleMountTeleport(event);
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityMountEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		if(!(event.getMount() instanceof AbstractHorse))
			return;
		//Remove modifiers if system/speed-feature is disabled		
		if (!TownyCombatSettings.isTownyCombatEnabled() || !TownyCombatSettings.isSpeedAdjustmentsEnabled()) {
			TownyCombatMovementUtil.removeTownyCombatMovementAttributeModifiers((AbstractHorse)event.getMount());
		}
		if (!TownyCombatSettings.isTownyCombatEnabled())
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
		//Register for charge bonus
		TownyCombatHorseUtil.registerPlayerForCavalryStrengthBonus((Player)event.getEntity());
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityDismountEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!(event.getEntity() instanceof Player))
			return;
		//Deregister for charge bonus
		TownyCombatHorseUtil.deregisterPlayerForCavalryStrengthBonus((Player)event.getEntity());
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
		//Remove legacy data
		TownyCombatMovementUtil.resetPlayerBaseSpeedToVanilla(event.getPlayer());
		//Remove modifiers if system/feature is disabled
		if (!TownyCombatSettings.isTownyCombatEnabled() || !TownyCombatSettings.isSpeedAdjustmentsEnabled()) {
			TownyCombatMovementUtil.removeTownyCombatMovementAttributeModifiers(event.getPlayer());
		} else {
			TownyCombatMovementUtil.adjustPlayerAndMountSpeeds(event.getPlayer());
		}
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityDamageEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if (event.getEntity() instanceof AbstractHorse) {
			//No fire damage to horses ridden by players
			if(TownyCombatSettings.isCavalryFireShieldEnabled()
                                && event.getEntity().getPassengers().size() > 0
			        && event.getEntity().getPassengers().get(0) instanceof Player
			        && (event.getCause() == EntityDamageEvent.DamageCause.FIRE
				    || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
				event.setCancelled(true);
			} 
		}
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityDamageByEntityEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;

		//Get attacking player (if any)	
		Player attackingPlayer;
		if(event.getDamager() instanceof Player) {
			attackingPlayer = (Player)event.getDamager();
		} else if (event.getDamager() instanceof Arrow
				&& ((Arrow)event.getDamager()).getShooter() != null
				&& ((Arrow)event.getDamager()).getShooter() instanceof Player) {
			attackingPlayer = (Player)((Arrow)event.getDamager()).getShooter();
		} else {
			attackingPlayer = null;
		}
		
		//Discover if cavalry is under attack
		boolean isCavalryUnderAttack;
		if(	
			(event.getEntity() instanceof AbstractHorse
				&& event.getEntity().getPassengers().size() > 0
				&& event.getEntity().getPassengers().get(0) instanceof Player)
			||
			(event.getEntity() instanceof Player
				&& event.getEntity().isInsideVehicle()
				&& event.getEntity().getVehicle() instanceof AbstractHorse)
		) {
			isCavalryUnderAttack = true;
		} else {
			isCavalryUnderAttack = false;
		}

		//CAVALRY MISSILE SHIELD: Cavalry are shielded from arrows fired by player-bows
		if(TownyCombatSettings.isCavalryMissileShieldEnabled()
				&& attackingPlayer != null
				&& isCavalryUnderAttack
				&& event.getDamager() instanceof Arrow
				&& attackingPlayer.getInventory().getItemInMainHand().getType() == Material.BOW
		) {
			event.setCancelled(true);
			return;				
		}

		//SPEAR: Do extra damage to cavalry
		double finalDamage = event.getFinalDamage();
		if(TownyCombatSettings.isNewItemsSpearEnabled()
			&& isCavalryUnderAttack
			&& event.getDamager() instanceof Player
		) { 
			ItemStack mainHandItem = ((Player)event.getDamager()).getInventory().getItemInMainHand();
			if(TownyCombatItemUtil.isSpear(mainHandItem)) {
				finalDamage += TownyCombatSettings.getNewItemsSpearBonusDamageVsCavalry();
			}
		}

		//CAVALRY STRENGTH BONUS: Do extra damage to player infantry
		if(TownyCombatSettings.isCavalryStrengthBonusEnabled()
			&&
			//Cavalry Attacker 
			(attackingPlayer != null && attackingPlayer.isInsideVehicle() && attackingPlayer.getVehicle() instanceof AbstractHorse) 				
			&&						
			//Infantry Victim
			(event.getEntity() instanceof Player && (!event.getEntity().isInsideVehicle() || !(event.getEntity().getVehicle() instanceof AbstractHorse)))
			&&
			//Bonus is charged-up
			attackingPlayer.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)
		) {
			attackingPlayer.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			finalDamage += (3 * TownyCombatSettings.getCavalryChargeStrengthBonusEffectLevel());
			TownyCombatHorseUtil.registerPlayerForCavalryStrengthBonus(attackingPlayer);
		}

		//WARHAMMER: Possibly break shield
		if(TownyCombatSettings.isNewItemsWarhammerEnabled()) {
			if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
				ItemStack itemInAttackerMainHand = ((Player)event.getDamager()).getInventory().getItemInMainHand();
				if (TownyCombatItemUtil.isWarhammer(itemInAttackerMainHand)) {
					if(((Player)event.getEntity()).isBlocking()) {
						ItemStack victimMainHand = ((Player)event.getEntity()).getInventory().getItemInMainHand();
						ItemStack victimOffHand = ((Player)event.getEntity()).getInventory().getItemInOffHand();
						if(victimMainHand.getType() == Material.SHIELD) {
							//Player is blocking with shield in main hand
							TownyCombatItemUtil.rollBreakItemInHand((Player)event.getEntity(), false, TownyCombatSettings.getNewItemsWarhammerShieldBreakChancePercent());
						} else if (victimOffHand.getType() == Material.SHIELD) {
							//Player is blocking with shield in off hand
							TownyCombatItemUtil.rollBreakItemInHand((Player)event.getEntity(), true, TownyCombatSettings.getNewItemsWarhammerShieldBreakChancePercent());
						}
					}
				}
			}
		}

		//DAMAGE RESISTANCE AND AUTOPOTTING
		if(event.getEntity() instanceof Player) {
			//Auto-pot if needed
			if(TownyCombatSettings.isAutoPottingEnabled()
					&& ((Player) event.getEntity()).getHealth() < TownyCombatSettings.getAutoPottingThreshold()) {
				TownyCombatItemUtil.autopotToThreshold((Player)event.getEntity());
			}
		} else if (event.getEntity() instanceof AbstractHorse) {
			//Horse damage resistance
			if(attackingPlayer != null) {
				finalDamage = finalDamage - (finalDamage * (TownyCombatSettings.getDamageResistanceHorsesPercent() / 100));
			}
			//Auto-pot if needed
			if(TownyCombatSettings.isAutoPottingEnabled()
					&& event.getEntity().getPassengers().size() > 0
					&& event.getEntity().getPassengers().get(0) instanceof Player
					&& ((AbstractHorse) event.getEntity()).getHealth() < TownyCombatSettings.getAutoPottingThreshold()) {
				TownyCombatItemUtil.autopotToThreshold((Player)event.getEntity().getPassengers().get(0), (AbstractHorse)event.getEntity());
			}
		}

		//SET DAMAGE
		event.setDamage(finalDamage);
	}

	@EventHandler (ignoreCancelled = true)
	public void on (PrepareItemCraftEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(event.getInventory().getResult() != null
				&& TownyCombatItemUtil.isForbiddenItem(event.getInventory().getResult())) {
			event.getInventory().setResult(null);
			return; //Cannot craft or repair forbidden item
		}
		if(event.isRepair()) {
			//Repair
			if(event.getInventory().getResult() != null
					&& TownyCombatItemUtil.isReservedMaterial(event.getInventory().getResult().getType())) {
				event.getInventory().setResult(null); //Cannot repair reserved material
			}
		} else {
			//Craft
			event.getInventory().setResult(TownyCombatItemUtil.calculateCraftingResult(event));
		}
	}

	@EventHandler (ignoreCancelled = true)
	public void on (PrepareAnvilEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(event.getResult() != null
				&& TownyCombatItemUtil.isReservedMaterial(event.getResult().getType())) {
			event.setResult(null);  //Cannot repair reserved material
		}
	}

}
