# TownyCombat
A combat plugin for Towny, containing some fun combat/battle/pvp features.

***User Guide Accurate to version 0
2.1***

## Features:
- :horse: :star: **Cavalry Enhancements:**
  - Mounted Horses (*aka cavalry*) play the role of "Tanks" on the battlefield:
    - Tough, mobile, and with a powerful shot. 
    - Great v.s most infantry, but vulnerable to infantry/cavalry with spears, and sustained crossbow attack.
    - Not "one man armies", but rather do best when operating in combination with other troop types.
  - Special Abilities:
    - Teleport with player on /n or /t spawn.
    - Speed Increase: +12%.
    - Attack Damage Resistance: +60% .
    - Fire Shield: Immune to fire.
    - Missile Shield: Immune to arrows from bows (but not crossbows).
    - Cavalry Strength Bonus: Strength 3 effect for 1 hit, cooldown for 10 seconds.
  - Special Vulnerabilities:
    - Take +9 damage when hit by a spear.
- :snake: :x: **Prevent Towny-Block-Glitching by quick-block-place/destroy:**
  - Via a secret magical method.
  - Try it and see!
- :coffin: :moneybag: **Keep Stuff on Death:**
  - If you die within 400 blocks of a town-homeblock, you keep your inventory and levels.
  - Any tools/weapons/armour in your inventory are subject to a 20% degrade.
- :guard: :guard: :guard: :recycle: **Battlefield Roles**
  - :information_source: Overview:
    - :crossed_swords: Significantly upgrades the combat/battle/war experience of Towny servers.
    - :student: Provides everyone with new combat skills to learn.
    - :rainbow: Makes battles/wars more inclusive of new/casual/builder/trader/roleplaying  players.
  - :spiral_notepad: Details:
    - :arrow_right: **Global Speed Increases**
      - Players: +12%
      - Horses +12%
    - :left_luggage: **Encumbrance**
      - Stuff is harder to carry because of weight: (*a standard RPG feature*)
      - Horses are strong, so the encumbrance of player-riders only affects them at rate of 50%.
      - Speed Adjustments: 
          - Armour:
            - Infantry:
              - Naked: +12% (*the global speed increase*)
              - Full Leather Set: +8%
              - Full Chain-Mail Set: +4%
              - Full Gold Set: +0%
              - Full Iron Set: -4%
              - Full Diamond Set:-8%
              - Full Netherite Set: -12%
            - Cavalry:
              - Naked: +12% (*the global speed increase*)
              - Leather Horse Armour: +8%
              - Gold Horse Armour: +0
              - Iron Horse Armour: -4%
              - Diamond Horse Armour: -8%                 
          - Weapons:
            - Bow: -3%
            - Crossbow: -12%
            - Spear: -5% (*new item*)
            - Warhammer: -12% (*new item*)
          - Other Items:
            - Shield: -4%
            - Shulker Box: -15%
            - Ender Chest: -30%
        - Encumbrance effect on infantry jump/climb speed:
          - Players with 8% encumbrance or more sometimes incur some damage when jumping/climbing.
          - This compensates for an MC bug which allows slowdown-bypassing via. bunny-hopping.
    - :new: **New Items**
      - Spear:
        ![image](https://user-images.githubusercontent.com/50219223/162958194-a7ecd2ae-c880-49be-afb9-6838d21e2a4d.png)
      - Warhammer
        ![image](https://user-images.githubusercontent.com/50219223/162962278-0a172a1c-3f6f-4299-89bc-b92700c2b288.png)
    - :military_helmet: **Military Uniforms**
      - With all the above changes, it makes little sense for an nation to put every soldier in a full-diamond/netherite kit.
      - It makes a more sense to equip all units with at least one identifying piece of kit (*e.g. a coloured leather helmet*).
      - Shields, made practical by autopotting (*see below*), can be very visually effective when decorated with banners. 
    - :teapot: **Auto-Potting**
      - In combat, Splash health potions (I and II) are automatically consumed after a player's health falls below 5 hearts.
      - *This setting offers servers a critical choice:*
        - A. Disabled: 
          - Status Quo.
          - Combat is generally restricted to an elite caste of highly trained 'PVP'ers', 
            often non-roleplaying and/or belonging to cross-server PVP clans.
          - New players are correctly told that they are effectively useless in combat, 
            and should either commit to training (*usually off-server*), or adopt a peaceful supply role.
          - Cross-Server PVP Clans are gifted with an outsized geo-political influence.
        - B Enabled
          - Shake Things Up.
          - Combat is opened up more to new-players/casual-players/roleplayers/builders/traders.
          - *Example:* A player who has never fought in MC before can still be useful in a battle, 
            once their teams equips then with some basic military kit like bow, shield, sword, and a few health potions.
          - The geo-political influence of Cross-Server PVP Clans is reduced.
- :bust_in_silhouette: :footprints: **Tactical Invisibilty:** ***(Disabled By Default)***
  - Use stealth tactics by going invisible on the dynmap.
  - This is a harcore battle feature, which enables stealth tactics, in exchange for showing less activity on the dynmap.
  - There are 2 Modes:
    - Automatic: You go map-invisible when in the wilderness.
    - Triggered: You go map-invisible when holding a certain combination of items in your hands (*e.g. double diamond swords*).  
 
## Commands:
- ```/tcm reload```: Reloads config and language settings

## Installation:
1. Download the latest ***TownyCombat.jar*** from [here](https://github.com/TownyAdvanced/TownyCombat/releases).
2. Drop the jar into your plugins folder.
3. Restart your server.
