package main;

import events.EventArgs;
import events.EventHandler;
import events.SimpleEventHandler;

/**
 *
 * @author nerobot
 */

public class MyObject {

    private ObjState state = ObjState.Undefined;
    private String name;
    // публичный обработчик события, на который можно подписаться
    public final EventHandler<ObjStateEventArgs> eventStateChanged = new EventHandler<>();
    public final SimpleEventHandler eventDie = new SimpleEventHandler();

    public MyObject(String name) {
        this.name = name;
    }

    public void freeze() {
        setState(ObjState.Freezed);
    }

    private void setState(ObjState state) {
        if (this.state == state) return;
        this.state = state;
        // проверяем подписчиков, чтобы зря не создавать объект с аргументами
        if (eventStateChanged.hasListeners()) {
            eventStateChanged.raiseEvent(this, new ObjStateEventArgs(state));
        }
    }

    public void makeDead() {
        setState(ObjState.Dead);
        if (eventDie.hasListeners()) {
            // нам важен сам факт события, параметры не нужны - empty
            eventDie.raiseEvent(this, EventArgs.Empty);
        }
        dispose();
    }

    private void dispose() {
        eventDie.unsubscribeAll();
        eventStateChanged.unsubscribeAll();
    }

    @Override
    public String toString() {
        return "object["+name+"]";
    }
}
