package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by denzelw on 16/8/21.
 */
public class NodeActionResult extends ActionResult {
    protected AccessibilityNodeInfo mNode;

    public NodeActionResult(boolean result) {
        super(result);
    }

    public NodeActionResult(boolean result, AccessibilityNodeInfo node) {
        super(result);
        setNode(node);
    }

    public void setNode(AccessibilityNodeInfo node) {
        mNode = node;
    }

    public AccessibilityNodeInfo getNode() {
        return mNode;
    }

}
