package io.github.townyadvanced.townycombat.utils;

import com.palmergames.bukkit.towny.object.Resident;
import io.github.townyadvanced.townycombat.events.BattlefieldRole;
import io.github.townyadvanced.townycombat.metadata.TownyCombatResidentMetaDataController;

public class TownyCombatBattlefieldRoleUtil {
    
    public static BattlefieldRole getBattlefieldRole(Resident resident) {
        String roleAsString = TownyCombatResidentMetaDataController.getBattlefieldRole(resident);
        return BattlefieldRole.parseString(roleAsString);
    }
}
