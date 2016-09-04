package cc.ddhub.cclocker.smart;

import android.accessibilityservice.AccessibilityService;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denzelw on 16/8/10.
 */
public class SmartHolder {
    private SparseArray<List<IAccessibilityEventCallBack>> mCallback;
    private AccessibilityNodeInfo mActiveRootNode;
    private SmartService mSmartService;

    private static SmartHolder sHolder = new SmartHolder();

    public static SmartHolder getInstance() {
        return sHolder;
    }

    private SmartHolder() {
        mCallback = new SparseArray<>();
    }

    public void registerService(SmartService service) {
        mSmartService = service;
    }

    public void unregisterService() {
        mSmartService = null;
    }

    public void addCallBack(int type, IAccessibilityEventCallBack callBack) {
        synchronized (SmartHolder.class) {
            List<IAccessibilityEventCallBack> list = mCallback.get(type, new ArrayList<IAccessibilityEventCallBack>());
            list.add(callBack);
            mCallback.put(type, list);
        }
    }

    public void removeCallBack(IAccessibilityEventCallBack callBack) {
        synchronized (SmartHolder.class) {
            int size = mCallback.size();
            int i = 0;
            while (i < size) {
                int key = mCallback.keyAt(i);
                List<IAccessibilityEventCallBack> list = mCallback.get(key);
                if (list != null) {
                    for (IAccessibilityEventCallBack cb : list) {
                        if (cb == callBack) {
                            list.remove(callBack);
                            if (list.isEmpty()) {
                                mCallback.remove(key);
                                i--;
                                size--;
                            }
                            break;
                        }
                    }
                }
                i++;
            }
        }
    }

    public void onAccessibilityEvent(int type) {
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            setCurrentNode(mSmartService.getRootInActiveWindow());
        }
        List<IAccessibilityEventCallBack> list = mCallback.get(type);
        if (list != null) {
            for (IAccessibilityEventCallBack callBack : list) {
                callBack.onAccessibilityEvent();
            }
        }
    }

    public int getCallBackSize() {
        int result = 0;
        int size = mCallback.size();
        int i = 0;
        while (i < size) {
            List<IAccessibilityEventCallBack> list = mCallback.get(mCallback.keyAt(i));
            if (list != null) {
                result += list.size();
            }
            i++;
        }
        return result;
    }

    private void setCurrentNode(AccessibilityNodeInfo node) {
        if (mActiveRootNode != null && mActiveRootNode != node) {
            mActiveRootNode.recycle();
        }
        mActiveRootNode = node;
    }

    public AccessibilityNodeInfo getActiveRootNode() {
        return mActiveRootNode;
    }

    public boolean performBack() {
        if (mSmartService != null) {
            return mSmartService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        }
        return false;
    }

}
