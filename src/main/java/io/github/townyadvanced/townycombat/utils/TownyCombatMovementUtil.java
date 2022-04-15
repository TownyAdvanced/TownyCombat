package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
    private static final double GRAVITY_VELOCITY = -0.0784000015258789;  //A players has this when stationary or travelling horizontally
    private static final double LADDER_VELOCITY = -0.22540001022815717; //A player has this when going up OR down a ladder
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
        
        Resident ownerResident = TownyAPI.getInstance().getResident(ownerUUID);
        if(ownerResident == null) {
            //Owner has left server. Horse is sad and goes slow.
            baseWalkSpeed = 0.1;
        } else {
            baseWalkSpeed = TownyCombatResidentMetaDataController.getTrainedHorseBaseSpeed(ownerResident, mount.getUniqueId());
            if(baseWalkSpeed == null) {
                //Register base walk speed
                baseWalkSpeed = TownyCombatResidentMetaDataController.registerTrainedHorse(ownerResident, mount);
            }
        }

        //Calculate generic adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericCavalrySpeedAdjustmentPercentage();
    
        //Calculate encumbrance
        //1. Encumbrance due to weight/bulk of horses own armour
        double totalEncumbrancePercentage; 
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
                TownyCombatResidentMetaDataController.setHorseSpeedMap(resident, horseSpeedMap);
                resident.save();
            }
        }
        TownyCombat.info("Resident Horse Registration Cleanup Complete");
    }

    /**
     * This method exists to compensate for a bug in vanilla Minecraft,
     * in which players can bypass slowing-effects by jumping forward.
     *
     * This method compensates by applying a little damage,
     * if a heavily encumbered player jumps or ascends an incline.
     */
    public static void punishEncumberedJumpingPlayers() {        
        Map<Player, Double> playerEncumbrancePercentageMap = TownyCombatMovementUtil.getPlayerEncumbrancePercentageMap();
        final double JUMP_DAMAGE_THRESHOLD = TownyCombatSettings.getJumpDamageThreshold();
        final double JUMP_DAMAGE_PER_ENCUMBRANCE_PERCENT = TownyCombatSettings.getJumpDamageDamagePerEncumbrancePercent();
        Double playerEncumbrancePercentage;
        double velocityY;        
        double damage;
        double newHealth;        
        for(Player player: Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() == GameMode.CREATIVE
                    || player.getGameMode() == GameMode.ADVENTURE
                    || player.isInvulnerable()) {                
                continue;
            }
            velocityY = player.getVelocity().getY();  
            if(velocityY != GRAVITY_VELOCITY && velocityY != LADDER_VELOCITY && velocityY > 0) {
                /*
                 * Player is jumping or ascending an incline
                 *
                 * If they have are over the encumbrance threshold, apply damage
                 */
                playerEncumbrancePercentage = playerEncumbrancePercentageMap.get(player);
                if(playerEncumbrancePercentage != null && playerEncumbrancePercentage > JUMP_DAMAGE_THRESHOLD) {
                damage = playerEncumbrancePercentage * JUMP_DAMAGE_PER_ENCUMBRANCE_PERCENT;
                newHealth = player.getHealth() - damage;
                final double finalNewHealth = newHealth < 0 ? 0 : newHealth;   
                TownyCombat.getPlugin().getServer().getScheduler().runTask(TownyCombat.getPlugin(), ()->  player.setHealth(finalNewHealth));
               }
            }
        }
    }
}
