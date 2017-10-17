package me.vigroid.potato.impl.main.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.app.SavedStates;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.net.RestClient;
import me.vigroid.potato.core.net.callback.ISuccess;
import me.vigroid.potato.core.recycler.BaseDecoration;
import me.vigroid.potato.core.recycler.PlayerAdapter;
import me.vigroid.potato.core.recycler.PlayerBean;
import me.vigroid.potato.core.recycler.PlayerDataConverter;
import me.vigroid.potato.core.util.preference.PotatoPreference;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/13/17.
 * Teammate fragment
 */

public class TeamDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_team)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_teammate;
    }

    private void initRecyclerView() {

        mRecyclerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 15));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        List<PlayerBean> beans =Potato.getConfiguration(ConfigKeys.TEAM);

        if (beans!=null && beans.size()>0) {
            initRecyclerView();

            PlayerAdapter adapter = new PlayerAdapter(beans, getContext());

            //Log.i("yoo",Boolean.toString(PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name())));

            if (PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name())) {

                //Animation related code
                ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
                scaleAdapter.setFirstOnly(false);
                scaleAdapter.setDuration(800);
                scaleAdapter.setInterpolator(new OvershootInterpolator(.5f));

                AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(scaleAdapter);
                alphaAdapter.setFirstOnly(false);
                alphaAdapter.setDuration(800);
                alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));
                mRecyclerView.setAdapter(alphaAdapter);
            } else {
                mRecyclerView.setAdapter(adapter);
            }
        } else {
            Toast.makeText(_mActivity, "No contents!", Toast.LENGTH_SHORT).show();
        }
    }
}

