package events.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nerobot
 */
public class Event<TArgs extends EventArgs> implements IEventSender<TArgs> {

    private List<IEventListener<TArgs>> listeners;
    private List<Runnable> delayedCommands;
    private boolean isLocked;
    
    @Override
    public void sendEvent(Object sender, TArgs args) {
        if (listeners == null) return;
        lock();
        for (IEventListener<TArgs> i : listeners) {
            i.onReceived(sender, args);
        }
        unlock();
    }
    
    public void subscribe(final IEventListener<TArgs> listener) {
        if (listener == null) return;
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        if (!isLocked) {
            listeners.add(listener);
        } else {
            addDelayed(new Runnable() {
                @Override
                public void run() {
                    listeners.add(listener);
                }
            });
        }
    }

    public void unsubscribe(final IEventListener<TArgs> listener) {
        if (listener == null) return;
        if (listeners == null) return;
        if (!isLocked) {
            listeners.remove(listener);
        } else {
            addDelayed(new Runnable() {
                @Override
                public void run() {
                    listeners.remove(listener);
                }
            });
        }
    }

    public void unsubscribeAll() {
        if (listeners == null) return;
        if (!isLocked) {
            listeners.clear();
        } else {
            addDelayed(new Runnable() {
                @Override
                public void run() {
                    listeners.clear();
                }
            });
        }
    }

    public boolean hasListeners() {
        return listeners != null && !listeners.isEmpty();
    }

    public boolean canBeReceived() {
        return listeners != null && !listeners.isEmpty();
    }

    private void lock() {
        isLocked = true;
    }
    private void unlock() {
        isLocked = false;
        if (delayedCommands == null) return;
        synchronized (delayedCommands) {
            if (!delayedCommands.isEmpty()) {
                for (Runnable r :
                        delayedCommands) {
                    r.run();
                }
                delayedCommands.clear();
            }
        }
    }

    private void addDelayed(Runnable r) {
        if (delayedCommands == null)
            delayedCommands = new ArrayList<>();
        synchronized (delayedCommands) {
            delayedCommands.add(r);
        }
    }

}
