
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

    private static final Hashtable<IWithEvents, List<Event<? extends EventArgs>>> events = new Hashtable<>();

    private EventSystem(){}

    public static <TArgs extends EventArgs> Event<TArgs> newEvent(IWithEvents eventOwner) {
        Event<TArgs> e = new Event<>();
        add(eventOwner, e);
        return e;
    }

    public static SimpleEvent newSimpleEvent(IWithEvents eventOwner) {
        SimpleEvent e = new SimpleEvent();
        add(eventOwner, e);
        return e;
    }

    public static void remove(IWithEvents eventOwner) {
        List<Event<? extends EventArgs>> list = events.remove(eventOwner);
        if (list == null) return;
        for (Event<? extends EventArgs> eventHandler : list) {
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

    private static void add(IWithEvents owner, Event<? extends EventArgs> event) {
        List<Event<? extends EventArgs>> list = events.get(owner);
        if (list == null) {
            list = new ArrayList<>();
            events.put(owner, list);
        }
        list.add(event);
    }

}
