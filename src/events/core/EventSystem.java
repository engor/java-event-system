
package events.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Enumeration;


/**
 * Этот класс может хранить все собития в одном месте,
 * это позволяет отписывать всех слушателей для конкретного
 * объекта простым вызовом remove()
 *
 * @author nerobot
 */
public final class EventSystem {

    private static final Hashtable<IWithEvents, List<EventHandler<? extends EventArgs>>> events = new Hashtable<>();

    private EventSystem(){}

    public static <TArgs extends EventArgs> EventHandler<TArgs> newEvent(IWithEvents eventOwner) {
        EventHandler<TArgs> e = new EventHandler<>();
        add(eventOwner, e);
        return e;
    }

    public static SimpleEventHandler newSimpleEvent(IWithEvents eventOwner) {
        SimpleEventHandler e = new SimpleEventHandler();
        add(eventOwner, e);
        return e;
    }

    public static void remove(IWithEvents eventOwner) {
        List<EventHandler<? extends EventArgs>> list = events.remove(eventOwner);
        if (list == null) return;
        for (EventHandler<? extends EventArgs> eventHandler : list) {
            eventHandler.unsubscribeAll();
        }
        list.clear();
    }

    public static void removeAllObjects() {
        Enumeration<IWithEvents> keys = events.keys();
        while (keys.hasMoreElements()) {
            remove(keys.nextElement());
        }
    }

    private static void add(IWithEvents owner, EventHandler<? extends EventArgs> event) {
        List<EventHandler<? extends EventArgs>> list = events.get(owner);
        if (list == null) {
            list = new ArrayList<>();
            events.put(owner, list);
        }
        list.add(event);
    }

}
