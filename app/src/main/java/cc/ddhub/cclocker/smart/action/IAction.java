package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by denzelw on 16/8/8.
 */
public interface IAction {

    boolean setNode(AccessibilityNodeInfo node);

    ActionResult execute();

}
