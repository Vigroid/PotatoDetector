package me.vigroid.potato.impl.main.enemy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
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
import me.vigroid.potato.core.recycler.BaseDecoration;
import me.vigroid.potato.core.recycler.PlayerAdapter;
import me.vigroid.potato.core.recycler.PlayerBean;
import me.vigroid.potato.core.recycler.PlayerComparator;
import me.vigroid.potato.core.recycler.PlayerDataConverter;
import me.vigroid.potato.core.recycler.PlayerReversedComparator;
import me.vigroid.potato.core.util.preference.PotatoPreference;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/16/17.
 * Display for enemy information
 */

public class EnemyDelegate extends BottomItemDelegate {

    boolean isDescendant = false;

    PlayerAdapter mAdapter = null;

    @BindView(R2.id.rv_team)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.srl_enemy)
    SwipeRefreshLayout mSrl = null;

    @OnClick(R2.id.fab_enemy)
    void onClickFabEnemy() {
        if (mAdapter != null) {
            final ArrayList<PlayerBean> enemyList = new ArrayList<>();
            enemyList.addAll((List<PlayerBean>) Potato.getConfiguration(ConfigKeys.ENEMY));
            if (!isDescendant) {
                Collections.sort(enemyList, new PlayerReversedComparator());
                mAdapter.refresh(enemyList);
                isDescendant = true;
            } else {
                Collections.sort(enemyList, new PlayerComparator());
                mAdapter.refresh(enemyList);
                isDescendant = false;
            }

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_enemy;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (Potato.getConfiguration(ConfigKeys.ENEMY_UI_UPDATED)) {
            onLazyInitView(null);
            Configurator.getInstance().withEnemyUiUpdate(false);
        }
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mSrl.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSrl.setProgressViewOffset(true, 120, 300);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RestClient.builder()
                        .url(RestUrl.REST_URL)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {

                                Toast.makeText(_mActivity, "Refreshed!", Toast.LENGTH_SHORT).show();
                                HashMap<String, ArrayList<PlayerBean>> resultMap = new PlayerDataConverter().setJsonData(response).convert();

                                if (!resultMap.get("team").isEmpty() && !resultMap.get("enemy").isEmpty()) {
                                    Configurator.getInstance().withConnectionStatus(true)
                                            .withTeamUiUpdate(true)
                                            .withBackGroundColor(Color.GREEN);

                                    ArrayList<PlayerBean> team = Potato.getConfiguration(ConfigKeys.TEAM);
                                    team.clear();
                                    team.addAll(resultMap.get("team"));
                                    mAdapter.refresh(resultMap.get("enemy"));
                                }
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFailure() {
                                Toast.makeText(_mActivity, "Refreshing Failed! Please check your connection!", Toast.LENGTH_SHORT).show();
                                Configurator.getInstance().withConnectionStatus(false)
                                        .withBackGroundColor(Color.RED)
                                        .withConUiUpdate(true);
                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError(int code, String msg) {
                                Toast.makeText(_mActivity, "Refreshing Failed! Connection Error!\n" + code + msg, Toast.LENGTH_SHORT).show();
                                Configurator.getInstance().withConnectionStatus(false)
                                        .withBackGroundColor(Color.YELLOW)
                                        .withConUiUpdate(true);
                            }
                        })
                        .build()
                        .get();


                mSrl.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        List<PlayerBean> beans = Potato.getConfiguration(ConfigKeys.ENEMY);


        initRecyclerView();

        mAdapter = new PlayerAdapter(beans, getContext());

        //Log.i("yoo",Boolean.toString(PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name())));

        if (PotatoPreference.getAppFlagAnimation(SavedStates.ENABLE_ANIMATION.name())) {

            //Animation related code
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
            scaleAdapter.setFirstOnly(false);
            scaleAdapter.setDuration(800);
            scaleAdapter.setInterpolator(new OvershootInterpolator(.5f));

            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(scaleAdapter);
            alphaAdapter.setFirstOnly(false);
            alphaAdapter.setDuration(800);
            alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));

            mRecyclerView.setAdapter(alphaAdapter);
        } else {
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
