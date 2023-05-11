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
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
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
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
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
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() && TownyCombatSettings.isTeleportMountWithPlayerEnabled())
			TownyCombatHorseUtil.scheduleMountTeleport(event);
	}

	@EventHandler
	public void on (EntityMountEvent event) {
		if(!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!(event.getEntity() instanceof Player))
			return; //Not a player doing the mounting
		if(!(event.getMount() instanceof Horse))
			return; //Not a horse being mounted
		//Remove legacy data
		TownyCombatMovementUtil.removeTownyCombatMovementModifiers((AbstractHorse)event.getMount());
		TownyCombatMovementUtil.removeTownyCombatKnockbackModifiers((AbstractHorse)event.getMount());
		//Validate Armour and effects
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.validateArmour((Player)event.getEntity());
			TownyCombatBattlefieldRoleUtil.validatePotionEffectsOnMount((Horse)event.getMount(), (Player)event.getEntity());
		}
		//Prevent mount if the horse is about to be TP'd to owner
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() 
				&& TownyCombatSettings.isTeleportMountWithPlayerEnabled()
				&& event.getMount() instanceof AbstractHorse
				&& TownyCombatHorseUtil.isHorseTeleportScheduled((AbstractHorse)event.getMount())) {
			event.setCancelled(true);
		}
		//Register for charge bonus
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() && TownyCombatSettings.isCavalryStrengthBonusEnabled()) {
			if (TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
				//If battlefield roles is enabled, only some roles get the bonus
				if (TownyCombatBattlefieldRoleUtil.getBattlefieldRole((Player) event.getEntity()).hasCavalryStrengthBonus()) {
					TownyCombatHorseUtil.registerPlayerForCavalryStrengthBonus((Player) event.getEntity());
				}
			} else {
				//If battlefield roles is disabled, all players get the bonus
				TownyCombatHorseUtil.registerPlayerForCavalryStrengthBonus((Player) event.getEntity());
			}
		}
	}

	@EventHandler
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

	@EventHandler
	public void on (PlayerJoinEvent event) {
		if(!TownyCombatSettings.isTownyCombatEnabled())
			return;
		//Remove legacy data created by old TownyCombat versions
		TownyCombatMovementUtil.resetPlayerBaseSpeedToVanilla(event.getPlayer());
		TownyCombatMovementUtil.removeTownyCombatMovementModifiers(event.getPlayer());
		TownyCombatMovementUtil.removeTownyCombatKnockbackModifiers(event.getPlayer());

		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()) {
			//Process super-potions
			if(TownyCombatSettings.isBattlefieldRolesEnabled() 
					&& TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
				//Remove expired super potions
				TownyCombatItemUtil.removeExpiredSuperPotionsFromInventory(event.getPlayer());
				//Grant new super potions
				TownyCombatItemUtil.evaluateSuperPotionGrant(event.getPlayer());
			}
			//Transmute potions
			if(TownyCombatSettings.isPotionTransmuterEnabed()) {
				TownyCombatItemUtil.transmutePotionsInInventory(event.getPlayer());
			}
		}
	}

	@EventHandler
	public void on (EntityDamageEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(!TownyCombatSettings.isHorseRearingPreventionEnabled())
			return;
		if(!TownyCombatSettings.isCavalryEnhancementsEnabled())
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

		//Cancel strength potion effects on player riders, to avoid confusion with the cavalry power shot
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() 
				&& TownyCombatSettings.isCavalryStrengthBonusEnabled()) {
			TownyCombatHorseUtil.cancelStrengthEffectsOnPlayerRiders(event);
		}

		//Cancel if this is a super potion which was thrown by a player but not owner by them
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() 
				&& TownyCombatSettings.isBattlefieldRolesEnabled()
				&& TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()
			    && TownyCombatItemUtil.isSuperPotion(event.getPotion().getItem())) {
			ProjectileSource shooter = event.getEntity().getShooter();
			if (shooter instanceof Player) {
				if (!TownyCombatItemUtil.doesPlayerOwnSuperPotion((Player) shooter, event.getPotion().getItem())) {
					event.setCancelled(true);
					Messaging.sendMsg((Player) shooter, Translatable.of("msg_warning_cannot_use_super_potion_not_owner"));
				}
			}
		}

		//Apply role based effect adjustments
		if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()
				&& TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.processSplashPotionEvent(event);
		}
	}

	@EventHandler
	public void on (EntityShootBowEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		/*
		 * Cancel event if attacker has used a disallowed weapon
		 */
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
	
	@EventHandler
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

		//CAVALRY MISSILE SHIELD: Cavalry (Horse+Rider) are shielded from arrows fired by player-bows
		if(TownyCombatSettings.isCavalryEnhancementsEnabled()
				&& TownyCombatSettings.isCavalryMissileShieldEnabled()
				&& attackingPlayer != null
				&& isCavalryUnderAttack
				&& event.getDamager() instanceof Arrow) {

			if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()
					&& TownyCombatSettings.isBattlefieldRolesEnabled()) {
				/*
				 * If battlefield roles are enabled, then we query the role enum
				 */				
				if(TownyCombatBattlefieldRoleUtil.getBattlefieldRole(attackingPlayer).getMissileWeapon() == Material.BOW) {
					event.setCancelled(true);
					return;
				}
			} else {
				/*
				 * Here, battlefield roles are disabled
				 * This if-statement is bit hacky and could be improved if needed
				 * (by first tacking a data tag onto every fired arrow)
				 * But I expect that battlefield roles will usually be enabled.
				 */
				if (attackingPlayer.getInventory().getItemInMainHand().getType() == Material.BOW) {
					event.setCancelled(true);
					return;
				}
			}
		}

		//SPEAR: Do extra damage to cavalry
		double damage = event.getDamage();
		if(TownyCombatSettings.isNewItemsSpearEnabled()
			&& isCavalryUnderAttack
			&& attackingPlayer != null
		) { 
			ItemStack mainHandItem = attackingPlayer.getInventory().getItemInMainHand();
			if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()
				&& TownyCombatSettings.isBattlefieldRolesEnabled()) {
				//If B-Roles is enabled, only light infantry get the spear anti-cav bonus, and the valus is 9
				if(TownyCombatBattlefieldRoleUtil.getBattlefieldRole(attackingPlayer) == BattlefieldRole.LIGHT_INFANTRY
						&& TownyCombatItemUtil.isSpear(mainHandItem)) {
					damage += 9;
				}
			} else if(TownyCombatItemUtil.isSpear(mainHandItem)) {
				//If B-Roles is disabled, anyone can get the spear bonus
				damage += TownyCombatSettings.getNewItemsSpearBonusDamageVsCavalry();
			}
		}

		//CAVALRY STRENGTH BONUS: Do extra damage
		if(TownyCombatSettings.isCavalryEnhancementsEnabled()
			&&
			TownyCombatSettings.isCavalryStrengthBonusEnabled()
			&&
			//Attacker is on a horse
			(attackingPlayer != null && attackingPlayer.isInsideVehicle() && attackingPlayer.getVehicle() instanceof AbstractHorse) 				
			&&
			//Bonus is charged-up
			(attackingPlayer.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && attackingPlayer.getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getAmplifier() == -1))
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
				if(TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled()
						&& TownyCombatSettings.isBattlefieldRolesEnabled()) {
					//If battlefield roles are enabled, the damage is by role
					damage += (3 * TownyCombatBattlefieldRoleUtil.getBattlefieldRole(attackingPlayer).getCavalryStrengthBonus());
				} else {
					//If battlefield roles are not enabled, the damage is configured
					damage += (3 * TownyCombatSettings.getCavalryChargeStrengthBonusEffectLevel());
				}
			}
		}

		//DAMAGE RESISTANCE
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() 
				&& TownyCombatSettings.getAttackDamageResistanceHorsesPercent() > 0
				&& event.getEntity() instanceof AbstractHorse) {
			//Horse damage resistance
			if(attackingPlayer != null) {
				damage = damage - (damage * (TownyCombatSettings.getAttackDamageResistanceHorsesPercent() / 100));
			}
		}

		//SET DAMAGE
		event.setDamage(damage);
	}

	@EventHandler
	public void on (PrepareItemCraftEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		ItemStack resultItemStack = event.getInventory().getResult();
		if (resultItemStack!= null) {
			//Native Spear
			if (TownyCombatSettings.isNewItemsSpearEnabled() && TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled()) {
				if (event.isRepair()) {
					//Cannot repair native spear
					if (resultItemStack.getType() == Material.WOODEN_SWORD) {
						event.getInventory().setResult(null);
					}
				} else {
					//Craft native spear	
					if (resultItemStack.getType() == Material.WOODEN_SWORD) {
						event.getInventory().setResult(TownyCombatItemUtil.createNativeSpear());
					}
				}
			}
		}
	}

	@EventHandler
	public void on (BrewEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		//Can't alter the new regen potions
		if (TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isPotionTransmuterEnabed()) {
			BrewerInventory brewerInventory = event.getContents();
			ItemStack resultItemStack;
			for (int i = 0; i < brewerInventory.getContents().length; i++) {
				resultItemStack = brewerInventory.getItem(i);
				if (resultItemStack != null
						&& resultItemStack.getType() == Material.POTION
						&& resultItemStack.getItemMeta() != null
						&& ((PotionMeta) resultItemStack.getItemMeta()).getCustomEffects().size() > 0
						&& ((PotionMeta) resultItemStack.getItemMeta()).getCustomEffects().get(0).getType().equals(PotionEffectType.REGENERATION)) {
					//Cancel Event. Reduce ingredient so the brew does not keep going indefinitely
					if(brewerInventory.getIngredient() != null) {
						brewerInventory.getIngredient().setAmount(brewerInventory.getIngredient().getAmount() - 1);
					}
					event.setCancelled(true);
					event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.EXTINGUISH, null);
					break;
				}
			}
		}
	}
	
	@EventHandler
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
		//Validate armour
		TownyCombatBattlefieldRoleUtil.validateArmour((Player)event.getPlayer());
		//Remove expired super potions
		if(TownyCombatSettings.isBattlefieldRolesSuperPotionsEnabled()) {
			TownyCombatItemUtil.removeExpiredSuperPotionsFromInventory((Player) event.getPlayer());
		}
		//Transmute potions
		if(TownyCombatSettings.isPotionTransmuterEnabed()) {
			TownyCombatItemUtil.transmutePotionsInInventory((Player) event.getPlayer());
		}
	}

	@EventHandler
	public void on (PlayerItemConsumeEvent event) {
		if (!TownyCombatSettings.isTownyCombatEnabled())
			return;
		if(TownyCombatSettings.isCavalryEnhancementsEnabled() && TownyCombatSettings.isCavalryStrengthBonusEnabled()) {
			TownyCombatHorseUtil.cancelDrinkingStrengthPotionIfPlayerIsRider(event);
		}
		if(!event.isCancelled() && TownyCombatSettings.isUnlockCombatForRegularPlayersEnabled() && TownyCombatSettings.isBattlefieldRolesEnabled()) {
			TownyCombatBattlefieldRoleUtil.processItemConsumptionEvent(event);
		}
	}

}
