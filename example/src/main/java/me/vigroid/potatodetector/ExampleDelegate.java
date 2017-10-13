package me.vigroid.potatodetector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.vigroid.potato.core.delegates.PotatoDelegate;

/**
 * Created by vigroid on 10/12/17.
 * A exmple debug delegate, just for testing
 */

public class ExampleDelegate extends PotatoDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
