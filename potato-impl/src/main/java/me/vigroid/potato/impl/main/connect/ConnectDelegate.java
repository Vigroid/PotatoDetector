package me.vigroid.potato.impl.main.connect;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import me.vigroid.potato.core.net.RestUrl;
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

    @BindView(R2.id.region_spinner)
    Spinner spinner = null;

    @BindView(R2.id.connect_upper_tag)
    TextView tvUpper = null;

    @BindView(R2.id.edit_server_ip)
    TextInputEditText etIp = null;

    @BindView(R2.id.edit_port)
    TextInputEditText etPort = null;

    @OnClick(R2.id.button_connect)
    void onClickConnectButton() {
        String ip = etIp.getText().toString();
        String port = etPort.getText().toString();

        if (_mActivity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) _mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(_mActivity.getCurrentFocus().getWindowToken(), 0);
        }

        if (checkAndSetInput(ip, port)) {

            RestClient.builder()
                    .url(RestUrl.REST_URL)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            HashMap<String, ArrayList<PlayerBean>> resultMap = new PlayerDataConverter().setJsonData(response).convert();
                            if (!resultMap.get("team").isEmpty() && !resultMap.get("enemy").isEmpty()) {
                                Toast.makeText(_mActivity, "Connected!", Toast.LENGTH_SHORT).show();
                                Configurator.getInstance().withTeamBeans(resultMap.get("team"))
                                        .withEnemyBeans(resultMap.get("enemy"))
                                        .withConnectionStatus(true)
                                        .withBackGroundColor(Color.GREEN)
                                        .withTeamUiUpdate(true)
                                        .withEnemyUiUpdate(true);
                            }else {
                                Toast.makeText(_mActivity, "Connected but no contents!", Toast.LENGTH_SHORT).show();
                                Configurator.getInstance().withConnectionStatus(true)
                                        .withBackGroundColor(Color.GREEN);
                            }
                            setBackgroundColor();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(_mActivity, "Connection Failed! Please check your connection!", Toast.LENGTH_SHORT).show();
                            Configurator.getInstance().withConnectionStatus(false)
                                    .withBackGroundColor(Color.RED);
                            setBackgroundColor();
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            Toast.makeText(_mActivity, "Connection Error!\n" + code + msg, Toast.LENGTH_SHORT).show();
                            Configurator.getInstance().withConnectionStatus(false)
                                    .withBackGroundColor(Color.YELLOW);
                            setBackgroundColor();
                        }
                    })
                    .build()
                    .get();
        } else {
            Toast.makeText(_mActivity, "Sorry! Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    /*

    }*/

    @OnClick(R2.id.button_QR_connect)
    void onClickScanQr() {
        if (_mActivity.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) _mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(_mActivity.getCurrentFocus().getWindowToken(), 0);
        }
        startScanWithCheck(this.getParentDelegate());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (Potato.getConfiguration(ConfigKeys.CONNECT_UI_UPDATED)){
            setBackgroundColor();
            Configurator.getInstance().withConUiUpdate(false);
        }
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
        setBackgroundColor();

        etIp.setText((String) Potato.getConfiguration(ConfigKeys.IP));
        etPort.setText((String) Potato.getConfiguration(ConfigKeys.PORT));
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        if (args == null && args.isEmpty() && !args.contains(":")) {
                            Toast.makeText(_mActivity, "No QR code readed!", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] address = args.split(":");
                            if (address.length == 2) {
                                if (checkAndSetInput(address[0], address[1])) {
                                    Toast.makeText(_mActivity, args, Toast.LENGTH_LONG).show();
                                    etIp.setText((String) Potato.getConfiguration(ConfigKeys.IP));
                                    etPort.setText((String) Potato.getConfiguration(ConfigKeys.PORT));
                                    onClickConnectButton();
                                } else {
                                    Toast.makeText(_mActivity, "Invalid QR code", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(_mActivity, "Invalid QR code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        init();
    }

    private void setPreferences(String region, int regionIndex) {
        PotatoPreference.addCustomString(SavedStates.PLAYER_REGION.name(), region);
        PotatoPreference.addCustomInt(SavedStates.PLAYER_REGION_INDEX.name(), regionIndex);
    }

    private void setBackgroundColor() {
        tvUpper.setBackgroundColor((int) Potato.getConfiguration(ConfigKeys.BACKGND_COLOR));
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

        int portNum = 0;
        try {
            portNum = Integer.parseInt(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //My rest server will allocate a random port number between 8000 and 10000 each time
        if (portNum > 65535 || portNum < 1) {
            etPort.setError("Illegal port number");
            return false;
        }

        final int max_host_string_size = 50;

        StringBuilder sb = new StringBuilder(max_host_string_size);

        sb.append("http://");
        sb.append(ip);
        sb.append(":");
        sb.append(port);
        sb.append("/");

       // Log.i("yo", sb.toString());

        Configurator.getInstance().withApiHost(sb.toString());
        Configurator.getInstance().withIP(ip);
        Configurator.getInstance().withPort(port);

        etIp.setError(null);
        etPort.setError(null);

        return true;
    }
}
