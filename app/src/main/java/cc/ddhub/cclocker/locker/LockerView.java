package cc.ddhub.cclocker.locker;

import android.content.Context;
import android.widget.FrameLayout;

import cc.ddhub.cclocker.R;

/**
 * Created by denzelw on 16/7/26.
 */
public class LockerView extends FrameLayout {

    public LockerView(Context context) {
        super(context);
        inflate(context, R.layout.view_locker, this);
        init();
    }

    private void init() {
    }

}
