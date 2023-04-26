package io.github.townyadvanced.townycombat.events;

public enum BattlefieldRole {

    LIGHT("battlefield.role.light"), 
    MEDIUM("battlefield.role.medium"), 
    HEAVY("battlefield.role.heavy");

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
