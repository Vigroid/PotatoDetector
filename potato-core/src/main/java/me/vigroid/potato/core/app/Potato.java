package me.vigroid.potato.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * Created by vigroid on 10/12/17.
 * A class to get access to configs
 */

public final class Potato {

    //initialize the configuration
    public static Configurator init(Context context){
        //set the application context
        getConfigurator()
                .getPotatoConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigKeys.HANDLER);
    }
}
