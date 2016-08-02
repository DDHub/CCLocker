package cc.ddhub.cclocker.locker;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.List;

import cc.ddhub.cclocker.LockerApp;
import cc.ddhub.cclocker.R;
import cc.ddhub.cclocker.app.AppLoader;
import cc.ddhub.cclocker.locker.view.AppListPresenter;
import cc.ddhub.cclocker.locker.view.AppListView;
import cc.ddhub.cclocker.util.L;
import cc.ddhub.cclocker.util.ViewUtil;

/**
 * Created by denzelw on 16/7/26.
 */
public class LockerWindow implements ILockerWindowState{
    private Application mApp;
    private View mLockerView;
    private WindowManager mWindowManager;
    private AppTask mAppTask;
    private AppListPresenter mlistPresenter;

    public LockerWindow(Application application) {
        mApp = application;
        mWindowManager = (WindowManager) mApp.getSystemService(Context.WINDOW_SERVICE);
    }

    public void init() {
        LockerStateManager.getInstance().addStateCallBack(this);
    }

    private View createLockerView() {
        View view = new LockerView(LockerApp.getApp());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locker.getInstance().hide(HideReason.UNLOCK);
                Locker.getInstance().destroy(DestroyReason.UNLOCK);
            }
        });
        AppListView listView = ViewUtil.findViewById(view, R.id.appListView);
        mlistPresenter = AppListPresenter.handle(listView);
        return view;
    }

    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.format = PixelFormat.TRANSPARENT;
        params.flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return params;
    }

    @Override
    public void onCreate(CreateReason reason) {
        mLockerView = createLockerView();
        mWindowManager.addView(mLockerView, createLayoutParams());
        mAppTask = new AppTask();
        mAppTask.execute();
    }

    @Override
    public void onShow(ShowReason reason) {

    }

    @Override
    public void onHide(HideReason reason) {

    }

    @Override
    public void onDestroy(DestroyReason reason) {
        if (mAppTask != null) {
            mAppTask.cancel(true);
        }
        mWindowManager.removeView(mLockerView);
        if (mLockerView instanceof ViewGroup) {
            ((ViewGroup) mLockerView).removeAllViews();
            mLockerView = null;
        }
        LockerStateManager.getInstance().removeStateCallBack(this);
    }

    private class AppTask extends AsyncTask<Void, Integer, List<AppLoader.AppInfo>>{

        @Override
        protected List<AppLoader.AppInfo> doInBackground(Void... params) {
            return new AppLoader().getInstallApps(LockerApp.getApp());
        }

        @Override
        protected void onPostExecute(List<AppLoader.AppInfo> list) {
            super.onPostExecute(list);
            L.d("wrw", "post execute");
            if (!isCancelled()) {
                mlistPresenter.setAppInfoList(list);
            }
        }
    }

}
