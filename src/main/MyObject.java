package main;

import disposable.Disposer;
import disposable.IDisposable;
import events.EventArgs;
import events.EventHandler;
import events.IWithEvents;
import events.SimpleEventHandler;

/**
 *
 * @author nerobot
 */

public class MyObject implements IDisposable, IWithEvents {

    private ObjState state = ObjState.Undefined;
    private String name;
    // публичный обработчик события, на который можно подписаться
    public final EventHandler<ObjStateEventArgs> eventStateChanged = EventSystem.newEvent(this);
    public final SimpleEventHandler eventDie = EventSystem.newSimpleEvent(this);

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
            eventStateChanged.fireEvent(this, new ObjStateEventArgs(state));
        }
    }

    public void makeDead() {
        setState(ObjState.Dead);
        if (eventDie.hasListeners()) {
            // нам важен сам факт события, параметры не нужны - empty
            eventDie.fireEvent(this, EventArgs.Empty);
        }
        dispose();
    }

    @Override
    public void dispose() {
        EventSystem.remove(this);
    }

    @Override
    public String toString() {
        return "object["+name+"]";
    }
}
