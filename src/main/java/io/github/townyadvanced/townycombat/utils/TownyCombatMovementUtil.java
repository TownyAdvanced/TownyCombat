package io.github.townyadvanced.townycombat.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatMovementUtil {
    public static final double VANILLA_PLAYER_MOVEMENT_SPEED = 0.1;
    public static final String ATTRIBUTE_MODIFIER_NAME = "TownyCombatAdjustment";

    public static void removeTownyCombatMovementModifiers(LivingEntity livingEntity) {
        //Remove the TCM modifiers
        for (AttributeModifier attributeModifier : new ArrayList<>(livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers())) {
            if (attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(attributeModifier);
            }
        }
    }

    public static void removeTownyCombatKnockbackModifiers(LivingEntity livingEntity) {
        for(AttributeModifier attributeModifier: new ArrayList<>(livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getModifiers())) {
            if(attributeModifier.getName().equals(ATTRIBUTE_MODIFIER_NAME)) {
                livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(attributeModifier);
            }
        }
    }

    public static void resetPlayerBaseSpeedToVanilla(Player player) {
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(VANILLA_PLAYER_MOVEMENT_SPEED);        
    }
    
}
