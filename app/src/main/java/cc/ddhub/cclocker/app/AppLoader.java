package cc.ddhub.cclocker.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denzelw on 16/7/27.
 */
public class AppLoader {

    public static class AppInfo {
        String name;
        Drawable icon;
        String pkg;

        public AppInfo(String pkg, String name, Drawable icon) {
            this.pkg = pkg;
            this.name = name;
            this.icon = icon;
        }

        public String getPkg() {
            return pkg;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            return icon;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof AppInfo) {
                return this == o || TextUtils.equals(pkg, ((AppInfo) o).getPkg());
            }
            return super.equals(o);
        }
    }

    public List<AppInfo> getInstallApps(Context context) {
        List<AppInfo> infoList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo info : list) {
            boolean isSystem = (info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
            if (!isSystem) {
                isSystem = (info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
            }
            if (!isSystem) {
                CharSequence sequence = info.loadLabel(packageManager);
                if(!TextUtils.isEmpty(sequence)) {
                    infoList.add(new AppInfo(info.packageName, sequence.toString(), info.loadIcon(packageManager)));
                }
            }
        }
        return infoList;
    }

}
