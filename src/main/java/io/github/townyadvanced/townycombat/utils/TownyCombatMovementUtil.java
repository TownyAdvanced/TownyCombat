package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatMovementUtil {
    private static final double VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED = 0.1;
    private static Map<Player, Double> playerEncumbrancePercentageMap = new HashMap<>();  //Encumbrance = Walk-speed-slow

    public static void adjustAllPlayerAndMountSpeeds() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            adjustPlayerAndMountSpeeds(player);
        }
    }

    public static void adjustPlayerAndMountSpeeds(Player player) {
        //Apply adjustments to player walk
        adjustPlayerWalkSpeed(player);
        //Apply adjustments to mount walk
        if(player.isInsideVehicle() && player.getVehicle() instanceof AbstractHorse) {
            adjustMountWalkSpeed(player);
        }
    }

    private static void adjustPlayerWalkSpeed(Player player) {
        //Calculate generic speed adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericInfantrySpeedAdjustmentPercentage();

        //Calculate slow due to encumbrance
        Map<Material, Double> materialEncumbrancePercentageMap = TownyCombatSettings.getInfantryMaterialEncumbrancePercentageMap();
        double totalEncumbrancePercentage = 0;
        Double itemEncumbrancePercentage;
        for(ItemStack itemStack: player.getInventory().getContents()) {
            if(itemStack != null) {
                itemEncumbrancePercentage = materialEncumbrancePercentageMap.get(itemStack.getType());
                if(itemEncumbrancePercentage != null) {
                    totalEncumbrancePercentage += itemEncumbrancePercentage;
                }
            }
        }      
        playerEncumbrancePercentageMap.put(player, totalEncumbrancePercentage);

        //Calculate total speed adjustment
        double recalculatedSpeed = 
            VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED + (VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED * ((genericSpeedAdjustmentPercentage + totalEncumbrancePercentage) / 100));
        //Sanitize
        if(recalculatedSpeed < 0)
            recalculatedSpeed = 0;
        else if(recalculatedSpeed > 1)
            recalculatedSpeed = 1;
        //Apply
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed);
    }
    
    private static void adjustMountWalkSpeed(Player player) {
        AbstractHorse mount = (AbstractHorse)player.getVehicle();
        double baseWalkSpeed = mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue();
        System.out.println("MOUNT DEFAULT SPEED: " + baseWalkSpeed);

        //Calculate generic adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericCavalrySpeedAdjustmentPercentage();

        //Calculate encumbrance
        //1. Encumbrance due to weight/bulk of horses own armour
        double totalEncumbrancePercentage; 
        
        for(ItemStack x: mount.getInventory().getContents()) {
            System.out.println("Horse Item: " + x.getType());    
        }
        
            System.out.println("Horse Item 0: " + mount.getInventory().getItem(0));    
            System.out.println("Horse Item 1: " + mount.getInventory().getItem(1));    
            
        ItemStack horseArmour = mount.getInventory().getItem(1);
        if(horseArmour == null)
            totalEncumbrancePercentage = 0;
        else
            totalEncumbrancePercentage = TownyCombatSettings.getCavalryMaterialEncumbrancePercentageMap().get(horseArmour.getType());
        //2. Encumbrance due to the encumbrance of rider
        Double riderEncumbrance = playerEncumbrancePercentageMap.get(player);
        if(riderEncumbrance != null) {
            totalEncumbrancePercentage += riderEncumbrance * (TownyCombatSettings.getEncumbranceRiderContributionPercentage() / 100);
        }

        //Calculate total speed adjustment
        double recalculatedSpeed = 
            baseWalkSpeed + (baseWalkSpeed * ((genericSpeedAdjustmentPercentage + totalEncumbrancePercentage) / 100));
        //Sanitize
        if(recalculatedSpeed < 0)
            recalculatedSpeed = 0;
        else if(recalculatedSpeed > 1)
            recalculatedSpeed = 1;
        //Apply
        mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed);
    }
    
    public static Map<Player, Double> getPlayerEncumbrancePercentageMap() {
        return playerEncumbrancePercentageMap;
    }

}
