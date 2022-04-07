package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        //Apply adjustments to player walk speed
        adjustPlayerWalkSpeed(player);
        //Apply adjustments to mount walk speed
        adjustMountWalkSpeed(player);
    }

    private static void adjustPlayerWalkSpeed(Player player) {
        //Calculate generic speed adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericInfantrySpeedAdjustmentPercentage();

        //Calculate slow due to encumbrance
        Map<Material, Double> materialEncumbrancePercentageMap = TownyCombatSettings.getMaterialEncumbrancePercentageMap();
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
            VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED + (VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED * ((genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100));
        //Sanitize
        if(recalculatedSpeed < 0)
            recalculatedSpeed = 0;
        else if(recalculatedSpeed > 1)
            recalculatedSpeed = 1;
        //Apply
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed);
    }
    
    private static void adjustMountWalkSpeed(Player player) {
        if(!player.isInsideVehicle() || !(player.getVehicle() instanceof AbstractHorse))
            return;
        AbstractHorse mount = (AbstractHorse)player.getVehicle();
        AnimalTamer owner = mount.getOwner();
        if(owner == null) 
            return;
        
        //Get Base Walk Speed
        Double baseWalkSpeed;
        UUID ownerUUID = mount.getOwner().getUniqueId();
        
        System.out.println("Mount owner " + ownerUUID);
        Resident ownerResident = TownyAPI.getInstance().getResident(ownerUUID);
        if(ownerResident == null) {
            //Owner has left server. Horse is sad and goes slow.
            baseWalkSpeed = 0.1;
        } else {
            baseWalkSpeed = TownyCombatResidentMetaDataController.getTrainedHorseBaseSpeed(ownerResident, mount.getUniqueId());
            if(baseWalkSpeed == null) {
                //Register base walk speed
                System.out.println("Training horse now " + mount.getUniqueId());
                baseWalkSpeed = TownyCombatResidentMetaDataController.registerTrainedHorse(ownerResident, mount);
            }
        }
        System.out.println("Base walk speed " + baseWalkSpeed);

        //Calculate generic adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericCavalrySpeedAdjustmentPercentage();
    
        //Calculate encumbrance
        //1. Encumbrance due to weight/bulk of horses own armour
        double totalEncumbrancePercentage; 
        
        for(ItemStack x: mount.getInventory().getContents()) {
            if(x == null) {
                System.out.println("Horse Inv Item: null");                
            } else {
                System.out.println("Horse Inv Item: " + x.getType());
            }    
        }
        
            System.out.println("Horse Item 0: " + mount.getInventory().getItem(0));    
            System.out.println("Horse Item 1: " + mount.getInventory().getItem(1));    
            
        ItemStack horseArmour = mount.getInventory().getItem(1);
        if(horseArmour == null)
            totalEncumbrancePercentage = 0;
        else
            totalEncumbrancePercentage = TownyCombatSettings.getMaterialEncumbrancePercentageMap().get(horseArmour.getType());
        //2. Encumbrance due to the encumbrance of rider
        Double riderEncumbrance = playerEncumbrancePercentageMap.get(player);
        if(riderEncumbrance != null) {
            totalEncumbrancePercentage += riderEncumbrance * (TownyCombatSettings.getEncumbranceRiderContributionPercentage() / 100);
        }
    
        //Calculate total speed adjustment
        double recalculatedSpeed = 
            baseWalkSpeed + (baseWalkSpeed * ((genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100));
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
