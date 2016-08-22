package cc.ddhub.cclocker.smart.action;

/**
 * Created by denzelw on 16/8/10.
 */
public class ActionResult {
    private boolean mResult;

    public ActionResult(boolean result) {
        this.mResult = result;
    }

    public boolean result() {
        return mResult;
    }

}
