package com.i4vine.ryufragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterfaceDust {
    @GET("feed/geo:{geo}/?token=EditYourKey")
    Call<ResponseAqi> getAQI(@Path("geo") String location);

}
