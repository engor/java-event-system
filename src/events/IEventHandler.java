package events;

/**
 *
 * @author nerobot
 */
public interface IEventHandler<TArgs extends EventArgs> {
    public void raiseEvent(Object sender, TArgs args);
}
