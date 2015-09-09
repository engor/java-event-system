package main;

import events.ObjStateEventArgs;
import java.util.ArrayList;
import java.util.List;
import events.core.EventArgs;
import events.core.IEventHandler;
import events.core.IEventListener;

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

    private static final IEventListener<ObjStateEventArgs> eventHandlerForFreeze = new IEventListener<ObjStateEventArgs>() {
        @Override
        public void onReceived(Object sender, ObjStateEventArgs args) {
            ObjState state = args.newState;
            if (state == ObjState.Freezed) {
                System.out.println("object freeze detected for "+sender);
            }
        }
    };

    private static final IEventListener<EventArgs> eventHandlerForDie = new IEventListener<EventArgs>() {
        @Override
        public void onReceived(Object sender, EventArgs args) {
            System.out.println("object die detected for "+sender);
            // освобождаем ресурсы, если надо, и удаляем из списка
            items.remove((MyObject)sender);
        }
    };

}
