package com.i4vine.ryufragment;

import com.google.gson.annotations.SerializedName;

public class ResponseUV {
    public class UVData{
        double uv;
        String uv_time;
        String uv_max;
        String uv_max_time;
    }

    @SerializedName("result")
    private UVData data;

    public ResponseUV(UVData data){
        this.data = data;
    }

    public Double getUvValue() { return data.uv; }
    public String getUvTime() { return data.uv_time; }
    public String getUvMax() { return data.uv_max; }
    public String getUvMax_time() { return data.uv_max_time; }
}

