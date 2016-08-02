package cc.ddhub.cclocker.util;

import android.view.View;

/**
 * Created by denzelw on 16/7/28.
 */
public class ViewUtil {

    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(View view, int id) {
        return (V) view.findViewById(id);
    }

}
