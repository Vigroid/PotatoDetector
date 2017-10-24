package me.vigroid.potato.core.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.vigroid.potato.core.R;
import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.app.SavedStates;
import me.vigroid.potato.core.util.preference.PotatoPreference;

/**
 * Created by vigroid on 10/14/17.
 * Adapter for recyclerview, this include a VH(viewholder) where UI changes are made
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerHolder> {


    public class PlayerHolder extends RecyclerView.ViewHolder {

        private TextView mTvShipName;
        private TextView mTvPlayerName;
        private TextView mTvWinRate;
        private TextView mTvBattles;
        private TextView mTvAvgDmg;/*
        private TextView mTvAvgXp;
        private TextView mTvAvgFrags;*/
        private TextView mTvMore;
        private TextView mView;

        public PlayerHolder(View itemView) {
            super(itemView);
            mTvShipName = itemView.findViewById(R.id.text_ship_name);
            mTvPlayerName = itemView.findViewById(R.id.text_player_name);
            mTvWinRate = itemView.findViewById(R.id.text_win_rate_content);
            mTvBattles = itemView.findViewById(R.id.text_battles_played_content);
            mTvAvgDmg = itemView.findViewById(R.id.text_avg_dmg_content);/*
            mTvAvgXp = itemView.findViewById(R.id.text_avg_xp_content);
            mTvAvgFrags = itemView.findViewById(R.id.text_avg_frags_content);*/
            mTvMore = itemView.findViewById(R.id.text_more);
            mView = itemView.findViewById(R.id.player_rating_tag);
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

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(PlayerHolder holder, int position) {
        final PlayerBean bean = mBeans.get(position);

        holder.mTvShipName.setText(bean.shipName);
        holder.mTvPlayerName.setText(bean.playerName);
        holder.mTvWinRate.setText(bean.winRate);
        holder.mTvBattles.setText(bean.battlePlayed);
        holder.mTvAvgDmg.setText(bean.avgDmg);/*
        holder.mTvAvgXp.setText(bean.avgXp);
        holder.mTvAvgFrags.setText(bean.avgFrags);*/
        holder.mTvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean.playerId != null && !(bean.playerId.isEmpty())) {
                    final String url = "http://"+ PotatoPreference.getCustomString(SavedStates.PLAYER_REGION.name()) +".wows-numbers.com/player/" + bean.playerId + "," + bean.playerName;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);
                }else {
                    Toast.makeText(mContext, "Can't find user with this ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PlayerRating pr = bean.rating;

        switch (pr){
            case BAD:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_bad));
                holder.mView.setText(mContext.getString(R.string.rating_bad));
                break;
            case BELOW_AVG:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_below_avg));
                holder.mView.setText(mContext.getString(R.string.rating_below_avg));
                break;
            case AVG:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_avg));
                holder.mView.setText(mContext.getString(R.string.rating_avg));
                break;
            case GOOD:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_good));
                holder.mView.setText(mContext.getString(R.string.rating_good));
                break;
            case VERY_GOOD:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_very_good));
                holder.mView.setText(mContext.getString(R.string.rating_very_good));
                break;
            case GREAT:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_great));
                holder.mView.setText(mContext.getString(R.string.rating_great));
                break;
            case UNICUM:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_unicum));
                holder.mView.setText(mContext.getString(R.string.rating_unicum));
                break;
            case SUPER_UNICUM:
                holder.mView.setBackgroundColor(mContext.getColor(R.color.rating_super_unicum));
                holder.mView.setText(mContext.getString(R.string.rating_super_unicum));
                break;
            case NO_RATING:
                holder.mView.setBackgroundColor(Color.GRAY);
                holder.mView.setText(mContext.getString(R.string.rating_no));
            default:
                holder.mView.setBackgroundColor(Color.GRAY);
                holder.mView.setText(mContext.getString(R.string.rating_no));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    public void refresh(List<PlayerBean> beans){
        mBeans.clear();
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }
}
