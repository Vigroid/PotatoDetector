package me.vigroid.potato.impl.main.connect;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Configurator;
import me.vigroid.potato.core.app.PlayerRegion;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.app.SavedStates;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.net.RestClient;
import me.vigroid.potato.core.net.callback.IError;
import me.vigroid.potato.core.net.callback.IFailure;
import me.vigroid.potato.core.net.callback.ISuccess;
import me.vigroid.potato.core.recycler.PlayerBean;
import me.vigroid.potato.core.recycler.PlayerDataConverter;
import me.vigroid.potato.core.util.IpAddressChecker;
import me.vigroid.potato.core.util.callback.CallbackManager;
import me.vigroid.potato.core.util.callback.CallbackType;
import me.vigroid.potato.core.util.callback.IGlobalCallback;
import me.vigroid.potato.core.util.preference.PotatoPreference;
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

    @BindView(R2.id.edit_server_ip)
    TextInputEditText etIp = null;

    @BindView(R2.id.edit_port)
    TextInputEditText etPort = null;

    @OnClick(R2.id.button_connect)
    void onClickConnectButton() {
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();

        if (checkAndSetInput(ip, port)) {

            RestClient.builder()
                    //TODO add not hard coded url
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
                            HashMap<String, ArrayList<PlayerBean>> resultMap = converter.convert();
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
                            Toast.makeText(_mActivity, "Connection Error!\n" + code + msg, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .get();
        } else {
            Toast.makeText(_mActivity, "Sorry! Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.button_test)
    void onClickTestButton() {
        String apiHost = Potato.getConfiguration(ConfigKeys.API_HOST);
        Log.i("yo", apiHost);
        if (apiHost != null && !apiHost.isEmpty()) {
            RestClient.builder()
                    .url("player_info.php")
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
                            Toast.makeText(_mActivity, "Connection Error!\n" + code + msg, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .get();
        } else {
            Toast.makeText(_mActivity, "Please input valid IP and port first.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.button_QR_connect)
    void onClickScanQr() {
        startScanWithCheck(this.getParentDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_connect;
    }

    private void init() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.region_array, R.layout.item_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPreferences(spinner.getSelectedItem().toString(), (int) spinner.getSelectedItemId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setPreferences(PlayerRegion.ASIA.name(), 0);
            }
        });
        spinner.setSelection(PotatoPreference.getCustomInt(SavedStates.PLAYER_REGION_INDEX.name()));
        setBackgroundColor((boolean) Potato.getConfiguration(ConfigKeys.CONNECTED), (int) Potato.getConfiguration(ConfigKeys.BACKGND_COLOR));

        etIp.setText((String) Potato.getConfiguration(ConfigKeys.IP));
        etPort.setText((String) Potato.getConfiguration(ConfigKeys.PORT));
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        Toast.makeText(_mActivity, args, Toast.LENGTH_LONG).show();
                        String[] address = args.split(":");
                        if (checkAndSetInput(address[0], address[1])) {
                            etIp.setText((String) Potato.getConfiguration(ConfigKeys.IP));
                            etPort.setText((String) Potato.getConfiguration(ConfigKeys.PORT));
                        } else {
                            Toast.makeText(_mActivity, "Invalid QR code", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        init();
    }

    private void setPreferences(String region, int regionIndex) {
        PotatoPreference.addCustomString(SavedStates.PLAYER_REGION.name(), region);
        PotatoPreference.addCustomInt(SavedStates.PLAYER_REGION_INDEX.name(), regionIndex);
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

    private boolean checkAndSetInput(String ip, String port) {

        if (ip.isEmpty()) {
            etIp.setError("Empty IP address");
            return false;
        }
        if (port.isEmpty()) {
            etPort.setError("Empty port number");
            return false;
        }

        if (!IpAddressChecker.isLegalIp(ip)) {
            etIp.setError("Illegal IP address");
            return false;
        }

        int portNum = Integer.parseInt(port);

        //My rest server will allocate a random port number between 8000 and 10000 each time
        if (portNum > 10000 || portNum < 8000) {
            etPort.setError("Port number should between 8000 and 10000");
            return false;
        }

        final int max_host_string_size = 50;

        StringBuilder sb = new StringBuilder(max_host_string_size);

        sb.append("http://");
        sb.append(ip);
        sb.append(":");
        sb.append(port);
        sb.append("/RestServer/api/");

        Log.i("yo", sb.toString());

        Configurator.getInstance().withApiHost(sb.toString());
        Configurator.getInstance().withIP(ip);
        Configurator.getInstance().withPort(port);

        return true;
    }
}
