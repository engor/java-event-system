package main;

import java.util.ArrayList;
import java.util.List;
import events.EventArgs;
import events.IEventHandler;

/**
 *
 * @author nerobot
 */
public class ObjManager {

    private static final List<MyObject> items = new ArrayList<>();

    public static MyObject createOne(String name) {
        MyObject obj = new MyObject(name);
        items.add(obj);
        // допустим, мы хотим отловить событие, когда объект заморозили
        obj.eventStateChanged.subscribe(eventHandlerForFreeze);
        obj.eventDie.subscribe(eventHandlerForDie);
        return obj;
    }

    private static final IEventHandler<ObjStateEventArgs> eventHandlerForFreeze = new IEventHandler<ObjStateEventArgs>() {
        @Override
        public void raiseEvent(Object sender, ObjStateEventArgs args) {
            ObjState state = args.newState;
            if (state == ObjState.Freezed) {
                System.out.println("object freeze detected for "+sender);
            }
        }
    };

    private static final IEventHandler<EventArgs> eventHandlerForDie = new IEventHandler<EventArgs>() {
        @Override
        public void raiseEvent(Object sender, EventArgs args) {
            System.out.println("object die detected for "+sender);
            // освобождаем ресурсы, если надо, и удаляем из списка
            items.remove((MyObject)sender);
        }
    };

}
