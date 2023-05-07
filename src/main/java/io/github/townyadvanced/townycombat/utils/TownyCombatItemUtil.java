package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.util.TimeMgmt;
import io.github.townyadvanced.townycombat.TownyCombat;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static void grantSuperPotionsToOnlinePlayers() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            Resident resident = TownyAPI.getInstance().getResident(player);
            if(resident == null)
                continue;
            grantSuperPotionsNow(player, resident);
        }
    }

    public static void evaluateSuperPotionGrant(Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player);
        if(resident == null)
            return;
        LocalDate dateOfLastSuperPotionGrant = TownyCombatResidentMetaDataController.getDateOfLastSuperPotionGrant(resident);
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);

        if(dateOfLastSuperPotionGrant == null) {
            /*
             * Player never received super potions before.
             * Grant super potions now.
             */
            grantSuperPotionsNow(player, resident);
        } else if(dateOfLastSuperPotionGrant.isBefore(yesterday)) {
            /*
             * Player didn't receive potions today or yesterday.
             * Grant super potions now.
             */
            grantSuperPotionsNow(player, resident);
        } else if (dateOfLastSuperPotionGrant.equals(yesterday)) {
            long secondsUntilNextTownyNewDayEvent = TimeMgmt.townyTime(true);
            LocalDate dateOfNextTownyNewDayEvent = LocalDateTime.now().plusSeconds(secondsUntilNextTownyNewDayEvent).toLocalDate();
            if(dateOfNextTownyNewDayEvent.equals(tomorrow)) {
                /*
                 * The towny new day event already occurred today. 
                 * Grant super potions now.
                 */
                grantSuperPotionsNow(player, resident);
            } else if (dateOfNextTownyNewDayEvent.equals(today)) {
                /*
                 * The towny new day event has not yet occurred today. 
                 * Do not grant.
                 */
            }
        } else if (dateOfLastSuperPotionGrant.equals(today)) {
            /*
             * Potions were granted already today. 
             * Do not grant.
             */
        }
    }

    public static void grantSuperPotionsNow(Player player, Resident resident) {
        BattlefieldRole battlefieldRole = TownyCombatBattlefieldRoleUtil.getBattlefieldRole(resident);
        switch(battlefieldRole) {
            case LIGHT:
                grantLightRoleSuperPotionsNow(player);
                break;
            case MEDIUM:
                grantMediumRoleSuperPotionsNow(player);
                break;
            case HEAVY:
                grantHeavyRoleSuperPotions(player);
                break;
            default:
                throw new RuntimeException("Unknown battlefield role");
        }
    }

    public static void grantLightRoleSuperPotionsNow(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createTrueInvisibilityPotion(20));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
    }

    public static void grantMediumRoleSuperPotionsNow(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createLingeringHarmPotion(60, 4));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
    }

    public static void grantHeavyRoleSuperPotions(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createAbsorbtionPotion(180, 4));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
    }

    public static void dropItemsAtPlayersFeet(Player player, List<ItemStack> itemsToDrop) {
        Towny.getPlugin().getServer().getScheduler().runTask(Towny.getPlugin(), new Runnable() {
            public void run() {
                for(ItemStack item: itemsToDrop) {
                    player.getWorld().dropItem(player.getLocation(), item);
                }
            }
        });
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

    public static ItemStack createPotion(Material material, PotionEffectType potionEffectType, int durationSeconds, int amplifier, boolean ambient, boolean particles, boolean icon) {
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

}
