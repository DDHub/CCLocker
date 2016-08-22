package cc.ddhub.cclocker.smart;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by denzelw on 16/8/10.
 */
public class SmartHolder {
    private AccessibilityNodeInfo mRootNode;

    private static SmartHolder sHolder = new SmartHolder();

    private SmartHolder() {

    }

    public static SmartHolder getInstance() {
        return sHolder;
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
