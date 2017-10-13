package me.vigroid.potato.core.delegates.bottonTab;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import me.vigroid.potato.core.R;
import me.vigroid.potato.core.delegates.PotatoDelegate;

/**
 * Created by vigroid on 10/7/17.
 */

public abstract class BottomItemDelegate extends PotatoDelegate implements View.OnKeyListener{

    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        View rootView = getView();
        if (rootView!=null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-mExitTime) > EXIT_TIME){
                Toast.makeText(getContext(),
                        "Double Click to quit"+getString(R.string.app_name),
                        Toast.LENGTH_LONG)
                        .show();
                mExitTime = System.currentTimeMillis();
            }else {
                _mActivity.finish();
                if (mExitTime!=0){
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
