package events.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nerobot
 */
public class Event<TArgs extends EventArgs> implements IEventSender<TArgs> {

    private List<IEventListener<TArgs>> listeners;
    
    @Override
    public void sendEvent(Object sender, TArgs args) {
        if (listeners == null) return;
        for (IEventListener<TArgs> i : listeners) {
            i.onReceived(sender, args);
        }
    }
    
    public void subscribe(IEventListener<TArgs> listener) {
        if (listener == null) return;
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void unsubscribe(IEventListener<TArgs> listener) {
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

    public boolean canBeReceived() {
        return listeners != null && !listeners.isEmpty();
    }

}
