package me.vigroid.potato.impl.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedHashMap;

import me.vigroid.potato.core.delegates.bottonTab.BaseBottomDelegate;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.delegates.bottonTab.BottomTabBean;
import me.vigroid.potato.core.delegates.bottonTab.ItemBuilder;
import me.vigroid.potato.impl.main.connect.ConnectDelegate;
import me.vigroid.potato.impl.main.enemy.EnemyDelegate;
import me.vigroid.potato.impl.main.team.TeamDelegate;

/**
 * Created by vigroid on 10/12/17.
 * The Very root delegate which include a bottom bar and children delegates
 */

public class PotatoBottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-link}", "Connect"), new ConnectDelegate());
        items.put(new BottomTabBean("{fa-users}", "Team"), new TeamDelegate());
        items.put(new BottomTabBean("{fa-bomb}", "Enemy"), new EnemyDelegate());
        items.put(new BottomTabBean("{fa-cogs}", "Settings"), new TeamDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        Log.d("yo", Integer.toString(mCurrentDelegate));
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#000080");
    }

}
