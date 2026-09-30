package com.rinkynooble.coldsweatramfix;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;

/**
 * Wraps a codec without changing what it reads or writes. The only difference is its name.
 *
 * <p>Some DataFixerUpper codecs copy the full name of the codec inside them when they are built,
 * for example {@code xmap} names itself {@code inner + "[xmapped]"}. Cold Sweat's entity
 * requirement codec contains itself three times per level, over five levels, so its name grows
 * to about 14 MB and every codec built on top keeps its own copy. Giving each level a short
 * fixed name stops that growth.
 */
public final class ShortNameCodec<A> implements Codec<A> {
    private final Codec<A> delegate;
    private final String name;

    public ShortNameCodec(Codec<A> delegate, String name) {
        this.delegate = delegate;
        this.name = name;
    }

    @Override
    public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
        return delegate.encode(input, ops, prefix);
    }

    @Override
    public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
        return delegate.decode(ops, input);
    }

    @Override
    public String toString() {
        return name;
    }
}
