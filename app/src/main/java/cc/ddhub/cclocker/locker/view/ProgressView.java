package cc.ddhub.cclocker.locker.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by denzelw on 16/8/7.
 */
public abstract class ProgressView extends View {

    private ValueAnimator mAnimator;
    protected int mDuration = 600;
    protected float mLastProcess;
    protected float mProcess;
    private RectF mRect = new RectF();

    private ValueAnimator.AnimatorUpdateListener mProcessUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float process = (float) animation.getAnimatedValue();
            computeDrawProcess(process, true);
            invalidate();
        }
    };

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected abstract void computeDrawProcess(float process, boolean isAnim);

    protected abstract void drawProcess(Canvas canvas, RectF rectF);

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRect.set(0, 0, right - left, bottom - top);
    }

    public void setProcess(float process, boolean anim) {
        mLastProcess = mProcess;
        this.mProcess = process;
        if (anim) {
            stopAnim();
            mAnimator = ValueAnimator.ofFloat(mLastProcess, process);
            mAnimator.setDuration(mDuration);
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(mProcessUpdateListener);
            mAnimator.start();
        } else {
            computeDrawProcess(process, false);
            invalidate();
        }
    }

    private void stopAnim() {
        if (mAnimator != null) {
            if (mAnimator.isRunning()) {
                mAnimator.end();
            }
            mAnimator.removeAllUpdateListeners();
            mAnimator.removeAllListeners();
            mAnimator = null;
        }
    }

    public void reset() {
        mProcess = 0;
        mLastProcess = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProcess(canvas, mRect);
    }
}
