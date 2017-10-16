package me.vigroid.potatodetector;

import android.app.Application;
import android.graphics.Color;
import android.widget.Toast;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import me.vigroid.potato.core.app.PlayerRegion;
import me.vigroid.potato.core.app.Potato;
import me.vigroid.potato.core.net.interceptors.DebugInterceptor;

/**
 * Created by vigroid on 10/12/17.
 * App entrance
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Potato.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://192.168.1.37:8080/RestServer/api/")
                .withInterceptor(new DebugInterceptor("haha",R.raw.test))
                .withRegion(PlayerRegion.NA)
                .withBackGroundColor(Color.GRAY)
                .configure();
    }
}


