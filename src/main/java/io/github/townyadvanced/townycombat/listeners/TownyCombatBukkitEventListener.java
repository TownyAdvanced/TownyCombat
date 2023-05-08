package io.github.townyadvanced.townycombat.listeners;

import com.palmergames.bukkit.towny.object.Translatable;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.events.TownyCombatSpecialCavalryHitEvent;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import io.github.townyadvanced.townycombat.utils.Messaging;
import io.github.townyadvanced.townycombat.utils.TownyCombatBattlefieldRoleUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatHorseUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatMovementUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatDistanceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatInventoryUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatExperienceUtil;
import io.github.townyadvanced.townycombat.utils.TownyCombatItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
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
		if(!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!(event.getEntity() instanceof Player))
			return; //Not a player doing the mounting
		if(!(event.getMount() instanceof AbstractHorse))
			return; //Not a horse being mounted
		//Remove legacy data
		TownyCombatMovementUtil.removeTownyCombatMovementModifiers((AbstractHorse)event.getMount());
		TownyCombatMovementUtil.removeTownyCombatKnockbackModifiers((AbstractHorse)event.getMount());
		//Validate Armour
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.validateArmour((Player)event.getEntity());
		}
		//Prevent mount if the horse is about to be TP'd to owner
		if(TownyCombatSettings.isTeleportMountWithPlayerEnabled()
				&& event.getMount() instanceof AbstractHorse
				&& TownyCombatHorseUtil.isHorseTeleportScheduled((AbstractHorse)event.getMount())) {
			event.setCancelled(true);
		}
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

	@EventHandler
	public void on (PlayerDeathEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatSettings.isKeepInventoryOnDeathEnabled() && !TownyCombatSettings.isKeepExperienceOnDeathEnabled())
			return;	
		if(!TownyCombatDistanceUtil.isCloseToATown(event.getEntity(), TownyCombatSettings.getKeepStuffOnDeathTownProximityBlocks()))
			return;
		//Keep inv functions
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
		if(!TownyCombatSettings.isTownyCombatEnabled())
			return;
		//Remove legacy data created by old TownyCombat versions
		TownyCombatMovementUtil.resetPlayerBaseSpeedToVanilla(event.getPlayer());
		TownyCombatMovementUtil.removeTownyCombatMovementModifiers(event.getPlayer());
		TownyCombatMovementUtil.removeTownyCombatKnockbackModifiers(event.getPlayer());
		//Possibly grant super potions
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()
				&& TownyCombatSettings.isBattlefieldRolesEnabled()
				&& TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
			TownyCombatItemUtil.evaluateSuperPotionGrant(event.getPlayer());
		}

	}

	@EventHandler (ignoreCancelled = true)
	public void on (EntityDamageEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatSettings.isHorseRearingPreventionEnabled())
			return;
		// When it is a horse, and it is not being hurt by a bush/cactus, or fire block, try to prevent the rearing.
		if (event.getEntity() instanceof AbstractHorse && !event.getCause().equals(DamageCause.CONTACT) && !event.getCause().equals(DamageCause.FIRE)) {
			//Prevent the rearing by cancelling the event, then applying the same damage in a simple set operation.
			if(TownyCombatHorseUtil.getPlayerRider(event.getEntity()) != null) {
				event.setCancelled(true);
				AbstractHorse horse = (AbstractHorse)event.getEntity();
				double newHealth = horse.getHealth() - event.getDamage();
				horse.setHealth(Math.max(0, Math.min(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), newHealth)));
			}
		}
	}

	@EventHandler
	public void on (PotionSplashEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;

		if(!TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && !TownyCombatSettings.isBattlefieldRolesEnabled())
			return;

		//Cancel if this is a super potion which was thrown by a player but not owner by them
		if(TownyCombatItemUtil.isSuperPotion(event.getPotion().getItem())) {
			ProjectileSource shooter = event.getEntity().getShooter();
			if(shooter instanceof Player) {
				if(!TownyCombatItemUtil.doesPlayerOwnSuperPotion((Player)shooter, event.getPotion().getItem())) {
					event.setCancelled(true);
					Messaging.sendMsg((Player)shooter, Translatable.of("msg_warning_cannot_use_super_potion_not_owner"));
				} 
			}
		}

		//Increase or decrease effect power depending on role of affected entity
		for (LivingEntity affectedEntity : event.getAffectedEntities()) {
			if (affectedEntity instanceof Player) {
				BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole((Player)affectedEntity);
				TownyCombatBattlefieldRoleUtil.evaluateEffectOfSplashPotion(event, affectedEntity, battlefieldRole);
			} else if (affectedEntity instanceof Horse) {
				Player playerRider = TownyCombatHorseUtil.getPlayerRider(affectedEntity);
				if(playerRider != null) {
					BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(playerRider);
					TownyCombatBattlefieldRoleUtil.evaluateEffectOfSplashPotion(event, affectedEntity, battlefieldRole);
				}
			}
		}
	}

	@EventHandler
	public void on (EntityShootBowEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;

		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			if(event.getBow() == null)
				return;
			if(!(event.getEntity() instanceof Player)) 
				return;
			if(!TownyCombatBattlefieldRoleUtil.isItemAllowedByBattlefieldRole(event.getBow(), (Player)event.getEntity())) {
				event.setCancelled(true);
				Messaging.sendMsg(event.getEntity(), Translatable.of("msg_warning_cannot_wield_this_missile_weapon").append(Translatable.of("msg_warning_how_to_view_and_change_role")));
			}
		}
	}
	
	@EventHandler (ignoreCancelled = true)
	public void on (EntityDamageByEntityEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;

		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
				/*
				 * PVP event Detected
				 * Cancel if the damager is holding a disallowed weapon
				 */
				if(!TownyCombatBattlefieldRoleUtil.isItemAllowedByBattlefieldRole(
						((Player)event.getDamager()).getInventory().getItemInMainHand(), 
						(Player)event.getDamager())) {
					event.setCancelled(true);
					Messaging.sendMsg(event.getDamager(), Translatable.of("msg_warning_cannot_wield_this_melee_weapon").append("msg_warning_how_to_view_and_change_role"));
				}
			}
		}
		
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
		double damage = event.getDamage();
		if(TownyCombatSettings.isNewItemsSpearEnabled()
			&& isCavalryUnderAttack
			&& event.getDamager() instanceof Player
		) { 
			ItemStack mainHandItem = ((Player)event.getDamager()).getInventory().getItemInMainHand();
			if(TownyCombatItemUtil.isSpear(mainHandItem)) {
				damage += TownyCombatSettings.getNewItemsSpearBonusDamageVsCavalry();
			}
		}

		//CAVALRY STRENGTH BONUS: Do extra damage to player infantry
		if(TownyCombatSettings.isCavalryStrengthBonusEnabled()
			&&
			//Cavalry Attacker 
			(attackingPlayer != null && attackingPlayer.isInsideVehicle() && attackingPlayer.getVehicle() instanceof AbstractHorse) 				
			&&
			//Infantry defender
			(event.getEntity() instanceof Player && (!event.getEntity().isInsideVehicle() || !(event.getEntity().getVehicle() instanceof AbstractHorse)))
			&&
			//Bonus is charged-up
			(attackingPlayer.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && attackingPlayer.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier() == 0))
		{
			TownyCombatSpecialCavalryHitEvent specialCavalryHit = new TownyCombatSpecialCavalryHitEvent(attackingPlayer, event.getEntity(), true);
			Bukkit.getPluginManager().callEvent(specialCavalryHit);
			if(specialCavalryHit.isSpecialHit()) {
				/*
				 * Note: The bonus is not already contained in the damage.
				 * Because the str amplifier is power 0
				 * So we need to explicitly add the damage.
				 */
				TownyCombatHorseUtil.registerPlayerForCavalryStrengthBonus(attackingPlayer);
				attackingPlayer.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				damage += (3 * TownyCombatSettings.getCavalryChargeStrengthBonusEffectLevel());
			}
		}
		//DAMAGE RESISTANCE
		if (event.getEntity() instanceof AbstractHorse) {
			//Horse damage resistance
			if(attackingPlayer != null) {
				damage = damage - (damage * (TownyCombatSettings.getAttackDamageResistanceHorsesPercent() / 100));
			}
		}

		//SET DAMAGE
		event.setDamage(damage);
	}

	@EventHandler (ignoreCancelled = true)
	public void on (PrepareItemCraftEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if (!TownyCombatSettings.isNewItemsSpearEnabled() || !TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled())
			return;
		if(event.getInventory().getResult() != null) {
			if (event.isRepair()) {
				//Cannot repair native spear
				if (event.getInventory().getResult().getType() == Material.WOODEN_SWORD) {
					event.getInventory().setResult(null);
				}
			} else {
				//Craft native spear	
				if (event.getInventory().getResult().getType() == Material.WOODEN_SWORD) {
					event.getInventory().setResult(TownyCombatItemUtil.createNativeSpear());
				}
			}
		}
	}

	@EventHandler (ignoreCancelled = true)
	public void on (PrepareAnvilEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if (!TownyCombatSettings.isNewItemsSpearEnabled() || !TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled())
			return;
		if(event.getResult() != null && event.getResult().getType() == Material.WOODEN_SWORD) {
			event.setResult(null);  //Cannot repair native spear
		}
	}

	@EventHandler
	public void on (InventoryCloseEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled() || !TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() || !TownyCombatSettings.isBattlefieldRolesEnabled())
			return;
		if(!(event.getPlayer() instanceof Player))
			return;
		TownyCombatBattlefieldRoleUtil.validateArmour((Player)event.getPlayer());
	}

	@EventHandler
	public void on (PlayerItemConsumeEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled() || !TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() || !TownyCombatSettings.isBattlefieldRolesEnabled())
			return;
		if (event.getItem().getType() == Material.POTION) {
			TownyCombatBattlefieldRoleUtil.evaluateEffectOfDrinkingPotion(event);
		}
	}

}
