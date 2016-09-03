package cc.ddhub.cclocker.smart;

import android.util.SparseArray;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denzelw on 16/8/10.
 */
public class SmartHolder {
    private SparseArray<List<IAccessibilityEventCallBack>> mCallback;
    private AccessibilityNodeInfo mRootNode;

    private static SmartHolder sHolder = new SmartHolder();

    public static SmartHolder getInstance() {
        return sHolder;
    }

    private SmartHolder() {
        mCallback = new SparseArray<>();
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

    public void setRootNode(AccessibilityNodeInfo node) {
        if (mRootNode != null && mRootNode != node) {
            mRootNode.recycle();
        }
        mRootNode = node;
    }

    public AccessibilityNodeInfo getRootNode() {
        return mRootNode;
    }

}
