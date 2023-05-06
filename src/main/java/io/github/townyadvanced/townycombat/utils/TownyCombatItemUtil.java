package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownyCombatItemUtil {

    public static final Material NATIVE_SPEAR_PLACEHOLDER_MATERIAL = Material.WOODEN_SWORD;
    public static final Material[] NATIVE_SPEAR_MATERIALS = new Material[]{null, null, Material.IRON_INGOT, null, Material.STICK, null, Material.STICK, null, null}; 			
    public static final int NATIVE_SPEAR_SHARPNESS_LEVEL = 8;

    public static final Material NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL = Material.WOODEN_AXE;
    public static final Material[] NATIVE_WARHAMMER_MATERIALS = new Material[]{null, null, Material.STONE, null, Material.STICK, null, Material.STICK, null, null}; 			
    public static final int NATIVE_WARHAMMER_SHARPNESS_LEVEL = 10;

    //After we have identified a weapon as spear or not spear, we list it here
    public static Map<ItemStack, Boolean> spearIdentificationMap = new HashMap<>();

    //After we have identified a weapon as warhammer or not warhammer, we list it here
    public static Map<ItemStack, Boolean> warhammerIdentificationMap = new HashMap<>();

    public static LocalDate dateOfLastSuperPotionGrant = null;
    /**
     * Check if the given item is a vanilla placeholder version of a custom item.
     * 
     * @param item the item
     * @return true if placeholder item
     */
    public static boolean isVanillaPlaceholderItem(ItemStack item) {
        if(TownyCombatSettings.isNewItemsSpearEnabled()
                && TownyCombatSettings.isNewItemsSpearNativeWeaponEnabled()
                && item.getType() == NATIVE_SPEAR_PLACEHOLDER_MATERIAL
                && !isSpear(item)) {
            return true;  //Vanilla wooden sword
        } else if (TownyCombatSettings.isNewItemsWarhammerEnabled()
                && TownyCombatSettings.isNewItemsWarhammerNativeWeaponEnabled()
                && item.getType() == NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL
                && !isWarhammer(item)) {
            return true;  //Vanilla stone axe
        } else {
            return false;
        }
    }

    /**
     * Check if the given material is s placeholder for special, non-vanilla items
     * 
     * @param material the material
     * @return true if forbidden material
     */
    public static boolean isPlaceholderMaterial(Material material) {
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
			itemMeta.setDisplayName(TownyCombatSettings.getNewItemsSpearNativeWeaponName());
			//Add enchants
			itemMeta.addEnchant(Enchantment.DAMAGE_ALL, NATIVE_SPEAR_SHARPNESS_LEVEL, true);
			//Add lore
			List<String> lore = new ArrayList<>();
			lore.add(TownyCombatSettings.getNewItemsSpearLore());
			itemMeta.setLore(lore);
			result.setItemMeta(itemMeta);
			return result;
        
        } else if(TownyCombatSettings.isNewItemsWarhammerEnabled()
                && TownyCombatSettings.isNewItemsWarhammerNativeWeaponEnabled()
                && doesMatrixMatch(event.getInventory().getMatrix(), NATIVE_WARHAMMER_MATERIALS)) {
			ItemStack result = new ItemStack(NATIVE_WARHAMMER_PLACEHOLDER_MATERIAL);
			ItemMeta itemMeta = result.getItemMeta();
			itemMeta.setDisplayName(TownyCombatSettings.getNewItemsWarhammerNativeWeaponName());
			//Add enchants
			itemMeta.addEnchant(Enchantment.DAMAGE_ALL, NATIVE_WARHAMMER_SHARPNESS_LEVEL, true);
			itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			//Add lore
			List<String> lore = new ArrayList<>();
			lore.add(TownyCombatSettings.getNewItemsWarhammerLore());
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
     * Determine is a given item is a spear
     * @param item the item
     *
     * @return true if the item is a spear
     */
    public static boolean isSpear(ItemStack item) {
        Boolean result = spearIdentificationMap.get(item);
        if(result == null) {
            result = false;
            if(item.getItemMeta() != null) {
                final int CONFIGURED_CUSTOM_MODEL_DATA_ID = TownyCombatSettings.getNewItemsSpearCustomModelDataID();
                if(CONFIGURED_CUSTOM_MODEL_DATA_ID != -1) { 
                    if(item.getItemMeta().hasCustomModelData()
                            && item.getItemMeta().getCustomModelData() == CONFIGURED_CUSTOM_MODEL_DATA_ID) {
                        result = true;     
                    }
                
                } else if (item.getItemMeta().getLore() != null) {
                    for(String loreLine: item.getItemMeta().getLore()) {
                        if(loreLine.equals(TownyCombatSettings.getNewItemsSpearLore())) {
                            result = true;
                            break;
                        }
                    }
                }
            }
            spearIdentificationMap.put(item, result);
        }
        return result;
    }

    /**
     * Determine is a given item is a warhammer
     * @param item the item
     *
     * @return true if the item is a warhammer
     */
    public static boolean isWarhammer(ItemStack item) {
        Boolean result = warhammerIdentificationMap.get(item);
        if(result == null) {
            result = false;
            if(item.getItemMeta() != null) {
                final int CONFIGURED_CUSTOM_MODEL_DATA_ID = TownyCombatSettings.getNewItemsWarhammerCustomModelDataID();
                if(CONFIGURED_CUSTOM_MODEL_DATA_ID != -1) { 
                    if(item.getItemMeta().hasCustomModelData()
                            && item.getItemMeta().getCustomModelData() == CONFIGURED_CUSTOM_MODEL_DATA_ID) {
                        result = true;     
                    }
                
                } else if (item.getItemMeta().getLore() != null) {
                    for(String loreLine: item.getItemMeta().getLore()) {
                        if(loreLine.equals(TownyCombatSettings.getNewItemsWarhammerLore())) {
                            result = true;
                            break;
                        }
                    }
                }
            }
            warhammerIdentificationMap.put(item, result);
        }
        return result;
    }

    public static ItemStack createTrueInvisibilityPotion(int durationSeconds) {
        return createPotion(Material.POTION, PotionEffectType.INVISIBILITY, durationSeconds, 0, false, false, true);
    }

    public static ItemStack createLingeringHarmPotion(int durationSeconds, int amplifier) {
        return createPotion(Material.LINGERING_POTION, PotionEffectType.HARM, durationSeconds, amplifier, true, true, true);
    }

    public static ItemStack createAbsorbtionPotion(int durationSeconds, int amplifier) {
        return createPotion(Material.POTION, PotionEffectType.ABSORPTION, durationSeconds, amplifier, true, true, true);
    }

    private static ItemStack createPotion(Material material, PotionEffectType potionEffectType, int durationSeconds, int amplifier, boolean ambient, boolean particles, boolean icon) {
        //Create the potion itemstack
        ItemStack newPotion = new ItemStack(material);
        newPotion.setAmount(1);
        //Add the required effect
        int durationTicks = durationSeconds * 20;
        PotionEffect newPotionEffect = new PotionEffect(potionEffectType, durationTicks, amplifier, ambient, particles, icon);
        PotionMeta newPotionMeta = (PotionMeta) newPotion.getItemMeta();
        if(newPotionMeta == null)
            return null;
        newPotionMeta.addCustomEffect(newPotionEffect, true);
        newPotion.setItemMeta(newPotionMeta);
        //Return the potion
        return newPotion;
    }

    /**
     * Grant super potions at the scheduled hour of day
     *  
     *  NOTE: If a server misses this, they can use
     *   /tcma grantsuperpotions
     */
    public static void grantSuperPotionsAtStartOfDay() {
        //Grant potions at a time between the scheduled hour, and 1 minute after.
        LocalDateTime now = LocalDateTime.now();
        int hourNow = now.getHour();
        int minuteNow = now.getMinute();
        if(hourNow == TownyCombatSettings.getSuperPotionsScheduledGrantHour() && minuteNow == 0) {
            if(dateOfLastSuperPotionGrant == null) {
                //No record of grant. Grant now
                grantSuperPotionsNow();
            } else if (dateOfLastSuperPotionGrant.isBefore(LocalDate.now())) {
                //Last grant was before today. Grant now
                grantSuperPotionsNow();
            } else {
                //Last grant was today. Do not re-grant
            }
        }
    }
    
    private static void grantSuperPotionsNow() {
        //Grant 
    }
}
