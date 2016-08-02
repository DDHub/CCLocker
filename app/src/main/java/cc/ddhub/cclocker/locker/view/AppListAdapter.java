package cc.ddhub.cclocker.locker.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cc.ddhub.cclocker.R;

/**
 * Created by denzelw on 16/7/29.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppItemHolder>{

    private List<AppListPresenter.ItemInfo> mInfoList;
    private AppListView.OnItemClickListener mOnItemClickListener;

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener == null) {
                return;
            }
            int position = (int) v.getTag(R.id.appItemHolderTag);
            if (position > -1) {
                mOnItemClickListener.onItemClick(position, v, mInfoList.get(position));
            }
        }
    };

    public AppListAdapter(List<AppListPresenter.ItemInfo> infoList, AppListView.OnItemClickListener itemClickListener) {
        this.mInfoList = infoList;
        this.mOnItemClickListener = itemClickListener;
    }

    public int getAppIndex(AppListPresenter.ItemInfo itemInfo) {
        int size = mInfoList.size();
        for (int i = 0; i < size; i++) {
            if (mInfoList.get(i).isSameApp(itemInfo.getInfo())) {
                return i;
            }
        }
        return  -1;
    }

    public void change(int index, AppListPresenter.ItemInfo itemInfo) {
        if (index < mInfoList.size()) {
            mInfoList.set(index, itemInfo);
        }
    }

    @Override
    public AppItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AppItemHolder.createHolder(parent);
    }

    @Override
    public void onBindViewHolder(AppItemHolder holder, int position) {
        holder.bind(mInfoList.get(position));
        holder.itemView.setTag(R.id.appItemHolderTag, position);
        holder.itemView.setOnClickListener(mClickListener);
    }

    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

}
