package cc.ddhub.cclocker.smart.action;

import android.os.SystemClock;

/**
 * Created by denzelw on 16/8/10.
 */
public class WaitingAction implements IAction {
    private long mTime;

    public WaitingAction(long time) {
        this.mTime = time;
    }

    @Override
    public ActionResult execute() {
        if (mTime > 0) {
            SystemClock.sleep(mTime);
        }
        return new ActionResult(true);
    }
}
