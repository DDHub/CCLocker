package cc.ddhub.cclocker.locker;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/7/26.
 */
public class LockerStateManager {

    enum State {
        CREATE, SHOW, HIDE, DESTROY, NONE
    }

    private State mState = State.NONE;

    private List<WeakReference<ILockerWindowState>> mStateCbList;

    private static final LockerStateManager sInstance = new LockerStateManager();

    private LockerStateManager() {
        mStateCbList = new LinkedList<>();
    }

    public static LockerStateManager getInstance() {
        return sInstance;
    }

    public State getState() {
        return mState;
    }

    public synchronized void create(ILockerWindowState.CreateReason reason) {
        L.d("wrw", "create " + reason + " size-" + mStateCbList.size());
        mState = State.CREATE;
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null) {
                    cb.onCreate(reason);
                } else {
                    mStateCbList.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    public synchronized void show(ILockerWindowState.ShowReason reason) {
        L.d("wrw", "show " + reason + " size-" + mStateCbList.size());
        mState = State.SHOW;
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null) {
                    cb.onShow(reason);
                } else {
                    mStateCbList.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    public synchronized void hide(ILockerWindowState.HideReason reason) {
        L.d("wrw", "hide " + reason + " size-" + mStateCbList.size());
        mState = State.HIDE;
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null) {
                    cb.onHide(reason);
                } else {
                    mStateCbList.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    public synchronized void destroy(ILockerWindowState.DestroyReason reason) {
        L.d("wrw", "destroy " + reason + " size-" + mStateCbList.size());
        mState = State.DESTROY;
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null) {
                    cb.onDestroy(reason);
                } else {
                    mStateCbList.remove(i);
                    i--;
                    size--;
                }
            }
        }
        mState = State.NONE;
    }

    public synchronized void addStateCallBack(ILockerWindowState state) {
        if (state == null) {
            return;
        }
        L.d("wrw", "add state callback " + state.getClass().getSimpleName());
        boolean exit = false;
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null && cb == state) {
                    exit = true;
                    break;
                }
            }
        }
        if (!exit) {
            mStateCbList.add(new WeakReference<ILockerWindowState>(state));
        }
    }

    public synchronized void removeStateCallBack(ILockerWindowState state) {
        if (state == null) {
            return;
        }
        L.d("wrw", "remove state callback " + state.getClass().getSimpleName());
        int size = mStateCbList.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ILockerWindowState> w = mStateCbList.get(i);
            if (w != null) {
                final ILockerWindowState cb = w.get();
                if (cb != null && cb == state) {
                    mStateCbList.remove(i);
                    break;
                }
            }
        }
    }

    public synchronized void clearCallBackList() {
        mStateCbList.clear();
        L.d("wrw", "clear state callback ");
    }

}
