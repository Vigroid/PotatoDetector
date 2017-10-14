package me.vigroid.potato.core.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.vigroid.potato.core.R;

/**
 * Created by vigroid on 10/14/17.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerHolder> {


    public class PlayerHolder extends RecyclerView.ViewHolder {

        private TextView mTvShipName;
        private TextView mTvPlayerName;
        private TextView mTvWinRate;
        private TextView mTvBattles;
        private TextView mTvAvgDmg;
        private TextView mTvAvgFrags;

        public PlayerHolder(View itemView) {
            super(itemView);
            mTvShipName = itemView.findViewById(R.id.text_ship_name);
            mTvPlayerName = itemView.findViewById(R.id.text_player_name);
            mTvWinRate = itemView.findViewById(R.id.text_win_rate_content);
            mTvBattles = itemView.findViewById(R.id.text_battles_played_content);
            mTvAvgDmg = itemView.findViewById(R.id.text_avg_dmg_content);
            mTvAvgFrags = itemView.findViewById(R.id.text_avg_frags_content);
        }
    }

    private List<PlayerBean> mBeans;

    public PlayerAdapter(List bean) {
        mBeans = bean;
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player,parent,false);
        PlayerHolder holder = new PlayerHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(PlayerHolder holder, int position) {
        PlayerBean bean = mBeans.get(position);
        TextView tvShipName = holder.mTvShipName;
        TextView tvPlayerName = holder.mTvPlayerName;
        TextView tvWinRate = holder.mTvWinRate;
        TextView tvBattles = holder.mTvBattles;
        TextView tvAvgDmg = holder.mTvAvgDmg;
        TextView tvAvgFrags = holder.mTvAvgFrags;

        tvShipName.setText(bean.shipName);
        tvPlayerName.setText(bean.playerName);
        tvWinRate.setText(bean.winRate);
        tvBattles.setText(bean.battlePlayed);
        tvAvgDmg.setText(bean.avgDmg);
        tvAvgFrags.setText(bean.avgFrags);
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }
}
