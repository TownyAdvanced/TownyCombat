package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TownyCombatBattlefieldRoleUtil {

    public static final List<Material> lightRoleArmour = Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS);
    public static final List<Material> lightRoleWeapons = Arrays.asList(Material.BOW, Material.WOODEN_SWORD, Material.WOODEN_AXE);
    public static final Map<Material, List<BattlefieldRole>> armourBattlefieldRoleMap;
    public static final Map<Material, List<BattlefieldRole>> weaponBattlefieldRoleMap;

    static {
        armourBattlefieldRoleMap = new HashMap<>();
        weaponBattlefieldRoleMap = new HashMap<>();
        addToMaterialBattlefieldRoleMap(armourBattlefieldRoleMap, lightRoleArmour, BattlefieldRole.LIGHT);
        addToMaterialBattlefieldRoleMap(weaponBattlefieldRoleMap, lightRoleWeapons, BattlefieldRole.LIGHT);
    }
    
    private static void addToMaterialBattlefieldRoleMap(Map<Material, List<BattlefieldRole>> materialBattlefieldRoleMap, List<Material> materialList, BattlefieldRole battlefieldRole) {
        for(Material material: materialList) {
            if(materialBattlefieldRoleMap.containsKey(material)) {
                materialBattlefieldRoleMap.get(material).add(battlefieldRole);
            } else {
                List<BattlefieldRole> battlefieldRoleList = new ArrayList<>();
                battlefieldRoleList.add(battlefieldRole);
                materialBattlefieldRoleMap.put(material, battlefieldRoleList);
            }
        }
    }
    
    public static boolean isMaterialAllowedByBattlefieldRole(Map<Material, List<BattlefieldRole>> materialRoleMap, Material material, BattlefieldRole battlefieldRole) {
        List<BattlefieldRole> rolesWhichCanUseThisItem = materialRoleMap.get(material);
        if(rolesWhichCanUseThisItem == null) {
            return true; //Anyone can use it
        } else {
            return rolesWhichCanUseThisItem.contains(battlefieldRole);
        }
    }

    public static BattlefieldRole getBattlefieldRole(Resident resident) {
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }

    public static void validateInventoryContents(HumanEntity player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return;
        //Create Convenience variables
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        ItemStack[] inventoryContents = player.getInventory().getContents();
        List<Integer> invalidArmourIndexes = new ArrayList<>();
        List<Integer> invalidWeaponIndexes = new ArrayList<>();
        //Identify invalid armour (Slots 36-39)
        for(int i = 36; i <= 39; i++) {
            ItemStack itemStack = inventoryContents[i];
            if(itemStack != null && isMaterialAllowedByBattlefieldRole(armourBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole)) {
                invalidArmourIndexes.add(i);
            }
        }
        //Identify Invalid Weapons (Slots 0-8 is hotbar)
        for(int i = 0; i <= 8; i++) {
            ItemStack itemStack = inventoryContents[i];
            if(itemStack != null && isMaterialAllowedByBattlefieldRole(armourBattlefieldRoleMap, itemStack.getType(), playerBattlefieldRole)) {
                invalidWeaponIndexes.add(i);
            }
        }
        //Drop all Invalid Items           
        List<Integer> invalidItemIndexes = new ArrayList<>();
        invalidItemIndexes.addAll(invalidArmourIndexes);
        invalidItemIndexes.addAll(invalidWeaponIndexes);
        Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), () -> {
            for(Integer inventoryIndex: invalidItemIndexes) {
                //Drop item on ground
                player.getWorld().dropItemNaturally(player.getLocation(), inventoryContents[inventoryIndex]);
                //Remove item from inventory
                player.getInventory().clear(inventoryIndex);
            }
        });
        //Send warning message(s)
        if(invalidArmourIndexes.size() > 0) {
            Translatable errorMessage = Translatable.of("msg_warning_cannot_wear_this_armour");
            errorMessage.append(Translatable.of("msg_warning_how_to_view_and_change_role"));
            Messaging.sendErrorMsg(player, errorMessage);
        }
        if(invalidWeaponIndexes.size() > 0) {
            Translatable errorMessage = Translatable.of("msg_warning_cannot_wield_this_weapon");
            errorMessage.append(Translatable.of("msg_warning_how_to_view_and_change_role"));
            Messaging.sendErrorMsg(player, errorMessage);
        }
    }
    
    private static long getTimeOfNextRoleChange(Resident resident) {
        return 0;
    }

    public static void processChangeRoleAttempt(CommandSender commandSender, String roleAsString) throws TownyException {
        //Check if the player can change role
        if(!(commandSender instanceof Player))
            throw new TownyException("Cannot run this command from console"); //Scenario too rare to add translation
        Player player= (Player)commandSender;
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if(resident == null)
            throw new TownyException("Unknown resident"); //Scenario too rare too add translation
        long timeOfNextRoleChange = getTimeOfNextRoleChange(resident);
        if(System.currentTimeMillis() < timeOfNextRoleChange) {
            String timeOfNextRoleChangeFormatted = TimeMgmt.getFormattedTimeValue(timeOfNextRoleChange);
            Translatable errorMessage = Translatable.of("msg_warning_cannot_change_role_now", timeOfNextRoleChangeFormatted);
            throw new TownyException(errorMessage);
        }
        //Change Role
        TownyCombatResidentMetaDataController.setBattlefieldRole(resident, roleAsString.toLowerCase());
        resident.save();
        //Send success message
        Messaging.sendMsg(commandSender, Translatable.of("msg_changerole_success", roleAsString).translate(Locale.ROOT));
    }
}
