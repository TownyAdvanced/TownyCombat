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
    private static Map<Player, Double> playerArmourSlowPercentages = new HashMap<>();

    public static void adjustPlayerSpeed() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            adjustPlayerSpeed(player);
        }
    }

    private static void adjustPlayerSpeed(Player player) {
        //Calculate generic infantry adjustment       
        int genericSpeedAdjustmentPercentage = TownyCombatSettings.getGenericInfantrySpeedAdjustmentPercentage();

        //Calculate armour slowing
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
        playerArmourSlowPercentages.put(player, armourSpeedAdjustmentPercentage);
        
        //Apply all speed adjustments
        double recalculatedSpeed = 
            VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED + (VANILLA_PLAYER_GENERIC_MOVEMENT_SPEED * ((genericSpeedAdjustmentPercentage + armourSpeedAdjustmentPercentage) / 100));
        recalculatedSpeed = recalculatedSpeed < 0 ? 0 : recalculatedSpeed;
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(recalculatedSpeed); 
    }
    
    public static Map<Player, Double> getPlayerArmourSlowPercentages() {
        return playerArmourSlowPercentages;
    }

}
