package io.github.townyadvanced.townycombat.events;

import org.bukkit.Material;

public enum BattlefieldRole {

    LIGHT_INFANTRY("battlefield_role_light_infantry", Material.BOW, 0),
    LIGHT_CAVALRY("battlefield_role_light_cavalry", Material.BOW, 1),
    MEDIUM_INFANTRY("battlefield_role_medium_infantry", Material.CROSSBOW, 0),
    MEDIUM_CAVALRY("battlefield_role_medium_cavalry", Material.CROSSBOW, 3),
    HEAVY_INFANTRY("battlefield_role_heavy", null, 0),
    HEAVY_CAVALRY("battlefield_role_heavy", Material.CROSSBOW, 2);

    private final String nameKey;
    /*
     * It is important for good functioning of the cavalry missile shield,
     * that a role have either BOW, CROSSBOW, or null.
     */
    private final Material missileWeapon;
    private final int cavalryStrengthBonus;
 
    public Material getMissileWeapon() {
        return missileWeapon;
    }
    
    public int getCavalryStrengthBonus() {
        return cavalryStrengthBonus;
    }
    
    public boolean hasCavalryStrengthBonus() {
        return  cavalryStrengthBonus > 0;
    }
    
    BattlefieldRole(String nameKey, Material missileWeapon, int cavalryChargeBonus) {
        this.nameKey = nameKey;
        this.missileWeapon = missileWeapon;
        this.cavalryStrengthBonus = cavalryChargeBonus;
    }

    public static BattlefieldRole parseString(String roleAsString) {
        switch(roleAsString.toUpperCase()) {
            case "LIGHT-INFANTRY":
                return LIGHT_INFANTRY;
            case "LIGHT-CAVALRY":
                return LIGHT_CAVALRY;
            case "MEDIUM-INFANTRY":
                return MEDIUM_INFANTRY;
            case "MEDIUM-CAVALRY":
                return MEDIUM_CAVALRY;
            case "HEAVY-INFANTRY":
                return HEAVY_INFANTRY;
            case "HEAVY-CAVALRY":
                return HEAVY_CAVALRY;
            default:
                throw new RuntimeException("Unknown role");
        }
    }

    public String getNameKey() {
        return nameKey;
    }
}
