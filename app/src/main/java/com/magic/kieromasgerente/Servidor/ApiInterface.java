package com.magic.kieromasgerente.Servidor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("terminalLocales.php")
    Call<List<Retrofit2>> getDatos(@Query("actividad") String actividad, @Query("data01") String data01, @Query("data02") String data02,
                                   @Query("data03") String data03, @Query("data04") String data04, @Query("data05") String data05,
                                   @Query("data06") String data06);
}
