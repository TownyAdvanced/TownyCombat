package io.github.townyadvanced.townycombat.events;

public enum BattlefieldRole {

    LIGHT("battlefield_role_light"), 
    MEDIUM("battlefield_role_medium"), 
    HEAVY("battlefield_role_heavy");

    private String nameKey;
 
    BattlefieldRole(String nameKey) {
        this.nameKey = nameKey;
    }

    public static BattlefieldRole parseString(String roleAsString) {
        switch(roleAsString.toUpperCase()) {
            case "LIGHT":
                return LIGHT;
            case "MEDIUM":
                return MEDIUM;
            case "HEAVY":
                return HEAVY;
                
            default:
                throw new RuntimeException("Unknown role");
        }
    }

    public String getNameKey() {
        return nameKey;
    }

}
