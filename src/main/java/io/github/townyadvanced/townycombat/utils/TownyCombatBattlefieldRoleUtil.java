package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.bukkit.towny.object.Translator;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
        return materialBattlefieldRoleMap.get(material);
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
        BattlefieldRole materialBattlefieldRole = getBattlefieldRole(inventoryClickEvent.getCursor().getType());
        BattlefieldRole playerBattlefieldRole = getBattlefieldRole(resident);
        
        if(materialBattlefieldRole != playerBattlefieldRole) {
            //Compose error message
            String requiredRoleText = Translatable.of(materialBattlefieldRole.getNameKey()).translate(Locale.ROOT); 
            String currentRoleText = Translatable.of(playerBattlefieldRole.getNameKey()).translate(Locale.ROOT); ;
            Translatable errorMessage = Translatable.of("msg_warning_cannot_wear_this_armour", requiredRoleText);
            if(canPlayerChangeRoleNow(resident)) {
                errorMessage.append(Translatable.of("msg_warning_do_you_wish_to_change_role_now", currentRoleText, requiredRoleText));
            } else {
                errorMessage.append(Translatable.of("msg_warning_cannot_change_role_now", getFormattedTimeUntilNextRoleChange(resident)));
            }
            //Send error message
            Messaging.sendErrorMsg(inventoryClickEvent.getWhoClicked(), errorMessage);
            //Cancel event
            inventoryClickEvent.setCancelled(true);
            //TODO - Ensure item does not disappear
        }
    }
    
    private static boolean canPlayerChangeRoleNow(Resident resident) {
        //todo - check metadata
        return true;
    }
    
    private static String getFormattedTimeUntilNextRoleChange(Resident resident) {
        return "Never hur hur";
    }

    public static void processPlayerWeaponWieldAttempt(InventoryClickEvent event) {
        
    }
}
