package me.vigroid.potatodetector;

import android.app.Application;
import android.widget.Toast;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import me.vigroid.potato.core.app.Potato;

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
                .withApiHost("http://127.0.0.1:8080")
                .configure();
    }
}


