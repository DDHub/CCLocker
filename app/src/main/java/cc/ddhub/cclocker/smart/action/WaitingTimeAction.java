package cc.ddhub.cclocker.smart.action;

import android.os.SystemClock;

/**
 * Created by w on 2016/9/3.
 */
public class WaitingTimeAction extends WaitingAction {
    private long mTime;

    public WaitingTimeAction(long time) {
        super();
        mTime = time;
    }

    @Override
    public ActionResult execute() {
        SystemClock.sleep(mTime);
        wakeUp();
        return super.execute();
    }
}
