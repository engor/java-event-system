package events.core;

/**
 *
 * @author nerobot
 */
public interface IEventSender<TArgs extends EventArgs> {
    public void sendEvent(Object sender, TArgs args);
}
