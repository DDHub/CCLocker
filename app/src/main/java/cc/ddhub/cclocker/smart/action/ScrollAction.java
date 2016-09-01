package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by denzelw on 16/8/10.
 */
public class ScrollAction implements IAction {
    protected AccessibilityNodeInfo mNode;

    @Override
    public boolean setNode(AccessibilityNodeInfo node) {
        this.mNode = node;
        return false;
    }

    @Override
    public ActionResult execute() {
        if (mNode == null || !mNode.isScrollable()) {
            return new ActionResult(false);
        }
        boolean result = mNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        return new ActionResult(result);
    }
}
