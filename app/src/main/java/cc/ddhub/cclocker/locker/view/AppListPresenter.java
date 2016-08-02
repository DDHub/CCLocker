package cc.ddhub.cclocker.locker.view;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.ddhub.cclocker.app.AppLoader;

/**
 * Created by denzelw on 16/7/28.
 */
public class AppListPresenter {
    private IView mView;

    public interface IView {

        void attachPresenter(AppListPresenter presenter);

        void setItemInfoList(List<ItemInfo> itemInfoList);

        void change(ItemInfo itemInfo);

    }

    public static class ItemInfo {
        private AppLoader.AppInfo mInfo;
        private float mProcess;
        private int mState = STATE_INIT;

        static final int STATE_INIT = 0;
        static final int STATE_PROCESS = 1;
        static final int STATE_SUCCEED = 2;
        static final int STATE_FAIL = 3;

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
            setState(STATE_PROCESS);
            this.mProcess = mProcess;
        }

        public int getState() {
            return mState;
        }

        public void setState(int mState) {
            this.mState = mState;
        }

        public boolean isSameApp(AppLoader.AppInfo info) {
            return mInfo.equals(info);
        }

    }

    private AppListPresenter(IView view) {
        mView = view;
        view.attachPresenter(this);
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
    }

    private Random random = new Random(SystemClock.currentThreadTimeMillis());
    public void onItemClick(int position, ItemInfo itemInfo) {
        itemInfo.setProcess(random.nextFloat());
        mView.change(itemInfo);
//        Locker.getInstance().hide(ILockerWindowState.HideReason.UNLOCK);
//        Locker.getInstance().destroy(ILockerWindowState.DestroyReason.UNLOCK);
    }

}
