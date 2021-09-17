package com.willfp.eco.core.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Three values.
 *
 * @param <A> The first value type.
 * @param <B> The second value type.
 * @param <C> The third value type.
 */
public class Triplet<A, B, C> extends Pair<A, B> {
    /**
     * The third item in the tuple.
     */
    @Getter
    @Setter
    @Nullable
    private C third;

    /**
     * Create a triple of values.
     *
     * @param first  The first item in the triplet.
     * @param second The second item in the triplet.
     * @param third  The third item in the triplet.
     */
    public Triplet(@Nullable final A first,
                   @Nullable final B second,
                   @Nullable final C third) {
        super(first, second);

        this.third = third;
    }

    /**
     * component3 exists to allow a pair to be destructured by kotlin.
     * The default kotlin pair already has this, however there is no default
     * pair in java so this exists for parity.
     *
     * @return First.
     */
    public C component3() {
        return third;
    }
}
