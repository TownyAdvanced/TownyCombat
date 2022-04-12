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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
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
		//Register for charge bonus
		TownyCombatHorseUtil.registerPlayerForChargeBonus((Player)event.getEntity());
	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityDismountEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!(event.getEntity() instanceof Player))
			return;
		//Deregister for charge bonus
		TownyCombatHorseUtil.deregisterPlayerMountForChargeBonus((Player)event.getEntity());
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
		double finalDamage = event.getFinalDamage();
		if(event.getDamager() instanceof Player) {
			//SPEAR: Increase damage if spear v.s. cavalry
			if(TownyCombatSettings.isNewItemsSpearEnabled()) {
				if(event.getEntity() instanceof AbstractHorse
						|| (event.getEntity() instanceof Player
							&& event.getEntity().isInsideVehicle()
							&& event.getEntity().getVehicle() instanceof AbstractHorse)) {
					ItemStack mainHandItem =  ((Player)event.getDamager()).getInventory().getItemInMainHand();
					if(mainHandItem.getType() == TownyCombatItemUtil.SPEAR_PLACEHOLDER_MATERIAL
							&& mainHandItem.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)
							&& mainHandItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == TownyCombatItemUtil.SPEAR_SHARPNESS_LEVEL) {
						finalDamage += TownyCombatItemUtil.SPEAR_VS_CAVALRY_EXTRA_DAMAGE;
					}
				}
			}
			//CHARGING: Increase damage if charging
			if(TownyCombatSettings.isCavalryChargeEnabled()) {
				if(event.getDamager().isInsideVehicle()
						&& event.getDamager().getVehicle() instanceof AbstractHorse) {
					((Player) event.getDamager()).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					finalDamage += 3;   //Equivalent to Strength I
				}
			}
			//WARHAMMER: Possibly break shield if warhammer
			if(TownyCombatSettings.isNewItemsWarhammerEnabled()) {
				if(event.getEntity() instanceof Player) {
					ItemStack damagerMainHand = ((Player)event.getDamager()).getInventory().getItemInMainHand();
					if (damagerMainHand.getType() == TownyCombatItemUtil.WARHAMMER_PLACEHOLDER_MATERIAL) {
						if(((Player)event.getEntity()).isBlocking()) {
							ItemStack victimMainHand = ((Player)event.getEntity()).getInventory().getItemInMainHand();
							ItemStack victimOffHand = ((Player)event.getEntity()).getInventory().getItemInOffHand();
							if(victimMainHand.getType() == Material.SHIELD) {
								//Player is blocking with shield in main hand
								TownyCombatItemUtil.rollBreakItemInHand((Player)event.getEntity(), false, TownyCombatItemUtil.WARHAMMER_BREAK_SHIELD_CHANCE);
							} else if (victimOffHand.getType() == Material.SHIELD) {
								//Player is blocking with shield in off hand
								TownyCombatItemUtil.rollBreakItemInHand((Player)event.getEntity(), true, TownyCombatItemUtil.WARHAMMER_BREAK_SHIELD_CHANCE);
							}
						}
					}
				}
			}
		}
		//GENERIC DAMAGE ADJUSTMENTS AND AUTOPOTTING
		if(event.getEntity() instanceof Player) {
			//Generic damage adjustment
			finalDamage = finalDamage + (finalDamage * (TownyCombatSettings.getDamageAdjustmentAttackOnPlayer() / 100));
			//Auto-pot if needed
			if(TownyCombatSettings.isAutoPottingEnabled()
					&& ((Player) event.getEntity()).getHealth() < TownyCombatSettings.getAutoPottingThreshold()) {
				TownyCombatItemUtil.autopotToThreshold((Player)event.getEntity());
			}
		} else if (event.getEntity() instanceof AbstractHorse) {
			//Generic damage adjustment
			finalDamage = finalDamage + (finalDamage * (TownyCombatSettings.getDamageAdjustmentsAttackOnHorse() / 100));
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
		if(event.getResult() != null
				&& TownyCombatItemUtil.isReservedMaterial(event.getResult().getType())) {
			event.setResult(null);  //Cannot repair reserved material
		}
	}

}
