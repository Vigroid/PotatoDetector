package me.vigroid.potato.core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by vigroid on 10/12/17.
 * We pass and set the configurations in here, it follows the principle of builder design pattern
 * It also uses "Holder" and private constructor(singleton)indicate it is thread safe
 */

public class Configurator {
    //config info
    private static final HashMap<Object,Object> POTATO_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator(){
        //the default setting indicate the config is not ready
        POTATO_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        POTATO_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    //Holder inner class
    private static class Holder{
        //The address for this ref in heap is fixed due to the "final" keyword
        private static final Configurator INSTANCE = new Configurator();
    }

    //Get the singleton instance
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    //Get the configuration hashmap
    final HashMap<Object,Object> getPotatoConfigs(){
        return POTATO_CONFIGS;
    }

    private void initIcons(){
        if (ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    //set host IP
    public final Configurator withApiHost(String host){
        POTATO_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    //Pass some IconFontDescriptor to add support for those icons
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    //Pass some Interceptor(Network)
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        POTATO_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        POTATO_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withActivity(Activity activity){
        POTATO_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public final Configurator withRegion(PlayerRegion  region){
        POTATO_CONFIGS.put(ConfigKeys.PLAYER_REGION, region);
        return this;
    }

    public final void configure(){
        initIcons();
        //set the configure to ready state
        POTATO_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean) POTATO_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady){
            throw new RuntimeException("The configuration is not ready!");
        }
    }

    //get each configuration detail
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        //get the configuration value by key
        final Object value = POTATO_CONFIGS.get(key);
        if (value == null){
            throw new NullPointerException(key.toString() + ": This configuration is null");
        }
        return (T) value;
    }
}
