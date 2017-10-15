package me.vigroid.potato.core.recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
        private TextView mTvMore;

        public PlayerHolder(View itemView) {
            super(itemView);
            mTvShipName = itemView.findViewById(R.id.text_ship_name);
            mTvPlayerName = itemView.findViewById(R.id.text_player_name);
            mTvWinRate = itemView.findViewById(R.id.text_win_rate_content);
            mTvBattles = itemView.findViewById(R.id.text_battles_played_content);
            mTvAvgDmg = itemView.findViewById(R.id.text_avg_dmg_content);
            mTvAvgFrags = itemView.findViewById(R.id.text_avg_frags_content);
            mTvMore = itemView.findViewById(R.id.text_more);
        }
    }

    private List<PlayerBean> mBeans;
    private Context mContext;

    public PlayerAdapter(List bean, Context context) {
        this.mBeans = bean;
        this.mContext = context;
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player,parent,false);
        PlayerHolder holder = new PlayerHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(PlayerHolder holder, int position) {
        final PlayerBean bean = mBeans.get(position);

        holder.mTvShipName.setText(bean.shipName);
        holder.mTvPlayerName.setText(bean.playerName);
        holder.mTvWinRate.setText(bean.winRate);
        holder.mTvBattles.setText(bean.battlePlayed);
        holder.mTvAvgDmg.setText(bean.avgDmg);
        holder.mTvAvgFrags.setText(bean.avgFrags);
        holder.mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://www.google.com/search?q=" + bean.shipName;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }
}
