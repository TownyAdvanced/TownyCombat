package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
    private static Map<Player, Double> infantryArmourSpeedAdjustmentPercentages = new HashMap<>();

    public static void adjustPlayerSpeed() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            adjustPlayerSpeed(player);
        }
    }

    public static void adjustPlayerSpeed(Player player) {
        //Calculate generic speed adjustment
        double genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericInfantrySpeedAdjustmentPercentage();

        //Calculate armour speed adjustment
        Map<Material, Double> materialSlowPercentageMap = TownyCombatSettings.getMaterialSlowPercentageMap();
        double armourSpeedAdjustmentPercentage = 0;
        Double slowPercentage;
        for(ItemStack itemStack: player.getInventory().getContents()) {
            if(itemStack != null) {
                slowPercentage = materialSlowPercentageMap.get(itemStack.getType());
                if(slowPercentage != null) {
                    armourSpeedAdjustmentPercentage -= slowPercentage;
                }
            }
        }      
        infantryArmourSpeedAdjustmentPercentages.put(player, armourSpeedAdjustmentPercentage);        

        //Apply all speed adjustments
        double recalculatedSpeed = 
            VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED + (VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED * ((genericSpeedAdjustmentPercentage + armourSpeedAdjustmentPercentage) / 100));

        //Sanitize
        if(recalculatedSpeed < 0)
            recalculatedSpeed = 0;
        else if(recalculatedSpeed > 1)
            recalculatedSpeed = 1;            
        //Apply
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed);
        
        System.out.println("New Speed: " + recalculatedSpeed); 
    }
    
    public static Map<Player, Double> getInfantryArmourSpeedAdjustmentPercentages() {
        return infantryArmourSpeedAdjustmentPercentages;
    }

}
