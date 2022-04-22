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
- :guard: :star: **Infantry Enchancements**
  - Speed Increase: +12%
- :crossed_swords: :new: **New Weapons**
  - Spear:
    ![image](https://user-images.githubusercontent.com/50219223/162958194-a7ecd2ae-c880-49be-afb9-6838d21e2a4d.png)
  - Warhammer
    ![image](https://user-images.githubusercontent.com/50219223/162962278-0a172a1c-3f6f-4299-89bc-b92700c2b288.png)
  - Custom Weapons
    - The special bonuses can also be given to custom weapons made by other plugins.
    - See config file for more details.
- :left_luggage: :brain: :shirt: **Encumbrance, Tactics, & Military Uniforms**
  - :Encumbrance:
    - Standard combat feature:
      - All soldiers do not move at exactly the same speed.
      - Rather, encumbered soldiers move more slowly.
    - Base Encumbrance / Speed adjustments:
      - Helmet: 0.6%
      - Leggings: 1.2%
      - Chestplate: 1.6%
      - Boots: 0.6%
      - Bow: 3%
      - Crossbow: 12%
      - Spear: 5% (*new item*)
      - Warhammer: 12% (*new item*)
      - Shield: 4%
      - Shulker Box: 15%
    - Armour Material Modifiers:
      - Leather: x1
      - Chain-Mail: x2
      - Gold/Turtle: x3
      - Iron: x4
      - Diamond: x5
      - Netherite: x6
    - Horse Carry Modifier:
      - -50%
    - Heavy Armour Jump Damage:
      - Players with 8% encumbrance or more sometimes incur some damage when jumping/climbing.
      - Compensates for an MC bug which allows slowdown-bypassing via. bunny-hopping.
  - Tactics:
    - With encumbrance in effect, troops types and tactics naturally become more diverse.
  - Military Uniforms
    - With encumbrance in effect, it makes little sense for an nation to put every soldier in a full-diamond/netherite kit.
    - It makes a more sense to equip all units with at least one identifying piece of kit (*e.g. a coloured leather helmet*).
- :sparkling_heart: :heavy_plus_sign: **Auto-Potting**
  - In combat, Splash health potions (I and II) are automatically consumed after a player's health falls below 5 hearts.
  - This feature unlocks battlefield participation for the majority of Towny players (*e.g. new-players / casual-players / traders / builders / roleplayers*) who are otherwise massively excluded from battles, by a too-high skill bar of lightning-quick inventory-management.
  - Protests can be expected from a minority, who having already cleared the participation bar, are willing to continue excluding the majority from battles, in exchange for holding on to their own power.	
  - More progressive PVP'ers will recognize both a challenge and an opportunity, to dominate the battlefield less by twitchy young fingers, and more by solid army organization, reliable logistics and clever military tactics.
- :coffin: :moneybag: **Keep Stuff on Death:**
  - If you die within 400 blocks of a town-homeblock, you keep your inventory and levels.
  - Any tools/weapons/armour in your inventory are subject to a 20% degrade.
- :snake: :x: **Prevent Towny-Block-Glitching by quick-block-place/destroy:**
  - Via a secret magical method.
  - Try it and see!
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
