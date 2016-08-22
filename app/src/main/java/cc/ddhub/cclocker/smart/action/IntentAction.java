package cc.ddhub.cclocker.smart.action;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 *  for start intent
 * Created by denzelw on 16/8/8.
 */
public class IntentAction implements IAction {
    private WeakReference<Context> mContextRef;
    private Intent mIntent;

    public IntentAction(Context context, Intent intent) {
        this.mContextRef = new WeakReference<>(context);
        this.mIntent = intent;
    }

    @Override
    public final ActionResult execute() {
        final Context context = mContextRef.get();
        if (context != null && mIntent != null) {
            try {
                context.startActivity(mIntent);
                return new ActionResult(true);
            } catch (Exception ignored) {
                // TODO collect mobile info
            }
        }
        return new ActionResult(false);
    }

}
