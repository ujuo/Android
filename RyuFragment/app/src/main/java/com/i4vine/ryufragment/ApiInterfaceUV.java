package com.i4vine.ryufragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterfaceUV {
    @GET("api/v1/uv?")
    Call<ResponseUV> getUV(@Query("lat") double lat, @Query("lng") double lng, @Query("dt") String dt, @Header("x-access-token") String token);

}
