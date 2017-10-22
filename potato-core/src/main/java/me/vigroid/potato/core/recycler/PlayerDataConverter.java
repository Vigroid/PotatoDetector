package me.vigroid.potato.core.recycler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by vigroid on 10/10/17.
 * conver json to playerbeans
 */

public final class PlayerDataConverter {

    private String mJsonData = null;


    public PlayerDataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    public HashMap<String, ArrayList<PlayerBean>> convert() {


        final HashMap<String, ArrayList<PlayerBean>> resultMap = new HashMap<>();
        final ArrayList<PlayerBean> teamList = new ArrayList<>();
        final ArrayList<PlayerBean> enemyList = new ArrayList<>();

        if (!getJsonData().isEmpty()) {
            final JSONArray dataArray = JSON.parseArray(getJsonData());

            final int size = dataArray.size();
            for (int i = 0; i < size; i++) {
                final JSONObject data = dataArray.getJSONObject(i);
                final String playerId = data.getString("accountId");
                final String shipName = data.getString("ship_name");
                final String playerName = data.getString("player_name");
                final String winRate = data.getFloat("winRate").toString();
                final String battlePlayed = data.getFloat("battles").toString();
                final String avgDmg = data.getFloat("avgDamage").toString();
                final String avgXp = data.getFloat("avgXp").toString();
                final String avgFrags = data.getFloat("avgFrags").toString();
                final boolean isPrivate = data.getBoolean("isPrivateOrHidden");
                final Long ratingNum = data.getLong("rating");
                final boolean isTeammate = (data.getString("team").equals("friendly"));
                final PlayerRating rating = translateToRating(ratingNum);

                if (isTeammate) {
                    teamList.add(new PlayerBean(playerId, shipName, playerName, winRate,
                            battlePlayed, avgDmg, avgXp, avgFrags, rating, ratingNum, isPrivate));
                } else {
                    enemyList.add(new PlayerBean(playerId, shipName, playerName, winRate,
                            battlePlayed, avgDmg, avgXp, avgFrags, rating, ratingNum, isPrivate));
                }
            }

            Collections.sort(teamList, new PlayerComparator());
            Collections.sort(enemyList, new PlayerComparator());
        }
        resultMap.put("team", teamList);
        resultMap.put("enemy", enemyList);


        return resultMap;
    }

    private PlayerRating translateToRating(long rating) {
        if (rating == 0)
            return PlayerRating.NO_RATING;
        if (rating < 750) {
            return PlayerRating.BAD;
        } else if (rating < 1100) {
            return PlayerRating.BELOW_AVG;
        } else if (rating < 1350) {
            return PlayerRating.AVG;
        } else if (rating < 1550) {
            return PlayerRating.GOOD;
        } else if (rating < 1750) {
            return PlayerRating.VERY_GOOD;
        } else if (rating < 2100) {
            return PlayerRating.GREAT;
        } else if (rating < 2450) {
            return PlayerRating.UNICUM;
        } else {
            return PlayerRating.SUPER_UNICUM;
        }
    }
}
