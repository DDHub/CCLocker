package cc.ddhub.cclocker.smart.action;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by denzelw on 16/8/10.
 */
public class WaitingAction implements IAction {
    private BlockingDeque<Integer> mLock;

    public WaitingAction() {
        mLock = new LinkedBlockingDeque<>(1);
    }

    public void wakeUp() {
        mLock.offer(0);
    }

    @Override
    public boolean setNode(AccessibilityNodeInfo node) {
        return false;
    }

    @Override
    public ActionResult execute() {
        while (true) {
            try {
                mLock.take();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return new ActionResult(false);
            }
        }
        return new ActionResult(true);
    }
}
