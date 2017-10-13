package me.vigroid.potatodetector;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.vigroid.potato.core.activities.ProxyActivity;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.delegates.PotatoDelegate;
import me.vigroid.potato.impl.main.PotatoBottomDelegate;
import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        Potato.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public PotatoDelegate setRootDelegate() {
        return new PotatoBottomDelegate();
    }

}
