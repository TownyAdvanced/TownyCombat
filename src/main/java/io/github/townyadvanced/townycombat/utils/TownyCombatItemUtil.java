package io.github.townyadvanced.townycombat.utils;

import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownyCombatItemUtil {
    
    public static final int NATIVE_SPEAR_SHARPNESS_LEVEL = 8;

    //After we have identified a weapon as spear or not spear, we list it here
    public static Map<ItemStack, Boolean> spearIdentificationMap = new HashMap<>();
    
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

    public static ItemStack createNativeSpear() {
        ItemStack result = new ItemStack(Material.WOODEN_SWORD);
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
    }
}
