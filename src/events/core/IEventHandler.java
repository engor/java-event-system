package events.core;

/**
 *
 * @author nerobot
 */
public interface IEventHandler<TArgs extends EventArgs> {
    public void fireEvent(Object sender, TArgs args);
}
