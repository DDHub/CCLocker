package cc.ddhub.cclocker.locker.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

import cc.ddhub.cclocker.util.DimenUtil;
import cc.ddhub.cclocker.util.L;

/**
 * Created by denzelw on 16/8/8.
 */
public class CircleProgress extends ProgressView {
    private float mStartDegree;
    private float mSwipeDegree;
    private int mColor = Color.GREEN;
    private ArgbEvaluator mEvaluator = new ArgbEvaluator();
    private Paint mPaint = new Paint();
    private ValueAnimator mAnimator;
    private RectF mRectF = new RectF();
    private float mDx;
    private float mDy;
    private boolean mResult;
    private static final int COLOR_PROCESS = Color.GREEN;
    private static final int COLOR_SUCCEED = Color.GREEN;
    private static final int COLOR_FAIL = Color.RED;

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float f = (float) animation.getAnimatedValue();
            if (mResult) {
                mColor = (int) mEvaluator.evaluate(f, COLOR_PROCESS, COLOR_SUCCEED);
            } else {
                mColor = (int) mEvaluator.evaluate(f, COLOR_PROCESS, COLOR_FAIL);
            }
            invalidate();
        }
    };

    public CircleProgress(Context context) {
        super(context);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        float width = DimenUtil.dp2px(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(width);

        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.addUpdateListener(mUpdateListener);
        mAnimator.setDuration(600);
        mAnimator.setInterpolator(new DecelerateInterpolator());

        mStartDegree = 0;
        mDx = width;
        mDy = width * 1;
    }

    public void setResult(boolean result) {
        mResult = result;
    }

    @Override
    protected void computeDrawProcess(float process, boolean isAnim) {
        mSwipeDegree = 360 * process;
        mColor = COLOR_PROCESS;
        if (process >= 1) {
            if (isAnim && !mAnimator.isRunning()) {
                mAnimator.start();
            } else {
                if (mResult) {
                    mColor = COLOR_SUCCEED;
                } else {
                    mColor = COLOR_FAIL;
                }
                invalidate();
            }
        }
    }

    @Override
    public void reset() {
        mAnimator.end();
        mSwipeDegree = 0;
        mColor = COLOR_PROCESS;
        super.reset();
    }

    @Override
    protected void drawProcess(Canvas canvas, RectF rectF) {
        int saveCount = canvas.save();
        mPaint.setColor(mColor);
        mRectF.set(rectF);
        mRectF.inset(mDx, mDy);
        canvas.drawArc(mRectF, mStartDegree, mSwipeDegree, false, mPaint);
        canvas.restoreToCount(saveCount);
    }

}
