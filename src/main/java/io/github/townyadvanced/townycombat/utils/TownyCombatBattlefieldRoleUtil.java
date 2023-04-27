package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
            //Send warning message
            Messaging.sendErrorMsg(inventoryClickEvent.getWhoClicked(), errorMessage);
            //Drop the item (Note: cancelling the event would delete the item)
            inventoryClickEvent.getWhoClicked().getWorld().dropItem(inventoryClickEvent.getWhoClicked().getLocation(), item);
        }
    }
    
    private static boolean canPlayerChangeRoleNow(Resident resident) {
        //todo - check metadata
        return true;
    }
    
    private static String getFormattedTimeUntilNextRoleChange(Resident resident) {
        return "Never hur hur";
    }

    public static void processPlayerWeaponWieldAttempt(InventoryClickEvent inventoryClickEvent) {
        
    }
}
