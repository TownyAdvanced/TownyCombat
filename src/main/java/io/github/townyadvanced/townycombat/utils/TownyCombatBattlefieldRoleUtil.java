package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TownyCombatBattlefieldRoleUtil {

    public static final List<Material> lightPlayerArmour = Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS);

    public static final Map<Material, BattlefieldRole> materialBattlefieldRoleMap;

    static {
        //Create the material-battlefieldRole map
        materialBattlefieldRoleMap = new HashMap<>();
        for(Material material: lightPlayerArmour) {
            materialBattlefieldRoleMap.put(material, BattlefieldRole.LIGHT);
        }
    }

    public static BattlefieldRole getBattlefieldRole(Material material) {
        BattlefieldRole result = materialBattlefieldRoleMap.get(material);
        if(result == null)
            return BattlefieldRole.HEAVY;
        else 
            return result;
    }

    public static BattlefieldRole getBattlefieldRole(Resident resident) {
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }

    public static void validateInventoryContents(HumanEntity player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null)
            return;

        //Identify Invalid Armour
        List<ItemStack> inventoryContentsList = Arrays.asList(player.getInventory().getContents());
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        List<Integer> invalidArmourIndexes = new ArrayList<>();
        for(ItemStack itemStack: player.getInventory().getArmorContents()) {
            if(itemStack != null && getBattlefieldRole(itemStack.getType()) != playerBattlefieldRole) {
                invalidArmourIndexes.add(inventoryContentsList.indexOf(itemStack));
            }
        }
        //Identify Invalid Weapons
        List<Integer> invalidWeaponIndexes = new ArrayList<>();
        //Drop all Invalid Items           
        List<Integer> invalidItemIndexes = new ArrayList<>();
        invalidItemIndexes.addAll(invalidArmourIndexes);
        invalidItemIndexes.addAll(invalidWeaponIndexes);
        Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), () -> {
            for(Integer inventoryIndex: invalidItemIndexes) {
                //Drop item on ground
                player.getWorld().dropItemNaturally(player.getLocation(), player.getInventory().getContents()[inventoryIndex]);
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
    }
        
    public static void processPlayerArmourWearAttempt(InventoryClickEvent inventoryClickEvent) {
        if(inventoryClickEvent.getCursor() == null || inventoryClickEvent.getCursor().getType() == Material.AIR)
            return;
        Resident resident = TownyAPI.getInstance().getResident(inventoryClickEvent.getWhoClicked().getUniqueId());
        if(resident == null)
            return;
        ItemStack item = inventoryClickEvent.getCursor();
        BattlefieldRole materialBattlefieldRole = getBattlefieldRole(item.getType());
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);

        
        if(materialBattlefieldRole != playerBattlefieldRole) {
            //Compose error message
            String requiredRoleText = Translatable.of(materialBattlefieldRole.getNameKey()).translate(Locale.ROOT); 
            Translatable errorMessage = Translatable.of("msg_warning_cannot_wear_this_armour", requiredRoleText);
            errorMessage.append(Translatable.of("msg_warning_how_to_view_and_change_role"));
            
            //inventoryClickEvent.set
            inventoryClickEvent.setCancelled(true);
            inventoryClickEvent.setCurrentItem(null);
            //Drop the item (Note: cancelling the event would delete the item)
            Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), () -> {
                //Remove item from creatures inventory
               // inventoryClickEvent.getWhoClicked().getInventory().remove(item);
                //Drop the item on the ground
                //inventoryClickEvent.getWhoClicked().getWorld().drop(inventoryClickEvent.getWhoClicked().getLocation(), item);
            });
            //Send warning message
            Messaging.sendErrorMsg(inventoryClickEvent.getWhoClicked(), errorMessage);
        }
    }
    
    private static boolean canPlayerChangeRoleNow(Resident resident) {
        //todo - check metadata
        return true;
    }
    
    
    private static long getTimeOfNextRoleChange(Resident resident) {
        return 0;
    }
    

    public static void processPlayerWeaponWieldAttempt(InventoryClickEvent inventoryClickEvent) {
        
    }

    public static void processChangeRoleAttempt(CommandSender commandSender, String roleAsString) throws TownyException {
        //Check if the player can change role
        if(!(commandSender instanceof Player))
            throw new TownyException("Cannot run this command from console"); //Scenario too rare to add translation
        Player player= (Player)commandSender;
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if(resident == null)
            throw new RuntimeException("Unknown resident"); //Scenario too rare too add translation
        long timeOfNextRoleChange = getTimeOfNextRoleChange(resident);
        if(System.currentTimeMillis() < timeOfNextRoleChange) {
            String timeOfNextRoleChangeFormatted = TimeMgmt.getFormattedTimeValue(timeOfNextRoleChange);
            Translatable errorMessage = Translatable.of("msg_warning_cannot_change_role_now", timeOfNextRoleChangeFormatted);
            throw new TownyException(errorMessage);
        }
        //Change Role
        TownyCombatResidentMetaDataController.setBattlefieldRole(resident, roleAsString);
        resident.save();
    }
}
