package cc.ddhub.cclocker.locker.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cc.ddhub.cclocker.R;
import cc.ddhub.cclocker.app.AppLoader;
import cc.ddhub.cclocker.util.ViewUtil;

/**
 * Created by denzelw on 16/7/29.
 */
public class AppItemHolder extends RecyclerView.ViewHolder {
    ImageView mIcon;
    TextView mText;
    CircleProgress mBgView;

    public AppItemHolder(View itemView) {
        super(itemView);
        mIcon = ViewUtil.findViewById(itemView, R.id.item_icon);
        mText = ViewUtil.findViewById(itemView, R.id.item_name);
        mBgView = ViewUtil.findViewById(itemView, R.id.item_bg);
        mBgView.setPivotX(0);
        mBgView.setPivotX(0);
    }

    public void bind(AppListPresenter.ItemInfo info) {
        mBgView.reset();
        AppLoader.AppInfo appInfo = info.getInfo();
        mIcon.setImageDrawable(appInfo.getIcon());
        mText.setText(appInfo.getName());
        setState(info, false);
    }

    private void setState(AppListPresenter.ItemInfo info, boolean anim) {
        float process = info.getProcess();
        if (process >= 1) {
            mBgView.setResult(info.getResult());
        }
        mBgView.setProcess(process, anim);
    }

    public void change(AppListPresenter.ItemInfo info) {
        setState(info, true);
    }

    public static AppItemHolder createHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_item_holder, parent, false);
        return new AppItemHolder(view);
    }
}
