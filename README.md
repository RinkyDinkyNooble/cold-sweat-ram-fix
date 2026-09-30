# Cold Sweat RAM Fix (Unofficial Patch)

Cold Sweat 2.4.3.2 keeps about 150 MB of memory it never uses. This small companion mod frees it. Cold Sweat works exactly the same, and nothing in your worlds or configs changes.

It does this by shortening the debug names Cold Sweat builds for its entity requirement codecs internally.

Client, server or both. They don't need to match.

## Reason For Existing

Temporarily exists until if/when Cold Sweat fixes the issue.

### Credit

This is an unofficial patch. [Cold Sweat](https://github.com/Momo-Softworks/Cold-Sweat) is made by Mikul (MikulDev).

## How It Works

Cold Sweat builds its entity requirement codec in five levels, and each level contains the one before it three times. DFU 6 builds some codec names eagerly (`xmap` names itself `this + "[xmapped]"`), so the names grow to about 14 MB each and eleven of them stay in memory.

A mixin on `EntityRequirement.addCodecStack()` wraps each level in a codec named `EntityRequirement` that passes encoding and decoding straight through. The log says `Fix applied` at startup, or `Fix not applied` if a later Cold Sweat changed this code, in which case the mod does nothing.

## Building

Put `ColdSweat-2.4.3.2.jar` in `libs/` (it's gitignored), then run `gradlew build`.

## License

GPL-3.0, as Cold Sweat's license requires for mods that change its code. The icon is Cold Sweat's icon with text added.
