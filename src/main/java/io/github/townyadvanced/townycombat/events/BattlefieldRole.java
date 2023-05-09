package io.github.townyadvanced.townycombat.events;

public enum BattlefieldRole {

    LIGHT_INFANTRY("battlefield_role_light_infantry"),
    LIGHT_CAVALRY("battlefield_role_light_cavalry"),
    MEDIUM_INFANTRY("battlefield_role_medium_infantry"),
    MEDIUM_CAVALRY("battlefield_role_medium_cavalry"),
    HEAVY_INFANTRY("battlefield_role_heavy"),
    HEAVY_CAVALRY("battlefield_role_heavy");

    private String nameKey;
 
    BattlefieldRole(String nameKey) {
        this.nameKey = nameKey;
    }

    public static BattlefieldRole parseString(String roleAsString) {
        switch(roleAsString.toUpperCase()) {
            case "LIGHT_INFANTRY":
                return LIGHT_INFANTRY;
            case "LIGHT_CAVALRY":
                return LIGHT_CAVALRY;
            case "MEDIUM_INFANTRY":
                return MEDIUM_INFANTRY;
            case "MEDIUM_CAVALRY":
                return MEDIUM_CAVALRY;
            case "HEAVY_INFANTRY":
                return HEAVY_INFANTRY;
            case "HEAVY_CAVALRY":
                return HEAVY_CAVALRY;
            default:
                throw new RuntimeException("Unknown role");
        }
    }

    public String getNameKey() {
        return nameKey;
    }

}
