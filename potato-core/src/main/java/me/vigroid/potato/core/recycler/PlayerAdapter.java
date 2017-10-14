package me.vigroid.potato.core.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.vigroid.potato.core.R;

/**
 * Created by vigroid on 10/14/17.
 */

public class PlayerAdapter extends RecyclerView.Adapter {

    private List<PlayerBean> mBeans;

    public PlayerAdapter(List bean){
        mBeans = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, null);
        return new PlayerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlayerHolder mHolder = (PlayerHolder) holder;
        mHolder.bindData(mBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }
}
