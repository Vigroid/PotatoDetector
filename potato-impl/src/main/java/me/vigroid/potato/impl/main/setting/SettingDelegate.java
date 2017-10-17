package me.vigroid.potato.impl.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.impl.R;

/**
 * Created by vigroid on 10/16/17.
 * settings page
 */

public class SettingDelegate extends BottomItemDelegate {


    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
    }
}
