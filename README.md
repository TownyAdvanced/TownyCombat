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
- :crossed_swords: :recycle: **Battlefield Roles**
  - :information_source: Overview:
    - :crossed_swords: Significantly upgrades the combat/battle/war experience of Towny servers.
    - :student: Provides everyone with new combat skills to learn.
    - :rainbow: Makes battles/wars more inclusive of new/casual/builder/trader/roleplaying  players.
  - :spiral_notepad: Details:
    - :left_luggage: **Encumbrance** (*The Centrepiece*)
      - Equipment-Based reduction in move speed:
        - Weapons:
          - Bow: 3%
          - Crossbow: 6%
          - Spear: 5% (*new item*)
          - Warhammer: 12% (*new item*)
        - Items:
          - Shield: 4%
          - Shulker Box: 15%
          - Ender Chest: 30%
        - Player Armour:
          - Base:
            - Helmet: 0.6%
            - Chestplate: 1.6%
            - Leggings: 1.2%
            - Boots: 0.6%
          - Materials:
            - Leather: +0%
            - Chain Mail: +100%
            - Turtle Shell: +200%
            - Gold: +300%
            - Iron: +400%
            - Diamond: +500% (*literally walking around covered in rocks...*)
            - Netherite: +600%
        - Horse Armour:
          - Leather Horse Armour: 8%
          - Gold Horse Armour: 32%
          - Iron Horse Armour: 40%
          - Diamond Horse Armour: 48%      
      - Horse-carry Modifier:
        - Horses are stronger than players, so their encumbrance is reduced by 50%.
      - Equipment reduction in jump/climb speed:
        - Players with 8% encumbrance or more get an extra slowing effect when jumping/climbing.
        - *Sorry this effect is a little janky, but it compensates for an MC bug which allows slowdown-bypassing via. bunny-hopping.*
    - :new: **New Items**
      - Spear:
        ![image](https://user-images.githubusercontent.com/50219223/162958194-a7ecd2ae-c880-49be-afb9-6838d21e2a4d.png)
      - Warhammer
        ![image](https://user-images.githubusercontent.com/50219223/162962278-0a172a1c-3f6f-4299-89bc-b92700c2b288.png)
    - :arrow_right: **Movement Increases**
      - Players: +10%
      - Horses +10%
    - :shield: **Attack Damage Resistance**
      - Players: 30%
      - Horses: 60%
    - :horse_racing: **Cavalry Charges**  
      - Players on horseback get a Charge Bonus of Strength 1.
      - When a player mounts a horse, or hits an enemy, the Charge Bonus goes into a 10 second cooldown.
    - :teapot: **Auto-Potting** (*The critical setting for battlefield inclusivity*)
      - After a player's health drops below 10 (*i.e 5 hearts*), then on the next incoming attack, any available Splash-Health potions are automatically consumed from the player's inventory, to take their health to back over 10.
    - :guard: :guard: :guard: **Military Uniforms**
      - With all the above changes, it makes little sense for an nation to put every soldier in a full-diamond/netherite kit.
      - It makes a more sense to equip all units with at least one identifying piece of kit (*e.g. a coloured leather helmet*).
      - Shields, being freed from purgatory by autopotting, can be particularly visually effective, as they can be decorated with banners. 
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
