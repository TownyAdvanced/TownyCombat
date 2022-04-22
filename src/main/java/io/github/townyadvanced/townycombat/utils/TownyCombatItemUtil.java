package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class TownyCombatItemUtil {

    public static final Material NATIVE_SPEAR_PLACEHOLDER_MATERIAL = Material.WOODEN_SWORD;
    public static final Material[] NATIVE_SPEAR_MATERIALS = new Material[]{null, null, Material.IRON_INGOT, null, Material.STICK, null, Material.STICK, null, null}; 			
    public static final int NATIVE_SPEAR_SHARPNESS_LEVEL = 8;

    public static final Material NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL = Material.WOODEN_AXE;
    public static final Material[] NATIVE_WARHAMMER_MATERIALS = new Material[]{null, null, Material.STONE, null, Material.STICK, null, Material.STICK, null, null}; 			
    public static final int WARHAMMER_SHARPNESS_LEVEL = 10;

    /**
     * Some vanilla items are forbidden
     * because they would cause confusion with custom TownyCombat items
     * 
     * @param item the item
     * @return true if forbidden item
     */
    public static boolean isForbiddenItem(ItemStack item) {
        if(TownyCombatSettings.isNewItemsSpearEnabled()
                && TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled()
                && item.getType() == NATIVE_SPEAR_PLACEHOLDER_MATERIAL
                && (!item.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)
                    || item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) != NATIVE_SPEAR_SHARPNESS_LEVEL)) {
            return true;  //Vanilla wooden sword
        } else if (TownyCombatSettings.isNewItemsWarhammerEnabled()
                && TownyCombatSettings.isNewItemsWarhammerNativeWeaponEnabled()
                && item.getType() == NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL
                && (!item.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)
                    || item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) != WARHAMMER_SHARPNESS_LEVEL)) {
            return true;  //Vanilla stone axe
        } else {
            return false;
        }
    }

    /**
     * Calculate if the given material is reserved for special, non-vanilla items
     * 
     * @param material the material
     * @return true if forbidden material
     */
    public static boolean isReservedMaterial(Material material) {
        if(TownyCombatSettings.isNewItemsSpearEnabled() 
                && TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled()
                && material == NATIVE_SPEAR_PLACEHOLDER_MATERIAL) {
            return true;

        } else if (TownyCombatSettings.isNewItemsWarhammerEnabled()
                && TownyCombatSettings.isNewItemsWarhammerNativeWeaponEnabled()
                && material == NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Calculate the result of the given crafting
     *
     * @param event the event
     * @return the result
     */
    public static ItemStack calculateCraftingResult(PrepareItemCraftEvent event) {
        if(TownyCombatSettings.isNewItemsSpearEnabled()
                && TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled()
                && doesMatrixMatch(event.getInventory().getMatrix(), NATIVE_SPEAR_MATERIALS)) {
			ItemStack result = new ItemStack(NATIVE_SPEAR_PLACEHOLDER_MATERIAL);
			ItemMeta itemMeta = result.getItemMeta();
			itemMeta.setDisplayName("Spear");
			itemMeta.addEnchant(Enchantment.DAMAGE_ALL, NATIVE_SPEAR_SHARPNESS_LEVEL, true);
			List<String> lore = new ArrayList<>();
            lore.add(
                TownyCombatSettings.getCachedSpearLoreCode()
                + ChatColor.translateAlternateColorCodes('&',"&a")  
                + "+" + TownyCombatSettings.getNewItemsSpearBonusDamageVsCavalry()
                + "% Damage v.s. Cavalry");
			itemMeta.setLore(lore);
			result.setItemMeta(itemMeta);
			return result;
        
        } else if(TownyCombatSettings.isNewItemsWarhammerEnabled()
                && TownyCombatSettings.isNewItemsWarhammerNativeWeaponEnabled()
                && doesMatrixMatch(event.getInventory().getMatrix(), NATIVE_WARHAMMER_MATERIALS)) {
			ItemStack result = new ItemStack(NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL);
			ItemMeta itemMeta = result.getItemMeta();
			itemMeta.setDisplayName("Warhammer");
			itemMeta.addEnchant(Enchantment.DAMAGE_ALL, WARHAMMER_SHARPNESS_LEVEL, true);
			itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			List<String> lore = new ArrayList<>();
            lore.add(
                TownyCombatSettings.getCachedWarhammerLoreCode()
                + ChatColor.translateAlternateColorCodes('&',"&a")  
                + TownyCombatSettings.getNewItemsWarhammerShieldBreakChancePercent()
                + "% Chance to Break Shield");
			itemMeta.setLore(lore);
			result.setItemMeta(itemMeta);
			return result;

        } else {
            return event.getInventory().getResult();
        }
    }

    /**
     * Check if the matrix matches
     * 
     * NOTE!: Because of a duplication bug in MC/Bukkit, 
     *  the recipe cannot be facilitated if the player has more than 1 item in each slot.
     * 
     * @param currentCraftingMatrix the matrix player has setup
     * @param requiredMaterialMatrix the required matrix
     * @return true if the matrix matches
     */
    private static boolean doesMatrixMatch(ItemStack[] currentCraftingMatrix, Material[] requiredMaterialMatrix) {
        if(currentCraftingMatrix.length != requiredMaterialMatrix.length)
            return false;
        for(int i = 0; i < 9; i++) {
            if(requiredMaterialMatrix[i] == null) { 
                if(currentCraftingMatrix[i] != null)
                    return false;
            } else {
                if(currentCraftingMatrix[i] == null)
                    return false;
                else if (currentCraftingMatrix[i].getAmount() > 1
                        || currentCraftingMatrix[i].getType() != requiredMaterialMatrix[i])
                    return false;
            }
        }
       return true;
    }

    /**
     * Roll the dice. If the number comes us, break the item
     *
     * @param itemHolder the holder of the item
     * @param offHand Is the item in offhand?, otherwise it is main hand.
     * @param percentageChange the chance
     */
    public static void rollBreakItemInHand(Player itemHolder, boolean offHand, double percentageChange) {
        double normalizedChance = percentageChange / 100;
        double num = Math.random();
        if(num < normalizedChance) {
            if(offHand) {
                itemHolder.getInventory().setItemInOffHand(null);
            } else {
                itemHolder.getInventory().setItemInMainHand(null);
            }
            if(itemHolder.getLocation().getWorld() != null)
                itemHolder.getLocation().getWorld().playSound(itemHolder.getLocation(), Sound.ITEM_SHIELD_BREAK ,1f, 0.5f);
        }        
    }

    /**
     * Heal player to over threshold, using any splash heal potions in the players inventory
     * @param player player
     */
    public static void autopotToThreshold(Player player) {
        if(player.getVehicle() != null  && player.getVehicle() instanceof AbstractHorse) {
            autopotToThreshold(player, player, (LivingEntity)player.getVehicle());
        } else {
            autopotToThreshold(player, player, null);
        }
    }

    /**
     * Heal horse to over threshold, using any splash heal potions in the riders inventory
     * @param player player
     * @param horse horse
     */
    public static void autopotToThreshold(Player player, AbstractHorse horse) {
        autopotToThreshold(player, horse, player);
    }

    /**
     * Heal primary recipient, using any splash heal potions in the players inventory
     * A secondary recipient, if not null, also receives any of the healing.
     *
     * @param supplier the one with the potions
     * @param primaryRecipient primary recipient of healing
     * @param secondaryRecipient secondary recipient of healing
     */
    public static void autopotToThreshold(Player supplier, LivingEntity primaryRecipient, LivingEntity secondaryRecipient) {
        for(ItemStack itemStack: supplier.getInventory().getContents()) {
            if(itemStack != null
                    && itemStack.getType() == Material.SPLASH_POTION
                    && itemStack.getItemMeta() != null) {

                PotionData potionData = ((PotionMeta) itemStack.getItemMeta()).getBasePotionData();
                if(potionData.getType() != PotionType.INSTANT_HEAL)
                    continue;
                //Use potion
                if(potionData.isUpgraded()) {
                    primaryRecipient.setHealth(primaryRecipient.getHealth() + 10);
                    if(secondaryRecipient != null) {
                        secondaryRecipient.setHealth(secondaryRecipient.getHealth() + 10);
                    }
                } else {
                    primaryRecipient.setHealth(primaryRecipient.getHealth() + 5);
                    if(secondaryRecipient != null) {
                        secondaryRecipient.setHealth(secondaryRecipient.getHealth() + 5);
                    }
                }
                //Remove potion
                itemStack.setAmount(0);
                //Return if the primary recipient is healed enough
                if(primaryRecipient.getHealth() > TownyCombatSettings.getAutoPottingThreshold()) {
                    return;
                }
            }
        }
    }

    /**
     * Determine is a given item is a spear
     * @param item the item
     *
     * @return true if the item is a spear
     */
    public static boolean isSpear(ItemStack item) {
        if(item.getItemMeta() != null
                && item.getItemMeta().getLore() != null
                && item.getItemMeta().getLore().size() > 0
                && item.getItemMeta().getLore().get(0).startsWith(TownyCombatSettings.getCachedSpearLoreCode())) {
            return true;        
        } else {
            return false;
        }
    }

    /**
     * Determine is a given item is a warhammer
     * @param item the item
     *
     * @return true if the item is a warhammer
     */
    public static boolean isWarhammer(ItemStack item) {
        if(item.getItemMeta() != null
                && item.getItemMeta().getLore() != null
                && item.getItemMeta().getLore().size() > 0
                && item.getItemMeta().getLore().get(0).startsWith(TownyCombatSettings.getCachedWarhammerLoreCode())) {
            return true;        
        } else {
            return false;
        }
    }
}
