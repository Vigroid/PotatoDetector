package me.vigroid.potato.impl.main.teammates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import me.vigroid.potato.core.delegates.bottonTab.BottomItemDelegate;
import me.vigroid.potato.core.net.RestClient;
import me.vigroid.potato.core.net.callback.ISuccess;
import me.vigroid.potato.core.recycler.BaseDecoration;
import me.vigroid.potato.core.recycler.PlayerAdapter;
import me.vigroid.potato.core.recycler.PlayerBean;
import me.vigroid.potato.core.recycler.PlayerDataConverter;
import me.vigroid.potato.impl.R;
import me.vigroid.potato.impl.R2;

/**
 * Created by vigroid on 10/13/17.
 * Teammate fragment
 */

public class TeamDelegate extends BottomItemDelegate{

    @BindView(R2.id.rv_team)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_teammate;
    }

    private void initRecyclerView() {

        mRecyclerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),15));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);


        initRecyclerView();

        RestClient.builder()
                .url("player_info.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        PlayerDataConverter converter = new PlayerDataConverter();
                        converter.setJsonData(response);
                        List<PlayerBean> beans = converter.convert();
                        PlayerAdapter adapter = new PlayerAdapter(beans, getContext());

                        //Animation related code
                        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
                        scaleAdapter.setFirstOnly(false);
                        scaleAdapter.setDuration(500);
                        scaleAdapter.setInterpolator(new OvershootInterpolator(.5f));

                        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(scaleAdapter);
                        alphaAdapter.setFirstOnly(false);
                        alphaAdapter.setDuration(1000);
                        alphaAdapter.setInterpolator(new OvershootInterpolator(.5f));

                        mRecyclerView.setAdapter(alphaAdapter);
                    }
                })
                .build()
                .get();
    }
}
