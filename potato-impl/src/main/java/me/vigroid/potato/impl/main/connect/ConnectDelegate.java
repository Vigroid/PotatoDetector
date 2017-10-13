package me.vigroid.potato.impl.main.connect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/13/17.
 * Connect fragment
 */

public class ConnectDelegate extends BottomItemDelegate {

    @BindView(R2.id.tv_icon_status)
    IconTextView tvStatus = null;

    @OnClick(R2.id.button_test)
    void onClickTestButton(){
        tvStatus.setText("{fa-check-circle}");
        tvStatus.setTextColor(Color.GREEN);
        Toast.makeText(_mActivity, "Connection is OK!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_connect;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
