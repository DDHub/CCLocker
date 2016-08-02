package cc.ddhub.cclocker.locker;

/**
 * Created by denzelw on 16/7/26.
 */
public interface ILockerWindowState {

    enum CreateReason {
        SCREEN_OFF
    }

    enum ShowReason {
        SCREEN_ON
    }

    enum HideReason {
        SCREEN_OFF, UNLOCK
    }

    enum DestroyReason {
        UNLOCK
    }

    void onCreate(CreateReason reason);

    void onShow(ShowReason reason);

    void onHide(HideReason reason);

    void onDestroy(DestroyReason reason);

}
