package me.vigroid.potato.core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import me.vigroid.potato.core.R;
import me.vigroid.potato.core.delegates.PotatoDelegate;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by vigroid on 10/12/17.
 * We use One activity - multi fragment structure
 * This activity will be the base class for the placeholder activity
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract PotatoDelegate setRootDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        //set up a ID to represent this proxy activity
        container.setId(R.id.delegate_container);

        setContentView(container);
        if (savedInstanceState == null) {
            //load the root fragment into our container
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

}
