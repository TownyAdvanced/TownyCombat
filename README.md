# TownyCombat
A combat plugin for Towny, containing some generally useful combat/battle/pvp features.

## Features:
- :horse: :star: **Horse Teleporting:**
  - When using /t or /n spawns, your horse comes with you.
- :snake: :x: **Prevent Towny-Block-Glitching by quick-block-place/destroy:**
  - Via a secret magical method.
  - Try it and see!
- :coffin: :moneybag: **Keep Stuff on Death:**
  - If you die within 400 blocks of a town-homeblock, you keep your inventory and levels.
  - Any tools/weapons/armour in your inventory are subject to a 20% degrade.
- :bust_in_silhouette: :footprints: **Tactical Invisibilty:** ***(Disabled By Default)***
  - Use stealth tactics by going invisible on the dynmap.
  - This is a harcore battle feature, which enables stealth tactics, in exchange for showing less activity on the dynmap.
  - There are 2 Modes:
    - Automatic: You go map-invisible when in the wilderness.
    - Triggered: You go map-invisible when holding a certain combination of items in your hands (*e.g. double diamond swords*).  
- :crossed_swords: :recycle: **Generic Damage Adjustments** ***(upcoming in 0.2.0)***
  - Player weapon damage decreased by -30%.
- :arrow_right: :recycle: **Generic Movement Adjustments** ***(upcoming in 0.2.0)***
  - Player speed increased by +10%.
  - Horse speed increased by +10%.
- :left_luggage: :recycle: **Encumbrance Movement Adjustments** ***(upcoming in 0.2.0)***
  - Carrying Military Equipment reduces speed:
  - Horses are stronger than players, so their encumbrance is reduced by 50%.
  - Equipment:
    - Infantry:
      - Shield: -4 %
      - Full Leather Set: -4 %
      - Full Chainmail Set: -8 %
      - Full Gold Set: -16 %
      - Full Iron Set: -20 %
      - Full Diamond Set: -24 %    (*Walking around literally covered in rocks...*)
      - Full Netherite Set: -28 %
    - Cavalry:
      - Leather Horse Armour: -8%
      - Gold Horse Armour: -32%
      - Iron Horse Armour: -40%
     - Diamond Horse Armour: -48%
  - Players with 8% encumbrance or more are extra slow at jumping/climbing.
  - This feature increases tactical options on the battlefield.
  - This feature also allows nation to develop practically useful military uniforms.
    - Full diamond/netherite sets are no longer de-rigeur, due to their tactical vulnerability (*ie. reduced soldier speed*).
    - Instead, each nation can develop its own practically useful uniform, containing specific materials and (with leather) colours. 

## Commands:
- ```/tcm reload```: Reloads config and language settings

## Installation:
1. Download the latest ***TownyCombat.jar*** from [here](https://github.com/TownyAdvanced/TownyCombat/releases).
2. Drop the jar into your plugins folder.
3. Restart your server.
