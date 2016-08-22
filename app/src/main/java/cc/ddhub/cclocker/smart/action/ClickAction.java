package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by denzelw on 16/8/10.
 */
public class ClickAction implements IAction, INodeHandler {
    protected AccessibilityNodeInfo mNode;

    @Override
    public void setNode(AccessibilityNodeInfo node) {
        mNode = node;
    }

    @Override
    public ActionResult execute() {
        if (mNode == null || !mNode.isClickable()) {
            return new ActionResult(false);
        }
        boolean result = mNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        return new ActionResult(result);
    }
}
