package cc.ddhub.cclocker.smart.action;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import cc.ddhub.cclocker.smart.SmartHolder;

/**
 * Created by denzelw on 16/8/10.
 */
public class FindAction implements IAction {
    private String mText;

    public FindAction(String text) {
        this.mText = text;
    }

    @Override
    public boolean setNode(AccessibilityNodeInfo node) {
        return false;
    }

    @Override
    public NodeActionResult execute() {
        AccessibilityNodeInfo mNode = SmartHolder.getInstance().getActiveRootNode();
        if (mNode == null) {
            return new NodeActionResult(false, null);
        }
        if (TextUtils.isEmpty(mText)) {
            return new NodeActionResult(false, null);
        }
        List<AccessibilityNodeInfo> list = mNode.findAccessibilityNodeInfosByText(mText);
        if (list == null || list.isEmpty()) {
            return new NodeActionResult(false, null);
        }
        AccessibilityNodeInfo nodeInfo = list.get(0);
        int size = list.size();
        for (int i = 1; i < size; i++) {
            list.get(i).recycle();
        }
        return new NodeActionResult(true, nodeInfo);
    }

}
