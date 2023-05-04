package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.TimeMgmt;
import com.palmergames.util.TimeTools;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
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
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, leatherArmourKey, BattlefieldRole.LIGHT);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, ironArmourKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, chainArmourKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, turtleArmourKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, goldArmourKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, diamondArmourKey, BattlefieldRole.HEAVY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, netheriteArmourKey, BattlefieldRole.HEAVY);
        
        weaponBattlefieldRoleMap = new HashMap<>();
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, woodenWeaponKey, BattlefieldRole.LIGHT);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, bowKey, BattlefieldRole.LIGHT);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, stoneWeaponKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, ironWeaponKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, crossbowKey, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, diamondWeaponKey, BattlefieldRole.HEAVY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, netheriteWeaponKey, BattlefieldRole.HEAVY);
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

        TownyCombat.info("MATERIAL NAME: " + materialKey);
        
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
            return BattlefieldRole.LIGHT;  //Edge case
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }
    
    public static void validateArmourSlots(HumanEntity player) {
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
    }

    private static long getTimeUntilNextRoleChange(Resident resident) {
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

    public static void giveEffectsToHeavyPlayersWearingArmour() {
        //Any players with the heavy role who are wearing armour, get some effects.
        for(Player player: Bukkit.getOnlinePlayers()) {
            if(TownyCombatBattlefieldRoleUtil.getBattlefieldRole(player) == BattlefieldRole.HEAVY
                    && TownyCombatBattlefieldRoleUtil.isPlayerWearingArmour(player)) {

                final int effectDurationTicks = (int)(TimeTools.convertToTicks(TownySettings.getShortInterval() + 10));
                Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), new Runnable() {
                    public void run() {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectDurationTicks,0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, effectDurationTicks,0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, effectDurationTicks,-2));
                    }
                });
            }
        }
    }
    
    /////////////// Non-Splash Potions ///////////////
    public static void evaluateEffectOfDrinkingPotion(PlayerItemConsumeEvent event) {
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
        //Create a replacement potion if required
        ItemStack replacementPotion = null;
        switch (battlefieldRole) {
            case LIGHT:
                if (potionMeta.getBasePotionData().getType().getEffectType() == PotionEffectType.SPEED) {
                    replacementPotion = getUpdatedSpeedPotion(potionMeta.getBasePotionData(), 1);
                }
                break;
            case MEDIUM:
                if (potionMeta.getBasePotionData().getType().getEffectType() == PotionEffectType.INCREASE_DAMAGE) {
                    replacementPotion =  getUpdatedStrengthPotion(potionMeta.getBasePotionData(), 1);
                }
                if (potionMeta.getBasePotionData().getType().getEffectType() == PotionEffectType.SPEED) {
                    replacementPotion = getUpdatedSpeedPotion(potionMeta.getBasePotionData(), -1);
                }
                break;
            case HEAVY:
                if (potionMeta.getBasePotionData().getType().getEffectType() == PotionEffectType.SPEED) {
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
            if(amplifier > 0) {
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

    //////////////// Splash Potions /////////////////
    
    public static void evaluateEffectOfSplashPotion(PotionSplashEvent event, Player player) {
        BattlefieldRole battlefieldRole = getBattlefieldRole(player);
        switch (battlefieldRole) {
            case LIGHT:
                for(PotionEffect potionEffect: event.getPotion().getEffects()) {
                    if(potionEffect.getType() == PotionEffectType.SPEED) {
                        //Set original effect on player to zero
                        event.setIntensity(player, 0);
                        //Give a new effect to the player
                        int amplifier = potionEffect.getAmplifier() + 1;
                        int duration = (int)((double)potionEffect.getDuration() * event.getIntensity(player));
                        givePotionEffectToPlayerUnlessAmplifierIsNegative(potionEffect.getType(), amplifier, duration, player);
                    }
                }
                break;
            case MEDIUM:
                for(PotionEffect potionEffect: event.getPotion().getEffects()) {
                    if(potionEffect.getType() == PotionEffectType.SPEED) {
                        //Set original effect on player to zero
                        event.setIntensity(player, 0);
                        //Give a new effect to the player
                        int amplifier = potionEffect.getAmplifier() - 1;
                        int duration = (int)((double)potionEffect.getDuration() * event.getIntensity(player));
                        givePotionEffectToPlayerUnlessAmplifierIsNegative(potionEffect.getType(), amplifier, duration, player);
                    }
                    if(potionEffect.getType() == PotionEffectType.INCREASE_DAMAGE) {
                        //Set original effect on player to zero
                        event.setIntensity(player, 0);
                        //Give a new effect to the player
                        int amplifier = potionEffect.getAmplifier() + 1;
                        int duration = (int)((double)potionEffect.getDuration() * event.getIntensity(player));
                        givePotionEffectToPlayerUnlessAmplifierIsNegative(potionEffect.getType(), amplifier, duration, player);
                    }
                }
                break;
            case HEAVY:
                for(PotionEffect potionEffect: event.getPotion().getEffects()) {
                    if(potionEffect.getType() == PotionEffectType.SPEED) {
                        //Set original effect on player to zero
                        event.setIntensity(player, 0);
                        //Give a new effect to the player
                        int amplifier = potionEffect.getAmplifier() - 2;
                        int duration = (int)((double)potionEffect.getDuration() * event.getIntensity(player));
                        givePotionEffectToPlayerUnlessAmplifierIsNegative(potionEffect.getType(), amplifier, duration, player);
                    }
                }
                break;
            default:
                throw new RuntimeException("Unknown Battlefield Role");
        }
    }

    private static void givePotionEffectToPlayerUnlessAmplifierIsNegative(PotionEffectType potionEffectType, int amplifier, int durationInTicks, Player player) {
        if(amplifier > 0) {
            Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), new Runnable() {
                public void run() {
                    player.addPotionEffect(new PotionEffect(potionEffectType, durationInTicks, amplifier));
                }
            });
        }
    }

}
