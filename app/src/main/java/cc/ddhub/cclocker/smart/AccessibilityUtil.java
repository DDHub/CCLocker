package cc.ddhub.cclocker.smart;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * Created by denzelw on 16/9/4.
 */
public class AccessibilityUtil {

    public static boolean isAccessibilityOn(Context mContext, Class c) {
        int accessibilityEnabled = 0;
        String service = mContext.getPackageName() + "/" + c.getName();

        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String values = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (!TextUtils.isEmpty(values)) {
                splitter.setString(values);
                while (splitter.hasNext()) {
                    String accessibilityService = splitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
