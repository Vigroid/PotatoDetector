package me.vigroid.potato.core.net;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import me.vigroid.potato.core.app.ConfigKeys;
import me.vigroid.potato.core.app.Potato;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by vigroid on 10/13/17.
 * This class will create concrete restservice class
 */

public class RestCreator {

    //Holder and getter for parameters
    private static final class ParamsHolder {
        public static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    //Holder for Retrofit, build from host address and okhttpclient
    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Potato.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    //Holder for okHttp, build from builder
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Potato.getConfiguration(ConfigKeys.INTERCEPTOR);

        //add our interceptor to the okhttpclient
        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    //Holder for RestService
    private static final class RestServiceHolder {
        //create RestService concrete class with Retro_client
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    //get our generated concrete restservice
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }
}
