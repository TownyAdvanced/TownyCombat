# TownyCombat
A combat plugin for Towny, containing some fun combat/battle/pvp features.

## Features:
- :horse: :star: **Horse Teleporting:**
  - When using /t or /n spawns, your horse comes with you.
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
    - :shield: **Attack Damage Resistance**
      - Players: +30%
      - Horses: +60%
    - :arrow_right: **Global Speed Increases**
      - Players: +10%
      - Horses +10%
    - :horse_racing: **Cavalry Charges**  
      - Players on horseback get a Charge Bonus of Strength 1.  ***(2 in v0.2.1)***
      - When a player mounts a horse, or hits an enemy, the Charge Bonus goes into a 10 second cooldown. ***(12 in v0.2.1)***
    - :left_luggage: **Encumbrance**
      - Stuff is harder to carry because of weight: (*a standard RPG feature*)
      - Speed Adjustments: 
          - Horse-Carry Modifier:
            - Horses are stronger than players, so any speed reduction while mounted is halved.
          - Armour:
            - Infantry:
              - Naked: +10% (*the global speed increase*)
              - Full Leather Set: +6%
              - Full Chain-Mail Set: +2%
              - Full Gold Set: -6%
              - Full Iron Set: -10%
              - Full Diamond Set:-14%
              - Full Netherite Set: -18%
            - Cavalry:
              - Naked: +10% (*the global speed increase*)
              - Leather Horse Armour: +2%
              - Gold Horse Armour: -22%
              - Iron Horse Armour: -30%
              - Diamond Horse Armour: -38%                 
          - Weapons:
            - Bow: -3%
            - Crossbow: -6%
            - Spear: -5% (*new item*)
            - Warhammer: -12% (*new item*)
          - Other Items:
            - Shield: -4%
            - Shulker Box: -15%
            - Ender Chest: -30%
        - Encumbrance effect on infantry jump/climb speed:
          - Players with 8% encumbrance or more get an extra slowing effect when jumping/climbing.
          - *Sorry this effect is a little janky, but it compensates for an MC bug which allows slowdown-bypassing via. bunny-hopping.*
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
