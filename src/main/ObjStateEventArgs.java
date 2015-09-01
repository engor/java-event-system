package main;

import events.EventArgs;

/**
 *
 * @author nerobot
 */
public class ObjStateEventArgs extends EventArgs {
    public ObjState newState;
    public ObjStateEventArgs(ObjState state) {
        newState = state;
    }
}
