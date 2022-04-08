package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatMovementUtil {
    public static final double VANILLA_PLAYER_MOVEMENT_SPEED = 0.1;
    public static final double VANILLA_HORSE_MAX_MOVEMENT_SPEED = 0.3375;

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
            VANILLA_PLAYER_MOVEMENT_SPEED + (VANILLA_PLAYER_MOVEMENT_SPEED * ((genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100));
        //Sanitize
        recalculatedSpeed = Math.max(recalculatedSpeed, 0.05);
        recalculatedSpeed = Math.min(recalculatedSpeed, 1);
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
        //2. Add encumbrance of rider
        Double riderEncumbrance = playerEncumbrancePercentageMap.get(player);
        if(riderEncumbrance != null) {
            totalEncumbrancePercentage += riderEncumbrance;
        }
        //3. Reduce encumbrance due to horse strength
        totalEncumbrancePercentage =
            totalEncumbrancePercentage - (totalEncumbrancePercentage * (TownyCombatSettings.getCavalryEncumbranceReductionPercentage() / 100));
    
        //Calculate total speed adjustment
        double recalculatedSpeed = 
            baseWalkSpeed + (baseWalkSpeed * ((genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100));
        //Sanitize
        recalculatedSpeed = Math.max(recalculatedSpeed, 0.05);
        recalculatedSpeed = Math.min(recalculatedSpeed, 1);
        //Apply
        mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed);
    }
    
    public static Map<Player, Double> getPlayerEncumbrancePercentageMap() {
        return playerEncumbrancePercentageMap;
    }

    /**
    * Cleanup resident horse registrations
    * Do this by removing dead/deleted horses from the resident files
    */
    public static void cleanupResidentHorseRegistrations() {
        Map<UUID, Double> horseSpeedMap;
        List<UUID> entriesToDelete = new ArrayList<>();
        for(Resident resident: TownyAPI.getInstance().getResidents()) {
            horseSpeedMap = TownyCombatResidentMetaDataController.getHorseSpeedMap(resident);
            entriesToDelete.clear();
            for(Map.Entry<UUID, Double> mapEntry: horseSpeedMap.entrySet()) {
                Entity horse = Bukkit.getEntity(mapEntry.getKey());
                if(horse == null || horse.isDead()) {
                    entriesToDelete.add(mapEntry.getKey());
                }
            }
            if(entriesToDelete.size() > 0) {
                for(UUID uuid: entriesToDelete) {
                    horseSpeedMap.remove(uuid);
                }
                resident.save();
            }
        }
        TownyCombat.info("Resident Horse Registration Cleanup Complete");
    }
}
