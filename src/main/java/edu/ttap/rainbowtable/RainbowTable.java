package edu.ttap.rainbowtable;

import java.util.function.Function;

import java.util.List;
import java.util.Optional;

/**
 * A rainbow table is a collection of chains of passwords for the purpose of
 * reversing hashes.
 */
public class RainbowTable {
    private List<Pair<Password, Password>> chains;
    private Function<Password, Hash> hasher;
    private Function<Hash, Password> reducer;

    /**
     * Constructs a new rainbow table from an already-computed list of endpoints.
     * 
     * @param chains  a list of password chains, pairs of starting and ending
     *                passwords.
     * @param hasher  a function that maps a password to its hash
     * @param reducer a function that maps a hash to its password
     */
    public RainbowTable(
            List<Pair<Password, Password>> chains,
            Function<Password, Hash> hasher,
            Function<Hash, Password> reducer) {
        this.chains = chains;
        this.hasher = hasher;
        this.reducer = reducer;
    }

    /**
     * Attempts to reverse the given hash according to the rainbow table algorithm.
     * 
     * @param h        the hash to invert
     * @param maxSteps the maximum number of steps (hash-reduce cycles) to attempt
     * @return an Optional containing the password if found, or empty if not
     */
    public Optional<Password> invert(Hash h, int maxSteps) {
        Hash hash = h;

        for (int i = 0; i < maxSteps; i++) {
            Password reduced = reducer.apply(hash);

            for(Pair<Password, Password> pair : chains) {
                if (reduced.equals(pair.second())) {
                    Password pw = pair.first();
                    Hash replay = hasher.apply(pw);

                    while (!replay.equals(h)) {
                        pw = reducer.apply(replay);
                        replay = hasher.apply(pw);
                    }

                    return Optional.of(pw);
                }
            }

            hash = hasher.apply(reduced);
        }

        return Optional.empty();
    }
}
