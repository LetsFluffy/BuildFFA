package de.letsfluffy.plorax.buildffa.events;

import de.letsfluffy.plorax.buildffa.BuildFFA;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.events
 * Class created: 2019-02-06, 16:42
 */
public interface Event {

    String getName();

    void start();

    void stop();

}
