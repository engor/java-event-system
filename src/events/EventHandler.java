package events;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nerobot
 */
public class EventHandler<TArgs extends EventArgs> implements IEventHandler<TArgs> {

    @Override
    public void raiseEvent(Object sender, TArgs args) {
        if (listeners == null) return;
        for (IEventHandler<TArgs> listener : listeners) {
            listener.raiseEvent(sender, args);
        }
    }
    private List<IEventHandler<TArgs>> listeners;
    public void subscribe(IEventHandler<TArgs> listener) {
        if (listener == null) return;
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }
    public void unsubscribe(IEventHandler<TArgs> listener) {
        if (listener == null) return;
        if (listeners == null) return;
        listeners.remove(listener);
    }
    public void unsubscribeAll() {
        if (listeners == null) return;
        listeners.clear();
    }
    public boolean hasListeners() {
        return listeners != null && !listeners.isEmpty();
    }
}
