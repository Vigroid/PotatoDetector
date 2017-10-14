package me.vigroid.potato.impl.main.teammates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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


    private final String NAME = "abc";

    @Override
    public Object setLayout() {
        return R.layout.delegate_teammate;
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(
                BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
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
                        int debug = beans.size();
                        mRecyclerView.setAdapter(new PlayerAdapter(beans));
                    }
                })
                .build()
                .get();
    }
}
