package cc.ddhub.cclocker.locker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cc.ddhub.cclocker.LockerApp;

/**
 * Created by denzelw on 16/7/26.
 */
public class LiveService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ScreenReceiver.register(LockerApp.getApp());
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ScreenReceiver.unregister();
    }
}
