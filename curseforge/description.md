# Cold Sweat RAM Fix

Cold Sweat 2.4.3.2 keeps about 150 MB of memory it never uses. This small companion mod frees it. Cold Sweat works exactly the same, and nothing in your worlds or configs changes.

This is an unofficial patch. [Cold Sweat](https://www.curseforge.com/minecraft/mc-mods/cold-sweat) is made by Mikul (MikulDev), and all credit for it goes to them.

## What it fixes

Cold Sweat reads its entity rules (like "this mob, riding that, targeting a player") with Mojang's codec library. The rules nest five levels deep, and the library gives each codec a debug name that repeats everything below it. Each name ends up about 14 MB long, and eleven of them stay in memory until you quit.

Cold Sweat does this on its own, so you save the same amount in a small pack as in a big one. The same 150 MB showed up on a test server with only Forge and Cold Sweat, and in a large modpack.

This mod gives those codecs a short name instead. Cold Sweat reads and writes its data the same way as before.

## Installing

Put it in your `mods` folder next to Cold Sweat.

- In singleplayer, you save about 150 MB.
- On a server, the server saves about 150 MB, and each player who installs it saves about 150 MB on their own computer.
- Clients and servers don't need to match. It works on either side alone.

When it's working, `logs/latest.log` has a line that starts with `Fix applied`. If a later Cold Sweat version changes this code, the mod does nothing and logs `Fix not applied` instead. The game loads normally either way.

Once Cold Sweat fixes this itself, you can remove this mod.

## Good to know

- Minecraft 1.20.1, Forge 47 or newer, Cold Sweat 2.4.3.2 or newer.
- Licensed under GPL-3.0, like Cold Sweat.

Source: https://github.com/RinkyDinkyNooble/cold-sweat-ram-fix
