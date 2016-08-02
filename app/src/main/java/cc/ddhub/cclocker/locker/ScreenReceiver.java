package cc.ddhub.cclocker.locker;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.concurrent.atomic.AtomicBoolean;

import cc.ddhub.cclocker.LockerApp;
import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/7/26.
 */
public class ScreenReceiver extends BroadcastReceiver {
    private static Application mApp;
    private static AtomicBoolean mBoolean = new AtomicBoolean(false);
    private static BroadcastReceiver mReceiver;

    public static void register(Application application) {
        if (mBoolean.get()) {
            return;
        }
        mReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        application.registerReceiver(mReceiver, filter);
        mBoolean.set(true);
    }

    public static void unregister() {
        if (mApp != null && mReceiver != null && mBoolean.get()) {
            mApp.unregisterReceiver(mReceiver);
            mBoolean.set(false);
            mApp = null;
            mReceiver = null;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        L.d("wrw", "action " + intent.getAction());
        LockerStateManager.State state = LockerStateManager.getInstance().getState();
        Locker locker = Locker.getInstance();
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_OFF:
                if (LockerStateManager.State.NONE == state) {
                    new LockerWindow(LockerApp.getApp()).init();
                    locker.create(ILockerWindowState.CreateReason.SCREEN_OFF);
                } else if (LockerStateManager.State.SHOW == state) {
                    locker.hide(ILockerWindowState.HideReason.SCREEN_OFF);
                }
                break;
            case Intent.ACTION_SCREEN_ON:
                locker.show(ILockerWindowState.ShowReason.SCREEN_ON);
                break;
        }
    }
}
