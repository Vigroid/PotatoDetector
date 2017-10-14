package me.vigroid.potato.core.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.vigroid.potato.core.R;

/**
 * Created by vigroid on 10/14/17.
 */

public class PlayerHolder extends RecyclerView.ViewHolder {

    private TextView mTvShipName;
    private TextView mTvPlayerName;
    private TextView mTvWinRate;
    private TextView mTvBattles;
    private TextView mTvAvgDmg;
    private TextView mTvAvgFrags;
    private boolean isDrawed = false;

    public PlayerHolder(View itemView) {
        super(itemView);
        mTvShipName = itemView.findViewById(R.id.text_ship_name);
        mTvPlayerName = itemView.findViewById(R.id.text_player_name);
        mTvWinRate = itemView.findViewById(R.id.text_win_rate);
        mTvBattles = itemView.findViewById(R.id.text_battles_played);
        mTvAvgDmg = itemView.findViewById(R.id.text_avg_dmg);
        mTvAvgFrags = itemView.findViewById(R.id.text_avg_frags);
    }

    public void bindData(PlayerBean bean) {
        if (isDrawed == false) {
            mTvShipName.setText(mTvShipName.getText() + bean.shipName);
            mTvPlayerName.setText(mTvPlayerName.getText() + bean.playerName);
            mTvWinRate.setText(mTvWinRate.getText() + bean.winRate);
            mTvBattles.setText(mTvBattles.getText() + bean.battlePlayed);
            mTvAvgDmg.setText(mTvAvgDmg.getText() + bean.avgDmg);
            mTvAvgFrags.setText(mTvAvgFrags.getText() + bean.avgFrags);
        }
        isDrawed = true;
    }
}

