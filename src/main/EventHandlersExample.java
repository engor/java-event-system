package main;

/**
 *
 * @author nerobot
 */
public class EventHandlersExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyObject obj = ObjManager.createOne("nerobot");
        obj.freeze();
        obj.makeDead();
        obj.freeze();// это событие уже не увидим, т.к. отписались от всех в MyObject.dispose()
    }

}
