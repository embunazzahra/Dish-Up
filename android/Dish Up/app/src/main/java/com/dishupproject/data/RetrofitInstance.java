package com.dishupproject.data;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;

    /**
     * Method for creating a retrofit instance
     * with localhost base url.
     *
     * @return a Retrofit instance.
     */
    public static Retrofit getInstance(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:5678")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
