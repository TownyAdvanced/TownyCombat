
package io.github.townyadvanced.townycombat.utils;

import com.palmergames.util.TimeMgmt;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 
 * @author Goosius
 *
 */
public class TownyCombatTimeUtil {

    public static boolean isExpiryTimeReached(LocalDate expiryDate) {
        LocalDate today = LocalDate.now();
        if(expiryDate.isBefore(today)) {
            return true;  //Expiry time was reached yesterday or before
        } else if (expiryDate.equals(today)) {
            if(hasTownyNewDayEventOccurredToday()) {
                return true;  //Expiry time was reached when towny new day occured
            } else {
                return false; //Expiry time not reached yet
            }
        } else {
            return false; //Expiry time not reached yet
        }
    }

    public static boolean hasTownyNewDayEventOccurredToday() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        long secondsUntilNextTownyNewDayEvent = TimeMgmt.townyTime(true);
        LocalDate dateOfNextTownyNewDayEvent = LocalDateTime.now().plusSeconds(secondsUntilNextTownyNewDayEvent).toLocalDate();
        return dateOfNextTownyNewDayEvent.equals(tomorrow);
    }
}
