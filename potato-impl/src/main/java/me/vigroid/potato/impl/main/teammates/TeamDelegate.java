package me.vigroid.potato.impl.main.teammates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.ui.loader.LoaderCreator;
import me.vigroid.potato.impl.R;

/**
 * Created by vigroid on 10/13/17.
 * Teammate fragment
 */

public class TeamDelegate extends BottomItemDelegate {


    @Override
    public Object setLayout() {
        return R.layout.delegate_team;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
    }
}
