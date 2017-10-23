package me.vigroid.potato.impl.main.setting;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Configurator;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.app.SavedStates;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.net.RestClient;
import me.vigroid.potato.core.net.RestUrl;
import me.vigroid.potato.core.net.callback.IError;
import me.vigroid.potato.core.net.callback.IFailure;
import me.vigroid.potato.core.net.callback.ISuccess;
import me.vigroid.potato.core.util.preference.PotatoPreference;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/16/17.
 * settings page
 */

public class SettingDelegate extends BottomItemDelegate {

    @BindView(R2.id.git_link)
    TextView mTvLink = null;

    @BindView(R2.id.tv_icon_status)
    IconTextView tvStatus = null;

    @OnClick(R2.id.git_link_view)
    void onClickGitLink(){
        final String url = mTvLink.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        _mActivity.startActivity(i);
    }

    @BindView(R2.id.spinner_lang)
    Spinner langSpinner = null;

    @BindView(R2.id.toggle_animation)
    ToggleButton toggleBtn = null;

    @OnCheckedChanged(R2.id.toggle_animation)
    void onToggleAnimation(boolean isChecked){
        PotatoPreference.setAppFlag(SavedStates.ENABLE_ANIMATION.name(),isChecked);
        Configurator.getInstance()
                .withEnemyUiUpdate(true)
                .withTeamUiUpdate(true);
    }

    //TODO easter egg
    @OnClick(R2.id.easter_egg)
    void onEasterEgg(){
        Toast.makeText(_mActivity, "Poi~", Toast.LENGTH_SHORT).show();

    }
    @OnClick(R2.id.button_test)
    void onClickTestButton() {
        String apiHost = Potato.getConfiguration(ConfigKeys.API_HOST);
        Log.i("yo", apiHost);
        if (apiHost != null && !apiHost.isEmpty()) {
            RestClient.builder()
                    .url(RestUrl.REST_URL)
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            Toast.makeText(_mActivity, "Connected!", Toast.LENGTH_SHORT).show();
                            Configurator.getInstance().withConnectionStatus(true)
                                    .withBackGroundColor(Color.GREEN);
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
            Toast.makeText(_mActivity, "Please input valid IP and port first.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array, R.layout.item_spinner);
        langSpinner.setAdapter(adapter);
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PotatoPreference.addCustomInt(SavedStates.LANGUAGE.name(), (int)l);
                if (l>0) {
                    Toast.makeText(_mActivity, "Sorry...Language switch under development!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PotatoPreference.addCustomInt(SavedStates.LANGUAGE.name(), 0);
            }
        });
        langSpinner.setSelection( PotatoPreference.getCustomInt(SavedStates.LANGUAGE.name()));
        toggleBtn.setChecked(PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name()));
    }

    private void setBackgroundColor() {

        if (Potato.getConfiguration(ConfigKeys.CONNECTED)) {
            tvStatus.setText("{fa-check-circle}");
            tvStatus.setTextColor(Color.GREEN);
        } else {
            tvStatus.setText("{fa-exclamation-triangle}");
            tvStatus.setTextColor(Color.RED);
        }
    }
}
