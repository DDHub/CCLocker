package cc.ddhub.cclocker.locker.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import cc.ddhub.cclocker.R;
import cc.ddhub.cclocker.app.AppLoader;
import cc.ddhub.cclocker.util.ViewUtil;

/**
 * Created by denzelw on 16/7/29.
 */
public class AppItemHolder extends RecyclerView.ViewHolder{
    ImageView mIcon;
    TextView mText;
    ValueAnimator mAnimator;
    View mBgView;

    public AppItemHolder(View itemView) {
        super(itemView);
        mIcon = ViewUtil.findViewById(itemView, R.id.item_icon);
        mText = ViewUtil.findViewById(itemView, R.id.item_name);
        mBgView = ViewUtil.findViewById(itemView, R.id.item_bg);
        mBgView.setPivotX(0);
        mBgView.setPivotX(0);
    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float f = (float) animation.getAnimatedValue();
            int color = (int) evaluator.evaluate(f, Color.BLUE, Color.GREEN);
            mBgView.setScaleX(f);
            mBgView.setBackgroundColor(color);
        }
    };

    private Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
        }
    };

    public void bind(AppListPresenter.ItemInfo info) {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        AppLoader.AppInfo appInfo = info.getInfo();
        mIcon.setImageDrawable(appInfo.getIcon());
        mText.setText(appInfo.getName());
        mBgView.setBackgroundColor(getColor(info.getProcess()));
        mBgView.setScaleX(info.getProcess());
    }

    public void process(float process) {
        mAnimator = ValueAnimator.ofFloat(0, process);
        mAnimator.addUpdateListener(updateListener);
        mAnimator.addListener(animatorListener);
        mAnimator.setDuration(700);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.start();
    }

    private int getColor(float process) {
        return (int) new ArgbEvaluator().evaluate(process, Color.BLUE, Color.GREEN);
    }

    public static AppItemHolder createHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_app_item_holder, parent, false);
        return new AppItemHolder(view);
    }
}
