package events;

import events.core.EventArgs;
import main.ObjState;

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
