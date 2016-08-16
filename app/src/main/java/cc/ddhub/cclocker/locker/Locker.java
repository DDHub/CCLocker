package cc.ddhub.cclocker.locker;

/**
 * Locker
 * Created by denzelw on 16/7/27.
 */
public class Locker {
    private static Locker sLocker = new Locker();

    private Locker() {

    }

    public static Locker getInstance() {
        return sLocker;
    }

    public void create(ILockerWindowState.CreateReason createReason) {
        LockerStateManager manager = LockerStateManager.getInstance();
        LockerStateManager.State state = manager.getState();
        if (LockerStateManager.State.NONE == state) {
            manager.create(createReason);
        }

    }

    public void show(ILockerWindowState.ShowReason showReason) {
        LockerStateManager manager = LockerStateManager.getInstance();
        LockerStateManager.State state = manager.getState();
        if (LockerStateManager.State.CREATE == state) {
            manager.show(showReason);
        }
    }

    public void hide(ILockerWindowState.HideReason hideReason) {
        LockerStateManager manager = LockerStateManager.getInstance();
        LockerStateManager.State state = manager.getState();
        if (LockerStateManager.State.SHOW == state) {
            manager.hide(hideReason);
        }
    }

    public void destroy(ILockerWindowState.DestroyReason destroyReason) {
        LockerStateManager manager = LockerStateManager.getInstance();
        LockerStateManager.State state = manager.getState();
        if (LockerStateManager.State.HIDE == state) {
            manager.destroy(destroyReason);
        }
    }
}
