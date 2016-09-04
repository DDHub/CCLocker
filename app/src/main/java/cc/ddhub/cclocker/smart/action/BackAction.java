package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

import cc.ddhub.cclocker.smart.SmartHolder;

/**
 * Created by denzelw on 16/9/4.
 */
public class BackAction implements IAction {

    @Override
    public boolean setNode(AccessibilityNodeInfo node) {
        return false;
    }

    @Override
    public ActionResult execute() {
        if (SmartHolder.getInstance().performBack()) {
            return new ActionResult(true);
        }
        return new ActionResult(false);
    }

}
