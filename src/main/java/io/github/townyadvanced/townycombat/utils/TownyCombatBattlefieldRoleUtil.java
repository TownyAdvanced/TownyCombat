package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.TimeMgmt;
import com.palmergames.util.TimeTools;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TownyCombatBattlefieldRoleUtil {
    public static final String leatherArmourKey = "LEATHER";
    public static final String ironArmourKey = "IRON";
    public static final String chainArmourKey = "CHAINMAIL";
    public static final String turtleArmourKey = "TURTLE";
    public static final String diamondArmourKey = "DIAMOND";
    public static final String goldArmourKey = "GOLDEN";
    public static final String netheriteArmourKey = "NETHERITE";
    public static final String woodenWeaponKey = "WOODEN";
    public static final String bowKey = "BOW";
    public static final String crossbowKey = "CROSSBOW";
    public static final String ironWeaponKey = "IRON";
    public static final String stoneWeaponKey = "STONE";
    public static final String diamondWeaponKey = "DIAMOND";
    public static final String netheriteWeaponKey = "NETHERITE";
    public static final Map<String, Set<BattlefieldRole>> armourBattlefieldRoleMap;
    public static final Map<String, Set<BattlefieldRole>> weaponBattlefieldRoleMap;

    static {
        armourBattlefieldRoleMap = new HashMap<>();
        //Infantry Armour
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, leatherArmourKey, BattlefieldRole.LIGHT_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, ironArmourKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, chainArmourKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, turtleArmourKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, goldArmourKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, diamondArmourKey, BattlefieldRole.HEAVY_INFANTRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, netheriteArmourKey, BattlefieldRole.HEAVY_INFANTRY);
        //Cavalry Armour
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, leatherArmourKey, BattlefieldRole.LIGHT_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, ironArmourKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, chainArmourKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, turtleArmourKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, goldArmourKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, diamondArmourKey, BattlefieldRole.HEAVY_CAVALRY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, netheriteArmourKey, BattlefieldRole.HEAVY_CAVALRY);
        
        weaponBattlefieldRoleMap = new HashMap<>();
        //Infantry Weapons
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, woodenWeaponKey, BattlefieldRole.LIGHT_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, bowKey, BattlefieldRole.LIGHT_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, stoneWeaponKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, ironWeaponKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, crossbowKey, BattlefieldRole.MEDIUM_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, diamondWeaponKey, BattlefieldRole.HEAVY_INFANTRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, netheriteWeaponKey, BattlefieldRole.HEAVY_INFANTRY);
        //Cavalry Weapons
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, woodenWeaponKey, BattlefieldRole.LIGHT_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, bowKey, BattlefieldRole.LIGHT_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, stoneWeaponKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, ironWeaponKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, crossbowKey, BattlefieldRole.MEDIUM_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, diamondWeaponKey, BattlefieldRole.HEAVY_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, netheriteWeaponKey, BattlefieldRole.HEAVY_CAVALRY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, crossbowKey, BattlefieldRole.HEAVY_CAVALRY);
    }

    private static void addMaterialsToBattlefieldRoleMap(Map<String, Set<BattlefieldRole>> materialBattlefieldRoleMap, String materialKey, BattlefieldRole battlefieldRole) {
        if(materialBattlefieldRoleMap.containsKey(materialKey)) {
            materialBattlefieldRoleMap.get(materialKey).add(battlefieldRole);
        } else {
            Set<BattlefieldRole> battlefieldRoleSet = new HashSet<>();
            battlefieldRoleSet.add(battlefieldRole);
            materialBattlefieldRoleMap.put(materialKey, battlefieldRoleSet);
        }
    }

    public static boolean isItemAllowedByBattlefieldRole(ItemStack itemStack, Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return true;  //Edge case
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        return isMaterialAllowedByBattlefieldRole(weaponBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole);
    }
    
    private static boolean isMaterialAllowedByBattlefieldRole(Map<String, Set<BattlefieldRole>> materialRoleMap, Material material, BattlefieldRole battlefieldRole) {
        String materialKey = material.name().split("_")[0];
        
        Set<BattlefieldRole> rolesWhichCanUseThisItem = materialRoleMap.get(materialKey);
        if(rolesWhichCanUseThisItem == null) {
            return true; //No restrictions on this material
        } else {
            return rolesWhichCanUseThisItem.contains(battlefieldRole);
        }
    }

    public static BattlefieldRole getBattlefieldRole(Resident resident) {
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }

    public static BattlefieldRole getBattlefieldRole(Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return BattlefieldRole.LIGHT_INFANTRY;  //Edge case
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }

    public static void validateArmour(Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return;
        //Create Convenience variables
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        ItemStack[] inventoryContents = player.getInventory().getContents();
        List<Integer> invalidInventoryIndexes = new ArrayList<>();
        //Identify invalid armour (Slots 36-39)
        for(int i = 36; i <= 39; i++) {
            ItemStack itemStack = inventoryContents[i];
            if(itemStack != null && !isMaterialAllowedByBattlefieldRole(armourBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole)) {
                invalidInventoryIndexes.add(i);
            }
        }
        //Drop Invalid armour           
        Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), () -> {
            for(int inventoryIndex: invalidInventoryIndexes) {
                //Drop item on ground
                player.getWorld().dropItemNaturally(player.getLocation(), inventoryContents[inventoryIndex]);
                //Remove item from inventory
                player.getInventory().clear(inventoryIndex);
            }
        });
        //Send warning message(s)
        if(invalidInventoryIndexes.size() > 0) {
            Translatable errorMessage = Translatable.of("msg_warning_cannot_wear_this_armour");
            errorMessage.append(Translatable.of("msg_warning_how_to_view_and_change_role"));
            Messaging.sendErrorMsg(player, errorMessage);
        }
        //Validate mount armour
        validateMountArmour(player, playerBattlefieldRole);
    }

    private static void validateMountArmour(Player player, BattlefieldRole playerBattlefieldRole) {
        //Validate mount armour
        Horse mount = TownyCombatHorseUtil.getMount(player);
        if(mount != null) {
            ItemStack horseArmour = mount.getInventory().getArmor();
            if(horseArmour != null && !isMaterialAllowedByBattlefieldRole(armourBattlefieldRoleMap, horseArmour.getType(), playerBattlefieldRole)) {
                //Drop Invalid armour           
                Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), () -> {
                    //Drop item on ground
                    player.getWorld().dropItemNaturally(player.getLocation(), horseArmour);
                    //Remove item from inventory
                    mount.getInventory().clear(1);
                });
                //Send warning message(s)
                Translatable errorMessage = Translatable.of("msg_warning_horse_cannot_wear_this_armour");
                errorMessage.append(Translatable.of("msg_warning_how_to_view_and_change_role"));
                Messaging.sendErrorMsg(player, errorMessage);
            }
        }
    }
    
    public static long getTimeUntilNextRoleChange(Resident resident) {
        long timeOfLastRoleChange = TownyCombatResidentMetaDataController.getLastBattlefieldRoleSwitchTime(resident);
        long timeOfNextRoleChange = timeOfLastRoleChange + (long)(TownyCombatSettings.getBattlefieldRolesMinimumTimeBetweenRoleChangesDays() * TimeMgmt.ONE_DAY_IN_MILLIS);
        return timeOfNextRoleChange - System.currentTimeMillis();
    }

    public static void processChangeRoleAttempt(CommandSender commandSender, String roleAsString) throws TownyException {
        //Check if the player can change role
        if(!(commandSender instanceof Player))
            throw new TownyException("Cannot run this command from console"); //Scenario too rare to add translation
        Player player= (Player)commandSender;
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if(resident == null)
            throw new TownyException("Unknown resident"); //Scenario too rare too add translation
        long timeUntilNextRoleChange = getTimeUntilNextRoleChange(resident);
        if(timeUntilNextRoleChange > 0) {
            String timeUntilNextRoleChangeFormatted = TimeMgmt.getFormattedTimeValue(timeUntilNextRoleChange);
            Translatable errorMessage = Translatable.of("msg_warning_cannot_change_role_now", timeUntilNextRoleChangeFormatted);
            throw new TownyException(errorMessage);
        }
        //Change Role
        TownyCombatResidentMetaDataController.setBattlefieldRole(resident, roleAsString.toLowerCase());
        TownyCombatResidentMetaDataController.setLastBattlefieldRoleSwitchTime(resident, System.currentTimeMillis());
        resident.save();
        //Validate Armour
        TownyCombatBattlefieldRoleUtil.validateArmour(player);
        //Send success message
        Messaging.sendMsg(commandSender, Translatable.of("msg_changerole_success", roleAsString));
    }

    public static boolean isPlayerWearingArmour(Player player) {
        for(ItemStack armourPiece: player.getInventory().getArmorContents()) {
            if(armourPiece != null) 
                return true;
        }
        return false;
    }

    /**
     * Players with heavy roles, and their mounts, can get resistance
     */
    public static void giveRoleBasedDamageResistance() {
        BattlefieldRole playerBattlefieldRole;
        for(Player player: Bukkit.getOnlinePlayers()) {
            playerBattlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(player);
            
            if(playerBattlefieldRole == BattlefieldRole.HEAVY_INFANTRY
                    && TownyCombatBattlefieldRoleUtil.isPlayerWearingArmour(player)        
                    && TownyCombatHorseUtil.getMount(player) == null) {
                //Heavy infantry player gets effects as he is armoured + unmounted.
                giveRoleBasedDamageResistance(player);

            } else if(playerBattlefieldRole == BattlefieldRole.HEAVY_CAVALRY) {
                Horse mount = TownyCombatHorseUtil.getMount(player);
                if (mount != null && isHorseWearingArmour(mount) && isPlayerWearingArmour(player)) {
                    //Heavy cavalry player+horse gets effects as both players+mount are armoured.
                    giveRoleBasedDamageResistance(player);
                    giveRoleBasedDamageResistance(mount);
                }
            }
        }
    }

    private static boolean isHorseWearingArmour(Horse mount) {
        return mount.getInventory().getArmor() != null;
    }
    
    /////////////// PROCESS NON-SPLASH POTIONS ///////////////
    
    public static void evaluateEffectOfDrinkingPotioxn(PlayerItemConsumeEvent event) {
        //Convenience variables and basic checks
        Player player = event.getPlayer();
        ItemStack potionItemStack = event.getItem();
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return;
        BattlefieldRole battlefieldRole = getBattlefieldRole(resident);
        PotionMeta potionMeta = (PotionMeta) potionItemStack.getItemMeta();
        if(potionMeta == null)
            return;
        if(potionMeta.getBasePotionData().getType().getEffectType() == null)
            return;
        //Create a replacement potion if required
        ItemStack replacementPotion = null;
        switch (battlefieldRole) {
            case LIGHT_INFANTRY:
            case LIGHT_CAVALRY:
                if (potionMeta.getBasePotionData().getType().getEffectType().equals(PotionEffectType.SPEED)) {
                    replacementPotion = getUpdatedSpeedPotion(potionMeta.getBasePotionData(), 1);
                }
                break;
            case MEDIUM_INFANTRY:
            case MEDIUM_CAVALRY:
                if (potionMeta.getBasePotionData().getType().getEffectType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                    replacementPotion = getUpdatedStrengthPotion(potionMeta.getBasePotionData(), 1);
                } else if (potionMeta.getBasePotionData().getType().getEffectType().equals(PotionEffectType.SPEED)) {
                    replacementPotion = getUpdatedSpeedPotion(potionMeta.getBasePotionData(), -1);
                }
                break;
            case HEAVY_INFANTRY:
            case HEAVY_CAVALRY:
                if (potionMeta.getBasePotionData().getType().getEffectType().equals(PotionEffectType.SPEED)) {
                    replacementPotion = getUpdatedSpeedPotion(potionMeta.getBasePotionData(), -2);
                }
                break;
            default:
                throw new RuntimeException("Unknown Battlefield Role");
        }
        //Set the replacement potion into the event
        if (replacementPotion != null) {
            event.setItem(replacementPotion);
        }
    }

    private static @Nullable ItemStack getUpdatedSpeedPotion(PotionData basePotionData, int powerModifier) {
        return getUpdatedPotion(basePotionData, powerModifier, 180, 480);
    }

    private static @Nullable ItemStack getUpdatedStrengthPotion(PotionData basePotionData, int powerModifier) {
        return getUpdatedPotion(basePotionData, powerModifier, 180, 480);
    }

    private static @Nullable ItemStack getUpdatedPotion(PotionData basePotionData, int powerModifier, int nonExtendedDurationSeconds, int extendedDurationSeconds) {
        //Create the updated potion
        ItemStack newPotion = new ItemStack(Material.POTION);
        newPotion.setAmount(1);
        //Add the required effect (if any)
        if(basePotionData.getType().getEffectType() != null) {
            int amplifier = basePotionData.isUpgraded() ? 1 : 0;
            amplifier += powerModifier;
            if(amplifier >= 0) {
                int duration = basePotionData.isExtended() ? extendedDurationSeconds * 20 : nonExtendedDurationSeconds * 20;
                PotionEffect newPotionEffect = new PotionEffect(basePotionData.getType().getEffectType(), duration, amplifier, true, true, true);
                PotionMeta newPotionMeta = (PotionMeta) newPotion.getItemMeta();
                if(newPotionMeta == null)
                    return null;
                newPotionMeta.addCustomEffect(newPotionEffect, true);
                newPotion.setItemMeta(newPotionMeta);
            }
        }
        //Return the update potion
        return newPotion;
    }

    
    //////////////  Process Drunk Potions //////////////////

    /**
     * Process a potion drunk by a player
     * Apply any required role-based adjustments
     * 
     * NOTE: Potions of strength will already be cancelled for player riders
     * 
     * @param event the potion drinking event
     */
    public static void processItemConsumptionEvent(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.POTION) {
            //Transform the potion effect into a PotionEffect object for reference
            PotionMeta potionMeta = (PotionMeta) event.getItem().getItemMeta();
            PotionData potionData = potionMeta.getBasePotionData();
            PotionEffectType potionEffectType = potionData.getType().getEffectType();
            if(potionEffectType != null && potionEffectType.equals(PotionEffectType.SPEED)) {
                int potionAmplifier = potionData.isUpgraded() ? 1 : 0;
                int potionDurationTicks = (potionData.isExtended() ? 480 : potionData.isUpgraded() ? 90 : 180) * 20;
                PotionEffect originalPotionEffect = new PotionEffect(potionEffectType, potionDurationTicks, potionAmplifier, true, true, true);
                //Apply adjustment
                if (grantAdjustedPotionSpeedEffectToPlayer(originalPotionEffect, event.getPlayer())) {
                    event.setItem(new ItemStack(Material.GLASS_BOTTLE));
                }
            }
        }
    }
    
    //////////////// Process Splash Potions /////////////////

    /**
     * Process the splash potion event
     * Apply any required role-based adjustments
     * 
     * NOTE: Strength effects on player horse riders will already have been removed 
     *
     * @param event the splash potion effect
     */
    public static void processSplashPotionEvent(PotionSplashEvent event) {
        for (PotionEffect potionEffect : event.getPotion().getEffects()) {
            if (potionEffect.getType() == PotionEffectType.SPEED) {
                for (LivingEntity entity : event.getAffectedEntities()) {
                    if (entity instanceof Player) {
                        if(grantAdjustedPotionSpeedEffectToPlayer(potionEffect, (Player) entity)) {
                            event.setIntensity(entity, 0);
                        }
                    } else if (entity instanceof Horse) {
                        if(grantAdjustedPotionSpeedEffectToHorse(potionEffect, (Horse) entity)) {
                            event.setIntensity(entity, 0);
                        }
                    }
                }
            }
        }
    }
    
    private static boolean grantAdjustedPotionSpeedEffectToPlayer(PotionEffect originalPotionEffect, Player player) {
        BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(player);
        boolean mounted = TownyCombatHorseUtil.getMount(player) != null;
        switch (battlefieldRole) {
            case LIGHT_INFANTRY:
                if (!mounted) {
                    grantIncreasedPotionEffectToLivingEntity(originalPotionEffect, player);
                    Messaging.sendMsg(player, "SPEED EFFECT WAS INCREASED ");
                    return true;
                }
                break;
            case LIGHT_CAVALRY:
                if(mounted) {
                    grantIncreasedPotionEffectToLivingEntity(originalPotionEffect, player);
                    Messaging.sendMsg(player, "SPEED EFFECT WAS INCREASED ");
                    return true;
                }
                break;
            case MEDIUM_INFANTRY:
            case MEDIUM_CAVALRY:
                grantDecreasedPotionEffectToLivingEntity(originalPotionEffect, player);
                Messaging.sendMsg(player, "SPEED EFFECT WAS DECREASED ");
                return true;
            case HEAVY_INFANTRY:
            case HEAVY_CAVALRY:
                Messaging.sendMsg(player, "NO EFFECT");
                return true;
        }
        return false;
    }

    private static boolean grantAdjustedPotionSpeedEffectToHorse(PotionEffect potionEffect, Horse horse) {
        Player rider = TownyCombatHorseUtil.getPlayerRider(horse);
        if (rider == null)
            return false;
        BattlefieldRole riderBattlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(rider);
        switch (riderBattlefieldRole) {
            case LIGHT_CAVALRY:
                grantIncreasedPotionEffectToLivingEntity(potionEffect, horse);
                Messaging.sendMsg(rider, "SPEED EFFECT WAS INCREASED ON HORSE");
                return true;
            case MEDIUM_INFANTRY:
            case MEDIUM_CAVALRY:
                grantDecreasedPotionEffectToLivingEntity(potionEffect, horse);
                Messaging.sendMsg(rider, "SPEED EFFECT WAS DECREASED ON HORSE");
                return true;
            case HEAVY_INFANTRY:
            case HEAVY_CAVALRY:
                Messaging.sendMsg(rider, "NO EFFECT ON HORSE");
                return true;
        }
        return false;
    }



    /////////////////////////////////////////////////////
    
    private static void giveRoleBasedDamageResistance(LivingEntity livingEntity) {
        final int effectDurationTicks = (int) (TimeTools.convertToTicks(TownySettings.getShortInterval() + 10));
        givePotionEffectUnlessAmplifierIsNegative(PotionEffectType.DAMAGE_RESISTANCE, effectDurationTicks,0, false, false, true, livingEntity);
    }

    private static void giveEffectUnlessAmplifierIsNegative(PotionEffectType potionEffectType, int durationTicks, int amplifier, LivingEntity livingEntity) {
        givePotionEffectUnlessAmplifierIsNegative(
                potionEffectType,
                durationTicks,
                amplifier,
                true,
                true,
                true,
                livingEntity);
    }
    
    private static void givePotionEffectUnlessAmplifierIsNegative(PotionEffectType potionEffectType, 
                                                                  int durationInTicks,
                                                                  int amplifier,
                                                                  boolean ambient,
                                                                  boolean particles,
                                                                  boolean icon,
                                                                  LivingEntity livingEntity) {
        if(amplifier >= 0) {
            Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), new Runnable() {
                public void run() {
                    livingEntity.addPotionEffect(new PotionEffect(potionEffectType, durationInTicks, amplifier, ambient, particles, icon));
                }
            });
        }
    }

    public static void validatePotionEffectsOnMount(Horse mountedHorse, Player playerRider) {
        BattlefieldRole riderBattlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(playerRider);
        PotionEffect speedEffect = mountedHorse.getPotionEffect(PotionEffectType.SPEED);

        switch (riderBattlefieldRole) {
            case MEDIUM_INFANTRY:
            case MEDIUM_CAVALRY:
                //If speed is power 2, reduce it to 1
                if (speedEffect != null && speedEffect.getAmplifier() > 0) {
                    mountedHorse.removePotionEffect(PotionEffectType.SPEED);
                    grantDecreasedPotionEffectToLivingEntity(speedEffect, mountedHorse);
                }
                break;
            case HEAVY_INFANTRY:
            case HEAVY_CAVALRY:
                //Remove speed effect
                if (speedEffect != null) {
                    mountedHorse.removePotionEffect(PotionEffectType.SPEED);
                }
                break;
        }
    }

    private static void grantIncreasedPotionEffectToLivingEntity(PotionEffect originalEffect, LivingEntity livingEntity) {
        grantAdjustedPotionEffectToLivingEntity(originalEffect, livingEntity, 1);
    }

    private static void grantDecreasedPotionEffectToLivingEntity(PotionEffect originalEffect, LivingEntity livingEntity) {
        grantAdjustedPotionEffectToLivingEntity(originalEffect, livingEntity, -1);
    }

    private static void grantAdjustedPotionEffectToLivingEntity(PotionEffect originalEffect, LivingEntity livingEntity, int adjustment) {
        giveEffectUnlessAmplifierIsNegative(
                originalEffect.getType(),
                originalEffect.getDuration(),
                (originalEffect.getAmplifier() + adjustment),
                livingEntity);
    }
    
}
