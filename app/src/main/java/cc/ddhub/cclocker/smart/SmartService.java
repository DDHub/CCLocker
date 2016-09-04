package cc.ddhub.cclocker.smart;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/8/9.
 */
public class SmartService extends AccessibilityService {
    private SmartHolder mHolder = SmartHolder.getInstance();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        L.d("wrw", "accessibility service connected");
        mHolder.registerService(this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        L.d("wrw", "accessibility event " + event.getEventType());
        mHolder.onAccessibilityEvent(event.getEventType());

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        mHolder.unregisterService();
        super.onDestroy();
    }
}
