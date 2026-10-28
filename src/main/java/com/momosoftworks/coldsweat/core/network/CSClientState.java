package com.momosoftworks.coldsweat.core.network;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Mutable state that client-only rendering and tooltip code reads from the networking layer.<br>
 * Packet handlers live outside the client source set, so they publish their results here instead of
 * reaching into client classes directly.
 */
public final class CSClientState
{
    /** Predicate results for the currently hovered stack, keyed by config data UUID. */
    public static final Map<UUID, Boolean> HOVERED_STACK_PREDICATES = new HashMap<>();

    /** Whether a tooltip predicate request is still in flight. */
    public static boolean FETCHING_TOOLTIP = false;

    /** Instant body temperature set by a temperature sync, or {@code null} when unset. */
    public static Double bodyTempInstant = null;

    /** Instant world temperature set by a temperature sync, or {@code null} when unset. */
    public static Double worldTempInstant = null;

    private CSClientState() {}

    public static void setHoveredStackPredicates(Map<UUID, Boolean> predicates)
    {   HOVERED_STACK_PREDICATES.clear();
        HOVERED_STACK_PREDICATES.putAll(predicates);
    }

    public static void setBodyTempInstant(double temp)
    {   bodyTempInstant = temp;
    }

    public static void setWorldTempInstant(double temp)
    {   worldTempInstant = temp;
    }
}