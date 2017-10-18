package me.vigroid.potato.impl.main.setting;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import me.vigroid.potato.core.app.SavedStates;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
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
        //Log.i("yoo",Boolean.toString(PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name())));
    }

    //TODO easter egg
    @OnClick(R2.id.easter_egg)
    void onEasterEgg(){
        Toast.makeText(_mActivity, "nanodesu~", Toast.LENGTH_SHORT).show();

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PotatoPreference.addCustomInt(SavedStates.LANGUAGE.name(), 0);
            }
        });
        langSpinner.setSelection( PotatoPreference.getCustomInt(SavedStates.LANGUAGE.name()));
        toggleBtn.setChecked(PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name()));
    }
}
