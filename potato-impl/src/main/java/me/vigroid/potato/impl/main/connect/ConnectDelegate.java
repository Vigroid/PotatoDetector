package me.vigroid.potato.impl.main.connect;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Configurator;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.net.RestClient;
import me.vigroid.potato.core.net.callback.IError;
import me.vigroid.potato.core.net.callback.IFailure;
import me.vigroid.potato.core.net.callback.ISuccess;
import me.vigroid.potato.core.recycler.PlayerBean;
import me.vigroid.potato.core.recycler.PlayerDataConverter;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/13/17.
 * Connect fragment
 */

public class ConnectDelegate extends BottomItemDelegate {

    @BindView(R2.id.tv_icon_status)
    IconTextView tvStatus = null;

    @BindView(R2.id.region_spinner)
    Spinner spinner = null;

    @BindView(R2.id.connect_upper_tag)
    TextView tvUpper = null;

    @BindView(R2.id.connect_lower_tag)
    TextView tvLower = null;

    @OnClick(R2.id.button_connect)
    void onClickConnectButton() {
        RestClient.builder()
                .url("player_info.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Configurator.getInstance().withConnectionStatus(true);
                        Configurator.getInstance().withBackGroundColor(Color.GREEN);
                        setBackgroundColor(true, Color.GREEN);
                        Toast.makeText(_mActivity, "Connected!", Toast.LENGTH_SHORT).show();
                        PlayerDataConverter converter = new PlayerDataConverter();
                        converter.setJsonData(response);
                        HashMap<String,ArrayList<PlayerBean>> resultMap = converter.convert();
                        Configurator.getInstance().withTeamBeans(resultMap.get("team"));
                        Configurator.getInstance().withEnemyBeans(resultMap.get("enemy"));
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Configurator.getInstance().withConnectionStatus(false);
                        Configurator.getInstance().withBackGroundColor(Color.RED);
                        setBackgroundColor(false, Color.RED);
                        Toast.makeText(_mActivity, "Connection Failed! Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Configurator.getInstance().withConnectionStatus(false);
                        Configurator.getInstance().withBackGroundColor(Color.YELLOW);
                        setBackgroundColor(false, Color.YELLOW);
                        Toast.makeText(_mActivity, "Connection Error!\n"+ code + msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }

    @OnClick(R2.id.button_test)
    void onClickTestButton() {
        RestClient.builder()
                .url("player_infosa.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Configurator.getInstance().withConnectionStatus(true);
                        Configurator.getInstance().withBackGroundColor(Color.GREEN);
                        setBackgroundColor(true, Color.GREEN);
                        Toast.makeText(_mActivity, "Connected!", Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Configurator.getInstance().withConnectionStatus(false);
                        Configurator.getInstance().withBackGroundColor(Color.RED);
                        setBackgroundColor(false, Color.RED);
                        Toast.makeText(_mActivity, "Connection Failed! Please check your connection!", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Configurator.getInstance().withConnectionStatus(false);
                        Configurator.getInstance().withBackGroundColor(Color.YELLOW);
                        setBackgroundColor(false, Color.YELLOW);
                        Toast.makeText(_mActivity, "Connection Error!\n"+ code + msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_connect;
    }

    private void init() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.region_array, R.layout.item_spinner);
        spinner.setAdapter(adapter);

        setBackgroundColor((boolean)Potato.getConfiguration(ConfigKeys.CONNECTED), (int)Potato.getConfiguration(ConfigKeys.BACKGND_COLOR));
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        init();
    }

    private void setBackgroundColor(boolean isConnected, @ColorInt int color) {
        tvUpper.setBackgroundColor(color);
        tvLower.setBackgroundColor(color);
        if (isConnected) {
            tvStatus.setText("{fa-check-circle}");
            tvStatus.setTextColor(Color.GREEN);
        } else {
            tvStatus.setText("{fa-exclamation-triangle}");
            tvStatus.setTextColor(Color.RED);
        }
    }
}
