package io.github.townyadvanced.townycombat.events;

public enum BattlefieldRole {
    
    LIGHT("battlefield.role.light"), 
    MEDIUM("battlefield.role.medium"), 
    HEAVY("battlefield.role.heavy");

    private String nameKey;
    
    BattlefieldRole(String nameKey) {
        this.nameKey = nameKey;
    }

}
