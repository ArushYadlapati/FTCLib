package com.seattlesolvers.solverslib.purepursuit.actions;

import com.seattlesolvers.solverslib.purepursuit.waypoints.InterruptWaypoint;

/**
 * This interface represents an action that InterruptWaypoint perform when
 * they reach their interrupt point.
 *
 * @author Michael Baljet, Team 14470
 * @version 1.0
 * @see InterruptWaypoint
 */
public interface InterruptAction {

    /**
     * Performs the action.
     */
    public void doAction();

}
