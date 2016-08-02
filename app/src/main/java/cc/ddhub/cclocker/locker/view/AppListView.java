package cc.ddhub.cclocker.locker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by denzelw on 16/7/28.
 */
public class AppListView extends RecyclerView implements AppListPresenter.IView {
    private AppListPresenter mPresenter;
    private AppListAdapter mAdapter;
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view, AppListPresenter.ItemInfo itemInfo) {
            if (mPresenter != null) {
                mPresenter.onItemClick(position, itemInfo);
            }
        }
    };

    public AppListView(Context context) {
        super(context);
        init();
    }

    public AppListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addItemDecoration(new DividerItemDecoration());
    }

    @Override
    public void attachPresenter(AppListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setItemInfoList(List<AppListPresenter.ItemInfo> itemInfoList) {
        mAdapter = new AppListAdapter(itemInfoList, mItemClickListener);
        setAdapter(mAdapter);
    }

    @Override
    public void change(AppListPresenter.ItemInfo itemInfo) {
        int index = mAdapter.getAppIndex(itemInfo);
        if (index > -1) {
            int first = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int p = index - first;
            if (p >= 0 && p < getChildCount()) {
                AppItemHolder holder = (AppItemHolder) getChildViewHolder(getChildAt(p));
                holder.process(itemInfo.getProcess());
            }
            mAdapter.change(index, itemInfo);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, View view, AppListPresenter.ItemInfo itemInfo);
    }

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private static final int HEIGHT = 1;

        private Drawable mDivider;

        public DividerItemDecoration() {
            mDivider = new ColorDrawable(Color.WHITE);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + HEIGHT;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            outRect.set(0, 0, 0, HEIGHT);
        }
    }


}
