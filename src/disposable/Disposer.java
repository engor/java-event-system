
package disposable;

/**
 *
 * @author nerobot
 */
public final class Disposer {

    private Disposer(){}

    public static void dispose(IDisposable object) {
        object.dispose();
    }

    public static void disposeAny(Object object) {
        if (object instanceof IDisposable) {
            dispose((IDisposable)object);
        }
    }

}
