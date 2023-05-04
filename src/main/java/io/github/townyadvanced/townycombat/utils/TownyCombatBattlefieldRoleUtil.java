package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class TownyCombatBattlefieldRoleUtil {

    public static final List<Material> leatherArmour = Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS);
    public static final List<Material> ironArmour = Arrays.asList(Material.IRON_BOOTS, Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.IRON_LEGGINGS);
    public static final List<Material> chainArmour = Arrays.asList(Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS);
    public static final List<Material> turtleArmour = Arrays.asList(Material.TURTLE_HELMET);
    public static final List<Material> goldArmour = Arrays.asList(Material.GOLDEN_BOOTS, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_HELMET, Material.GOLDEN_LEGGINGS);
    public static final List<Material> diamondArmour = Arrays.asList(Material.DIAMOND_BOOTS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS);
    public static final List<Material> netheriteArmour = Arrays.asList(Material.NETHERITE_BOOTS, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_HELMET, Material.NETHERITE_LEGGINGS);
    public static final List<Material> woodenWeapons = Arrays.asList(Material.WOODEN_SWORD, Material.WOODEN_AXE);
    public static final List<Material> stoneWeapons = Arrays.asList(Material.STONE_SWORD, Material.STONE_AXE);
    public static final List<Material> ironWeapons = Arrays.asList(Material.IRON_SWORD, Material.IRON_AXE);
    public static final List<Material> diamondWeapons = Arrays.asList(Material.DIAMOND_SWORD, Material.DIAMOND_AXE);
    public static final List<Material> netheriteWeapons = Arrays.asList(Material.NETHERITE_SWORD, Material.NETHERITE_AXE);
    public static final Map<Material, Set<BattlefieldRole>> armourBattlefieldRoleMap;
    public static final Map<Material, Set<BattlefieldRole>> weaponBattlefieldRoleMap;

    static {
        armourBattlefieldRoleMap = new HashMap<>();
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, leatherArmour, BattlefieldRole.LIGHT);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, ironArmour, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, chainArmour, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, turtleArmour, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, goldArmour, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, diamondArmour, BattlefieldRole.HEAVY);
        addMaterialsToBattlefieldRoleMap(armourBattlefieldRoleMap, netheriteArmour, BattlefieldRole.HEAVY);
        
        weaponBattlefieldRoleMap = new HashMap<>();
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, woodenWeapons, BattlefieldRole.LIGHT);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, stoneWeapons, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, ironWeapons, BattlefieldRole.MEDIUM);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, diamondWeapons, BattlefieldRole.HEAVY);
        addMaterialsToBattlefieldRoleMap(weaponBattlefieldRoleMap, netheriteWeapons, BattlefieldRole.HEAVY);
    }

    private static void addMaterialsToBattlefieldRoleMap(Map<Material, Set<BattlefieldRole>> materialBattlefieldRoleMap, List<Material> materialList, BattlefieldRole battlefieldRole) {
        for(Material material: materialList) {
            if(materialBattlefieldRoleMap.containsKey(material)) {
                materialBattlefieldRoleMap.get(material).add(battlefieldRole);
            } else {
                Set<BattlefieldRole> battlefieldRoleSet = new HashSet<>();
                battlefieldRoleSet.add(battlefieldRole);
                materialBattlefieldRoleMap.put(material, battlefieldRoleSet);
            }
        }
    }

    public static boolean isItemAllowedByBattlefieldRole(ItemStack itemStack, Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return true;  //Edge case
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        return isMaterialAllowedByBattlefieldRole(weaponBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole);
    }
    
    private static boolean isMaterialAllowedByBattlefieldRole(Map<Material, Set<BattlefieldRole>> materialRoleMap, Material material, BattlefieldRole battlefieldRole) {
        Set<BattlefieldRole> rolesWhichCanUseThisItem = materialRoleMap.get(material);
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
            if(itemStack != null && isMaterialAllowedByBattlefieldRole(armourBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole)) {
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
        Messaging.sendMsg(commandSender, Translatable.of("msg_changerole_success", roleAsString).translate(Locale.ROOT));
    }
    
    public static @Nullable ItemStack getAmplifiedPotion(Player player, ItemStack potionItemStack) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return null;
        BattlefieldRole battlefieldRole = getBattlefieldRole(resident);
        PotionMeta potionMeta = (PotionMeta) potionItemStack.getItemMeta();
        if(potionMeta == null)
            return null;
        switch (battlefieldRole) {
            case LIGHT:
                if (potionMeta.getBasePotionData().getType().getEffectType() == PotionEffectType.SPEED) {
                    return getAmplifiedSpeedPotion(potionMeta.getBasePotionData());
                }
                break;
            default:
                throw new RuntimeException("Unknown Role");
        }
        return null;
    }
    
    private static @Nullable ItemStack getAmplifiedSpeedPotion(PotionData basePotionData) {
        return getAmplifiedPotion(basePotionData, 180, 480);
    }

    private static @Nullable ItemStack getAmplifiedPotion(PotionData basePotionData, int nonExtendedDurationSeconds, int extendedDurationSeconds) {
        //Create the updated effect
        int amplifier = basePotionData.isUpgraded() ? 2 : 1;
        int duration = basePotionData.isExtended() ? extendedDurationSeconds * 20 : nonExtendedDurationSeconds * 20;
        amplifier++;
        if(basePotionData.getType().getEffectType() == null)
            return null;
        PotionEffect newPotionEffect = new PotionEffect(basePotionData.getType().getEffectType(), duration, amplifier, true, true, true);

        //Create the updated potion
        ItemStack newPotion = new ItemStack(Material.POTION);
        newPotion.setAmount(1);
        PotionMeta newPotionMeta = (PotionMeta) newPotion.getItemMeta();
        if(newPotionMeta == null)
            return null;
        newPotionMeta.addCustomEffect(newPotionEffect, true);
        newPotion.setItemMeta(newPotionMeta);

        //Return the update potion
        return newPotion;
    }
}
