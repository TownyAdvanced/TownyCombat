package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Translatable;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;
import io.github.townyadvanced.townycombat.settings.TownyCombatSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TownyCombatItemUtil {
    
    public static final int NATIVE_SPEAR_SHARPNESS_LEVEL = 8;
    public static final NamespacedKey ITEM_NATIVE_SPEAR_KEY = NamespacedKey.fromString("townycombat.nativespear");
    public static final PersistentDataType<String, String> ITEM_NATIVE_SPEAR_KEY_TYPE = PersistentDataType.STRING;
    public static final NamespacedKey ITEM_EXCLUSIVE_OWNER_KEY = NamespacedKey.fromString("townycombat.exclusiveowner");
    public static final PersistentDataType<String, String> ITEM_EXCLUSIVE_OWNER_KEY_TYPE = PersistentDataType.STRING;
    public static final NamespacedKey ITEM_EXPIRY_DATE_KEY = NamespacedKey.fromString("townycombat.expirydate");
    public static final PersistentDataType<String, String> ITEM_EXPIRY_DATE_KEY_TYPE = PersistentDataType.STRING;

    /**
     * Determine is a given item is a spear
     * 2 methods of identification are used:
     * 1. We look for a wooden sword with a ITEM_NATIVE_SPEAR data identification key
     * 2. If that is not found, we look for the signature custom model data ID
     * 
     * @param item the item
     *
     * @return true if the item is a spear
     */
    public static boolean isSpear(ItemStack item) {
        if (item.getType() == Material.WOODEN_SWORD && doesItemHaveDataKey(item, ITEM_NATIVE_SPEAR_KEY, ITEM_NATIVE_SPEAR_KEY_TYPE)) {
            return true;
        } else {
            if (item.getItemMeta() != null) {
                final int signatureCustomModelDataID = TownyCombatSettings.getNewItemsSpearSignatureCustomModelDataID();
                return signatureCustomModelDataID != -1
                        && item.getItemMeta().hasCustomModelData()
                        && item.getItemMeta().getCustomModelData() == signatureCustomModelDataID;
            }
        }
        return false;
    }
        
    public static boolean doesItemHaveDataKey(ItemStack item, NamespacedKey dataKey, PersistentDataType<String, String> dataKeyType) {
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null)
            return false;
        PersistentDataContainer itemStackDataContainer = itemMeta.getPersistentDataContainer();
        return itemStackDataContainer.has(dataKey, dataKeyType);
    }

    public static @Nullable String getDataKeyValue(ItemStack item, NamespacedKey dataKey, PersistentDataType<String, String> dataKeyType) {
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null)
            return null;
        PersistentDataContainer itemStackDataContainer = itemMeta.getPersistentDataContainer();
        return itemStackDataContainer.get(dataKey, dataKeyType);
    }

    public static void setDataKeyValue(ItemMeta itemMeta, NamespacedKey dataKey, PersistentDataType<String, String> dataKeyType, String value) {
        PersistentDataContainer itemStackDataContainer = itemMeta.getPersistentDataContainer();
        itemStackDataContainer.set(dataKey, dataKeyType, value);
    }

    public static ItemStack createNativeSpear() {
        ItemStack result = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.setDisplayName(Translatable.of("spear_name").translate(Locale.ROOT));
        //Add enchants
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, NATIVE_SPEAR_SHARPNESS_LEVEL, true);
        //Add lore
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Translatable.of("spear_lore_line_1", TownyCombatSettings.getNewItemsSpearBonusDamageVsCavalry()).translate(Locale.ROOT));
        itemMeta.setLore(lore);
        //Add data key
        setDataKeyValue(itemMeta, ITEM_NATIVE_SPEAR_KEY, ITEM_NATIVE_SPEAR_KEY_TYPE, "S");
        //Set item meta
        result.setItemMeta(itemMeta);
        return result;
    }

    public static void grantSuperPotionsToOnlinePlayers() {
        for(Player player: Bukkit.getOnlinePlayers()) {
            Resident resident = TownyAPI.getInstance().getResident(player);
            if(resident == null)
                continue;
            removeExpiredSuperPotionsFromInventory(player);
            grantSuperPotionsNow(player, resident);
        }
    }

    public static void removeExpiredSuperPotionsFromInventory(Player player) {
        int numRemovedItems = 0;
        for(ItemStack itemStack: player.getInventory().getContents()) {
            if(isSuperPotion(itemStack) && isSuperPotionExpired(itemStack)) {
                itemStack.setAmount(0);
                numRemovedItems++;
            }
        }
        if(numRemovedItems > 0) {
            Messaging.sendMsg(player, Translatable.of("super_potions_removed_from_inventory", numRemovedItems));
        }
    }

    public static void evaluateSuperPotionGrant(Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player);
        if(resident == null)
            return;
        LocalDate dateOfLastSuperPotionGrant = TownyCombatResidentMetaDataController.getLastSuperPotionCollectionDate(resident);
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

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
            if(TownyCombatTimeUtil.hasTownyNewDayEventOccurredToday()) {
                /*
                 * The towny new day event already occurred today. 
                 * Grant super potions now.
                 */
                grantSuperPotionsNow(player, resident);
            } else {
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
        TownyCombatResidentMetaDataController.setLastSuperPotionCollectionDate(resident, LocalDate.now());
        resident.save();
    }

    public static void grantLightRoleSuperPotionsNow(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createTrueInvisibilityPotion(player,20));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
        Messaging.sendMsg(player, Translatable.of("super_potions_granted", potionsToDrop.size()));
    }

    public static void grantMediumRoleSuperPotionsNow(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createLingeringHarmPotion(player,120, 4));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
        Messaging.sendMsg(player, Translatable.of("super_potions_granted", potionsToDrop.size()));
    }

    public static void grantHeavyRoleSuperPotions(Player player) {
        //Create potions
        List<ItemStack> potionsToDrop = new ArrayList<>();
        for(int i = 0; i < TownyCombatSettings.getBattlefieldRolesSuperPotionsDailyGenerationRate(); i++) {
            potionsToDrop.add(createAbsorbtionPotion(player,180, 4));
        }
        //Drop potions
        dropItemsAtPlayersFeet(player, potionsToDrop);
        Messaging.sendMsg(player, Translatable.of("super_potions_granted", potionsToDrop.size()));
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

    public static ItemStack createTrueInvisibilityPotion(Player owner, int durationSeconds) {
        return createPotion(owner, Material.POTION, PotionEffectType.INVISIBILITY, durationSeconds, 0, false, false, true, "super_potion_name_true_invisibility");
    }

    public static ItemStack createLingeringHarmPotion(Player owner, int durationSeconds, int amplifier) {
        return createPotion(owner, Material.LINGERING_POTION, PotionEffectType.HARM, durationSeconds, amplifier, true, true, true, "super_potion_name_lingering_harm");
    }

    public static ItemStack createAbsorbtionPotion(Player owner, int durationSeconds, int amplifier) {
        return createPotion(owner, Material.POTION, PotionEffectType.ABSORPTION, durationSeconds, amplifier, true, true, true, "super_potion_name_absorbtion");
    }

    public static ItemStack createPotion(Player owner, Material material, PotionEffectType potionEffectType, int durationSeconds, int amplifier, boolean ambient, boolean particles, boolean icon, String nameTranslationKey) {
        //Create the potion itemstack
        ItemStack potionItemStack = new ItemStack(material);
        potionItemStack.setAmount(1);
        //Get Potion Meta
        PotionMeta potionMeta = (PotionMeta) potionItemStack.getItemMeta();
        if(potionMeta == null)
            return null;
        potionMeta.setDisplayName(Translatable.of(nameTranslationKey).toString());
        //Add PotionEffect
        int durationTicks = durationSeconds * 20;
        PotionEffect potionEffect = new PotionEffect(potionEffectType, durationTicks, amplifier, ambient, particles, icon);
        potionMeta.addCustomEffect(potionEffect, true);
        //Add lore
        List<String> lore = new ArrayList<>();
        lore.add(""); //Blank line to start with
        lore.add(Translatable.of("super_potion_lore_line_1", owner.getName()).translate(Locale.ROOT));
        potionMeta.setLore(lore);
        //Add owner information
        setDataKeyValue(potionMeta, ITEM_EXCLUSIVE_OWNER_KEY, ITEM_EXCLUSIVE_OWNER_KEY_TYPE , owner.getName());
        //Add expiry information  
        String tomorrowString = LocalDate.now().plusDays(1).toString();
        setDataKeyValue(potionMeta, ITEM_EXPIRY_DATE_KEY,ITEM_EXPIRY_DATE_KEY_TYPE, tomorrowString);
        //Set Potion Meta
        potionItemStack.setItemMeta(potionMeta);
        //Return the potion
        return potionItemStack;
    }

    public static boolean isSuperPotion(ItemStack potionItemStack) {
        return doesItemHaveDataKey(potionItemStack, ITEM_EXCLUSIVE_OWNER_KEY, ITEM_EXCLUSIVE_OWNER_KEY_TYPE);
    }

    public static boolean doesPlayerOwnSuperPotion(Player player, ItemStack potionItemStack) {
        String nameOnPotion = getDataKeyValue(potionItemStack, ITEM_EXCLUSIVE_OWNER_KEY, ITEM_EXCLUSIVE_OWNER_KEY_TYPE);
        return player.getName().equals(nameOnPotion);
    }

    public static boolean isSuperPotionExpired(ItemStack potionItemStack) {
        String expiryDateAsString = getDataKeyValue(potionItemStack, ITEM_EXPIRY_DATE_KEY, ITEM_EXPIRY_DATE_KEY_TYPE);
        if(expiryDateAsString == null)
            return false;
        LocalDate expiryDate = LocalDate.parse(expiryDateAsString);
        return TownyCombatTimeUtil.isExpiryTimeReached(expiryDate);
    }
}
