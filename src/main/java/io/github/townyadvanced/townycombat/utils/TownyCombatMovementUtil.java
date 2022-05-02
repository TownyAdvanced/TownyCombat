package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    public static final String ATTRIBUTE_MODIFIER_NAME = "TownyCombatAdjustment";
    
    private static Map<Player, Double> playerEncumbrancePercentageMap = new HashMap<>();  //Encumbrance = Walk-speed-slow

    private static Map<Player,Long> jumpDamageNextWarningTimesMap = new HashMap<>();  //When were players last warned about jump damage

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
        //Apply adjustments to player knockback resistance
        adjustPlayerKnockbackResistance(player);
        //Apply adjustments to mount knockback resistance
        adjustMountKnockbackResistance(player);
    }

    public static void removeTownyCombatMovementModifiers(LivingEntity livingEntity) {
        //Remove the TCM modifiers
        for(AttributeModifier attributeModifier: new ArrayList<>(livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attributeModifier);
            }                
        }
        for(AttributeModifier attributeModifier: new ArrayList<>(livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(attributeModifier);
            }
        }
    }

    public static void resetPlayerBaseSpeedToVanilla(Player player) {
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(VANILLA_PLAYER_MOVEMENT_SPEED);        
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
        double scalarAdjustment = (genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100;

        //Remove old modifier
        for(AttributeModifier attributeModifier: new ArrayList<>(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attributeModifier);
            }
        }

        //Add new modifier
        AttributeModifier attributeModifier = new AttributeModifier(ATTRIBUTE_MODIFIER_NAME, scalarAdjustment, AttributeModifier.Operation.ADD_SCALAR);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(attributeModifier);

        //Warn player about jump damage
        if(totalEncumbrancePercentage >= TownyCombatSettings.getJumpDamageThreshold()) {
            Long nextWarningTime = jumpDamageNextWarningTimesMap.get(player);
            if(nextWarningTime == null
                    || System.currentTimeMillis() > nextWarningTime) {
                Messaging.sendErrorMsg(player, Translatable.of("msg_warning_jump_damage", TownyCombatSettings.getJumpDamageThreshold()));
                jumpDamageNextWarningTimesMap.put(player, System.currentTimeMillis() + (TownyCombatSettings.getJumpDamageWarningIntervalMinutes() * 60 * 1000));
            }
        }
    }
 
    private static void adjustMountWalkSpeed(Player player) {
        if(!player.isInsideVehicle() || !(player.getVehicle() instanceof AbstractHorse))
            return;
        AbstractHorse mount = (AbstractHorse)player.getVehicle();

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
        double scalarAdjustment = (genericSpeedAdjustmentPercentage - totalEncumbrancePercentage) / 100;

        //Remove old modifier
        for(AttributeModifier attributeModifier: new ArrayList<>(mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attributeModifier);
            }
        }

        //Add new modifier
        AttributeModifier attributeModifier = new AttributeModifier(ATTRIBUTE_MODIFIER_NAME, scalarAdjustment, AttributeModifier.Operation.ADD_SCALAR);
        mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(attributeModifier);
    }

    public static void adjustPlayerKnockbackResistance(Player player) {
        double scalarAdjustment =TownyCombatSettings.getKnockbackResistanceInfantryPercent() / 100;
        if(scalarAdjustment == 0)
            return;

        //Remove old modifier
        for(AttributeModifier attributeModifier: new ArrayList<>(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(attributeModifier);
            }
        }

        //Add new modifier
        AttributeModifier attributeModifier = new AttributeModifier(ATTRIBUTE_MODIFIER_NAME, scalarAdjustment, AttributeModifier.Operation.ADD_NUMBER);
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addModifier(attributeModifier);
    }

    public static void adjustMountKnockbackResistance(Player player) {
        if(!player.isInsideVehicle() || !(player.getVehicle() instanceof AbstractHorse))
            return;
        AbstractHorse mount = (AbstractHorse)player.getVehicle();
        double scalarAdjustment =TownyCombatSettings.getKnockbackResistanceHorsesPercent() / 100;
        if(scalarAdjustment == 0)
            return;

        //Remove old modifier
        for(AttributeModifier attributeModifier: new ArrayList<>(mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                mount.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(attributeModifier);
            }
        }

        //Add new modifier
        AttributeModifier attributeModifier = new AttributeModifier(ATTRIBUTE_MODIFIER_NAME, scalarAdjustment, AttributeModifier.Operation.ADD_NUMBER);
        mount.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addModifier(attributeModifier);
    }

    public static Map<Player, Double> getPlayerEncumbrancePercentageMap() {
        return playerEncumbrancePercentageMap;
    }

    /**
    * Remove legacy horse-speed-registration data
    * 
    * Do this by:
    * 1. Resetting the speeds of all registered horses back to their original values.
    * 2. Removing the horse speed metadata from the resident files.
    * 
    * This method, along with the resident metadata class,
    * should be removed when everyone has upgraded to 0.2.2 or above
    */
    public static void removeLegacyHorseSpeedRegistrationData() {
        Map<UUID, Double> horseSpeedMap;
        AbstractHorse horse;
        for(Resident resident: TownyAPI.getInstance().getResidents()) {
            //Reset speeds of horses back to their original values
            horseSpeedMap = TownyCombatResidentMetaDataController.getHorseSpeedMap(resident);
            for(Map.Entry<UUID, Double> mapEntry: horseSpeedMap.entrySet()) {
                horse = (AbstractHorse)Bukkit.getEntity(mapEntry.getKey());
                if(horse != null) {
                    horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mapEntry.getValue());
                }                
            }
            //Wipe resident horse metadata
            TownyCombatResidentMetaDataController.removeHorseSpeedMap(resident);
            resident.save();
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
        double newHealth;        
        for(Player player: Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() == GameMode.CREATIVE
                    || player.getGameMode() == GameMode.ADVENTURE
                    || player.isInvulnerable()) {                
                continue;
            }
            velocityY = player.getVelocity().getY();  
            if(velocityY != GRAVITY_VELOCITY 
                    && velocityY != LADDER_VELOCITY 
                    && velocityY > 0
                    && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                /*
                 * Player is jumping or ascending an incline
                 *
                 * If they have are over the encumbrance threshold, apply damage
                 */
                playerEncumbrancePercentage = playerEncumbrancePercentageMap.get(player);
                if(playerEncumbrancePercentage != null && playerEncumbrancePercentage > JUMP_DAMAGE_THRESHOLD) {
                    newHealth = player.getHealth() - (playerEncumbrancePercentage * JUMP_DAMAGE_PER_ENCUMBRANCE_PERCENT);
                    newHealth = Math.max(0, Math.min(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), newHealth)); //apply min and max
                    if(newHealth > 0) {
                        player.setHealth(newHealth);
                    } else {
                        double finalNewHealth = newHealth;
                        TownyCombat.getPlugin().getServer().getScheduler().runTask(TownyCombat.getPlugin(), ()->  player.setHealth(finalNewHealth));
                    }
               }
            }
        }
    }
}
