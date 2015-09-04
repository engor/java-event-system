package events;

/**
 *
 * @author nerobot
 */
public interface IEventListener<TArgs extends EventArgs> {
    public void onReceived(Object sender, TArgs args);
}
