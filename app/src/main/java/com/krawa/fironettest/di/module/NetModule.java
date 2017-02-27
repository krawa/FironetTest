package com.krawa.fironettest.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krawa.fironettest.Constants;
import com.krawa.fironettest.di.scope.PerApplication;
import com.krawa.fironettest.network.MapsAPI;
import com.krawa.fironettest.network.RestAPI;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private static final String TAG = "NetModule";

    @Provides
    @PerApplication
    MapsAPI provideMapsAPI(OkHttpClient okHttpClient, Gson gson){
        return provideRetrofit(MapsAPI.BASE_URL, okHttpClient, gson).create(MapsAPI.class);
    }

    @Provides
    @PerApplication
    RestAPI provideRestAPI(OkHttpClient okHttpClient, Gson gson){
        return provideRetrofit(RestAPI.BASE_URL, okHttpClient, gson).create(RestAPI.class);
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(String baseUrl, OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if(Constants.IS_DEBUG){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }
        return clientBuilder.build();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new GsonBuilder().create();
    }

}
