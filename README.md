# Cold Sweat RAM Fix

A small companion mod for [Cold Sweat](https://www.curseforge.com/minecraft/mc-mods/cold-sweat) on Minecraft 1.20.1 (Forge). Cold Sweat 2.4.3.2 keeps about 150 MB of memory it never uses, and this mod frees it. Gameplay doesn't change.

This is an unofficial patch. Cold Sweat is made by Mikul ([MikulDev](https://github.com/Momo-Softworks/Cold-Sweat)), and all credit for Cold Sweat goes to them.

## What it fixes

Cold Sweat reads its config and datapacks with Mojang's codec library. The codec for entity requirements (rules like "this entity, riding that, targeting something else") is built in five levels, and each level contains the one below it three times.

Some codecs in that library build a text name for themselves when they're created, and the name includes the full name of every codec inside them. With that nesting, each name grows to about 14 MB. Eleven of these names stay in memory until the game closes, which adds up to about 150 MB.

The amount doesn't depend on the modpack. It comes from Cold Sweat alone: a test server with only Forge and Cold Sweat held the same eleven names as a large modpack. Each game process has one copy, so singleplayer saves about 150 MB, and on a multiplayer server the server and each player's game save about 150 MB each.

This mod gives each level the short name "EntityRequirement" as it's built. Cold Sweat reads and writes its data exactly as before. Only the names change.

## Installing

Put the jar in the `mods` folder next to Cold Sweat. Install it on each side where you want the memory back: the client, the server, or both. A client and a server don't need to match.

To check that it's working, look in `logs/latest.log` for this line:

```
[Cold Sweat RAM Fix/]: Fix applied: shortened the names of 4 Cold Sweat entity requirement codec levels.
```

If a future Cold Sweat version builds this codec differently, the mod does nothing and logs a warning that starts with `Fix not applied`. The game loads normally either way.

Once Cold Sweat fixes this itself, you can remove this mod.

## Seeing the difference

The log line is enough to know the fix is on. To see the memory itself, take a heap dump with [spark](https://spark.lucko.me/) (`/spark heapdump`) and open it in a heap analyzer such as [Eclipse Memory Analyzer](https://eclipse.dev/mat/). This query lists very long strings:

```
SELECT s.@retainedHeapSize, toString(s) FROM java.lang.String s WHERE s.value.@length > 200000
```

Without this mod it finds eleven strings of about 14 MB that start with `EitherCodec[RecordCodec[UnitDecoder[com.momosoftworks.coldsweat...`. With it, none of them are there.

## Requirements

- Minecraft 1.20.1
- Forge 47 or newer
- Cold Sweat 2.4.3.2 or newer

## Building

1. Create a `libs/` folder in the project root.
2. Put `ColdSweat-2.4.3.2.jar` in it. You can download it from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/cold-sweat/files) or [Modrinth](https://modrinth.com/mod/cold-sweat/versions).
3. Run `gradlew build`. The jar ends up in `build/libs/`.

The `libs/` folder is gitignored, so Cold Sweat's jar isn't in this repository.

## License

Copyright (C) 2026 RinkyNooble. Released under the GNU General Public License v3.0, the same license as Cold Sweat. Cold Sweat's license requires this for any project that modifies its code, and this mod changes Cold Sweat's code while the game loads. The icon is Cold Sweat's icon with text added. See [LICENSE](LICENSE).
