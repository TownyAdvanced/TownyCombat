# TownyCombat
A combat plugin for Towny, containing some fun combat/battle/pvp features.

## Features:
- :unlock: **Unlock Combat for Regular Players**

  - :unlock: :guard: :guard: :guard: **Battlefield Roles**
    - Each player can type ```/tcm changerole``` to choose a Battlefield Role: Light, Medium, or Heavy.
    - These roles affect mounted horses also, so a total of 6 unique battlefield roles become available:
      - Light Infantry
      - Light Cavalry
      - Medium Infantry
      - Medium Cavalry
      - Heavy Infantry
      - Heavy Cavalry
    - Each role provides unique advantages & disadvantages. Type ```/resident``` for this information:
      - Light<br>
        ![image](https://user-images.githubusercontent.com/50219223/236868982-c487212a-3fec-4ebe-8142-ed99a74594a3.png)
      - Medium<br>
        ![image](https://user-images.githubusercontent.com/50219223/236868670-5203f17d-3e86-4b30-8f19-f93e57a9353e.png)
      - Heavy<br>
        ![image](https://user-images.githubusercontent.com/50219223/236869479-bb776a21-34de-4626-911a-b9c462207cfc.png)    
      <br>
    - Thus, *Players do not require excessively expensive armor and weapons to be competitive in battles.*
  
  - :unlock: :sparkling_heart: :sparkling_heart: :sparkling_heart:  **Potion Transmuter**
    - Potions of Healing are automatically Transmuted into Potion of Regeneration.
    - The healing is +50% of the source potion, but delivered over 15 seconds rather than instantly.
    - Thus, *Players get much more time between using each potion, allowing regular players without elite inventory management skills to successfully manage their potion consumption in combat.*

- :horse: :star: **Cavalry Enhancements:**
  - Overview:
    - Mounted Horses (*aka cavalry*) play the role of "Tanks" on the battlefield: tough + mobile + powerful shot. 
    - Great v.s most infantry, but very vulnerable to infantry/cavalry with spears, and sustained crossbow attack.
    - Not "one man armies", but instead they do best when operating in combination with other troop types.
  - Special Abilities:
    - Cavalry Strength Bonus: +3 PVP Strength Bonus v.s. Infantry for 1 hit. Cooldown for 10 seconds.
    - Missile Shield: Both horse and rider are immune to arrows fired from bows (but not crossbows).
    - Resistance to PVP Attack Damage: +60% .
    - Do not rear up on taking damage.
    - Teleport with player on /n or /t spawn.
  - Special Vulnerabilities:
    - Take +9 damage when hit by a spear in PVP.

- :crossed_swords: :new: **New Weapon**
  - Spear<br>
    ![image](https://user-images.githubusercontent.com/50219223/236872422-90922285-a49e-497a-9528-97a4581ca6db.png)    
  - *Note: The spear bonus can also be given to custom-model weapons. See config file for mode details.*
- :bust_in_silhouette: :footprints: **Tactical Invisibilty:**
  - Use stealth tactics by going invisible on the dynmap.
  - This feature allows players to use many stealth tactics, in exchange for showing less activity on the dynmap.
  - There are 2 Modes:
    - Automatic: You disappear from the map when in the wilderness.
    - Triggered: You disappear from the map when holding a certain combination of items in your hands (*e.g. double diamond swords*).  

- :coffin: :moneybag: **Keep Inventory on Death:**
  - If you die within 400 blocks of a town-homeblock, you keep your inventory and levels.
  - Any tools/weapons/armour in your inventory are subject to a 5% degrade.

- :snake: :x: **Prevent Towny-Block-Glitching by quick-block-place/destroy:**
  - Via a secret magical method.
  - Try it and see!

## Admin Commands:
- ```/tcma reload```: Reload config and language settings.

## Player Commands:
- ```/tcm changerole Light|Medium|Heavy```: Change your Battlefield Role.
 
## Installation:
1. Download the latest ***TownyCombat.jar*** from [here](https://github.com/TownyAdvanced/TownyCombat/releases).
2. Drop the jar into your plugins folder.
3. Restart your server.
4. Edit the file /TownyCombat/config.yml, and configure (***By Default, all features will be configured to off***).
5. Restart your server.

