package cc.ddhub.cclocker.locker.view;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cc.ddhub.cclocker.LockerApp;
import cc.ddhub.cclocker.app.AppLoader;
import cc.ddhub.cclocker.smart.SmartHolder;
import cc.ddhub.cclocker.smart.SmartTask;
import cc.ddhub.cclocker.smart.action.ActionResult;
import cc.ddhub.cclocker.smart.action.ClickAction;
import cc.ddhub.cclocker.smart.action.FindAction;
import cc.ddhub.cclocker.smart.action.IAction;
import cc.ddhub.cclocker.smart.action.IActionCallBack;
import cc.ddhub.cclocker.smart.action.IntentAction;
import cc.ddhub.cclocker.smart.action.WaitingWindowStateAction;
import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/7/28.
 */
public class AppListPresenter {
    private IView mView;
    private List<ItemInfo> mInfoList;

    public interface IView {

        void attachPresenter(AppListPresenter presenter);

        void setItemInfoList(List<ItemInfo> itemInfoList);

        void change(ItemInfo itemInfo);

    }

    public static class ItemInfo {
        private AppLoader.AppInfo mInfo;
        private float mProcess;
        private boolean mResult;

        public ItemInfo(AppLoader.AppInfo info) {
            this.mInfo = info;
        }

        public AppLoader.AppInfo getInfo() {
            return mInfo;
        }

        public void setInfo(AppLoader.AppInfo mInfo) {
            this.mInfo = mInfo;
        }

        public float getProcess() {
            return mProcess;
        }

        public void setProcess(float mProcess) {
            this.mProcess = mProcess;
        }

        public boolean getResult() {
            return mResult;
        }

        public void setResult(boolean result) {
            setProcess(1);
            this.mResult = result;
        }

        public boolean isSameApp(AppLoader.AppInfo info) {
            return mInfo.equals(info);
        }

    }

    private AppListPresenter(IView view) {
        mView = view;
        view.attachPresenter(this);
        mInfoList = new ArrayList<>();
    }

    public static AppListPresenter handle(IView view) {
        return new AppListPresenter(view);
    }

    public void setAppInfoList(List<AppLoader.AppInfo> infoList) {
        if (infoList == null) {
            return;
        }
        List<ItemInfo> itemList = new ArrayList<>();
        for (AppLoader.AppInfo info : infoList) {
            ItemInfo itemInfo = new ItemInfo(info);
            itemList.add(itemInfo);
        }
        mView.setItemInfoList(itemList);
        mInfoList.clear();
        mInfoList.addAll(itemList);
    }

    public void onItemClick(int position, ItemInfo itemInfo) {
        startAppSettingsPage(mInfoList.get(position));
    }

    boolean b = false;

    private void startAppSettingsPage(final ItemInfo itemInfo) {
        AppLoader.AppInfo appInfo = itemInfo.getInfo();
        String pkg = appInfo.getPkg();
        if (!TextUtils.isEmpty(pkg)) {
            if (!b) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SmartTask task = new SmartTask();
                task.addAction(new IntentAction(LockerApp.getApp(), intent));
                task.execute();
                b = true;
            } else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + pkg));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                final SmartTask task = new SmartTask();
                task.addAction(new IntentAction(LockerApp.getApp(), intent));
                task.addAction(new WaitingWindowStateAction());
                task.addAction(new FindAction("强行停止").root());
                task.addAction(new ClickAction());
                task.addAction(new WaitingWindowStateAction());
                task.addAction(new FindAction("确定").root());
                task.addAction(new ClickAction());

                final int size = task.actionSize();
                task.setActionCallBack(new IActionCallBack() {
                    int count;
                    boolean isSucceed = true;

                    @Override
                    public boolean onPreExecute(IAction action) {
                        if (task.nextAction() instanceof WaitingWindowStateAction) {
                            ((WaitingWindowStateAction) task.nextAction()).startListening();
                        }
                        L.d("wrw", "pre execute " + isSucceed + "  " + action.getClass().getSimpleName());
                        return isSucceed;
                    }

                    @Override
                    public void onExecuteDone(IAction action, ActionResult result) {
                        L.d("wrw", "start app settings page " + isSucceed + " callback size " + SmartHolder.getInstance().getCallBackSize());
                        count++;
                        this.isSucceed = result.result();
                        itemInfo.setProcess(count * 1f / size);
                        if (count == size || !isSucceed) {
                            itemInfo.setResult(isSucceed);
                            mView.change(itemInfo);
                        }
                        mView.change(itemInfo);
                    }

                    @Override
                    public void onExecuteCancel(IAction action) {
                        L.d("wrw", "action cancel " + action);
                    }
                });
                task.setExecuteListener(new SmartTask.OnTaskExecuteListener() {
                    @Override
                    public void onTaskExecuteStart() {

                    }

                    @Override
                    public void onTaskExecuteFinish() {

                    }
                });
                task.execute();
            }
        }
    }

}
