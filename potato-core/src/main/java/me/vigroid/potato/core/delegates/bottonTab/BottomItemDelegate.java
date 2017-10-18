package me.vigroid.potato.core.delegates.bottonTab;

import android.view.View;
import android.widget.Toast;


import me.vigroid.potato.core.R;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.delegates.PotatoDelegate;

/**
 * Created by vigroid on 10/7/17.
 * Each tag in the main interface
 */

public abstract class BottomItemDelegate extends PotatoDelegate{

    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "Double click to quit!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
