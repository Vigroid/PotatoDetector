package me.vigroid.potato.core.recycler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by vigroid on 10/10/17.
 */

//TODO: change this class to thread-safe
public final class PlayerDataConverter {

    private String mJsonData = null;


    public PlayerDataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("DATA IS NULL!");
        }
        return mJsonData;
    }

    public HashMap<String,ArrayList<PlayerBean>> convert() {

        //TODO a simulated rating, will add real one when server is ready
        final PlayerRating[] playerRating = { PlayerRating.AVG,PlayerRating.BAD, PlayerRating.GREAT,
                PlayerRating.SUPER_UNICUM,PlayerRating.AVG,PlayerRating.BAD,
                PlayerRating.BAD, PlayerRating.AVG,PlayerRating.AVG,
                PlayerRating.GREAT,PlayerRating.GREAT,PlayerRating.AVG,
                PlayerRating.NO_RATING,PlayerRating.BELOW_AVG,PlayerRating.SUPER_UNICUM,
                PlayerRating.UNICUM,PlayerRating.SUPER_UNICUM,PlayerRating.AVG,
                PlayerRating.GREAT,PlayerRating.GREAT,PlayerRating.AVG,
                PlayerRating.BAD, PlayerRating.AVG,PlayerRating.VERY_GOOD};

        final HashMap<String,ArrayList<PlayerBean>> resultMap= new HashMap<>();
        final ArrayList<PlayerBean> teamList = new ArrayList<>();
        final ArrayList<PlayerBean> enemyList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseArray(getJsonData());

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String playerId= data.getString("accountId");
            final String shipName= data.getString("ship_name");
            final String playerName = data.getString("player_name");
            final String winRate= data.getFloat("winRate").toString();
            final String battlePlayed= data.getFloat("battles").toString();
            final String avgDmg= data.getFloat("avgDamage").toString();
            final String avgXp= data.getFloat("avgXp").toString();
            final String avgFrags= data.getFloat("avgFrags").toString();
            final boolean isPrivate = data.getBoolean("isPrivateOrHidden");
            final boolean isTeammate = (data.getString("team").equals("friendly"));
            final PlayerRating rating = playerRating[i];

            if (isTeammate) {
                teamList.add(new PlayerBean(playerId, shipName, playerName, winRate,
                        battlePlayed, avgDmg, avgXp, avgFrags, rating, isPrivate, isTeammate));
            } else {
                enemyList.add(new PlayerBean(playerId, shipName, playerName, winRate,
                        battlePlayed, avgDmg, avgXp, avgFrags, rating, isPrivate, isTeammate));
            }
        }

        resultMap.put("team",teamList);
        resultMap.put("enemy", enemyList);


        return resultMap;
    }
}
