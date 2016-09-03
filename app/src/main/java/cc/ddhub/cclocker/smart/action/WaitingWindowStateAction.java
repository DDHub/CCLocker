package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityEvent;

import cc.ddhub.cclocker.smart.IAccessibilityEventCallBack;
import cc.ddhub.cclocker.smart.SmartHolder;

/**
 * Created by w on 2016/9/3.
 */
public class WaitingWindowStateAction extends WaitingAction implements IAccessibilityEventCallBack {

    public void startListening() {
        SmartHolder.getInstance().addCallBack(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED, this);
    }

    @Override
    public ActionResult execute() {
        return super.execute();
    }

    @Override
    public void onAccessibilityEvent() {
        SmartHolder.getInstance().removeCallBack(this);
        wakeUp();
    }
}
