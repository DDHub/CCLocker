package cc.ddhub.cclocker.smart;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/8/9.
 */
public class SmartService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        L.d("wrw", "accessibility service connected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        L.d("wrw", "accessibility event " + event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            SmartHolder.getInstance().setRootNode(getRootInActiveWindow());
        }
        SmartHolder.getInstance().onAccessibilityEvent(event.getEventType());

    }

    @Override
    public void onInterrupt() {

    }
}
