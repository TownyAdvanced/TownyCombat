package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownyCombatItemUtil {

    public static final Material NATIVE_SPEAR_PLACEHOLDER_MATERIAL = Material.WOODEN_SWORD;
    public static final Material[] NATIVE_SPEAR_MATERIALS = new Material[]{null, null, Material.IRON_INGOT, null, Material.STICK, null, Material.STICK, null, null}; 			
    public static final int NATIVE_SPEAR_SHARPNESS_LEVEL = 8;

    //After we have identified a weapon as spear or not spear, we list it here
    public static Map<ItemStack, Boolean> spearIdentificationMap = new HashMap<>();

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
}
