package com.fkeo2021.mytoilet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {

    @Headers("Authorization: KakaoAK 9d0755b88c293e4ff56d9329d6db0b7f")
    @GET("/v2/local/search/keyword.json")
        //일단 json을 글씨로라도 보기, searchPlaceByString 메소드를 호출하면 retrofit이 써야할 글을 대신써줌
    Call<String> searchPlaceByString(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);


    @Headers("Authorization: KakaoAK 9d0755b88c293e4ff56d9329d6db0b7f")
    @GET("/v2/local/search/keyword.json")
    Call<SearchLocalApiResponse> searchPlace(@Query("query") String query, @Query("x") String longitude, @Query("y") String latitude);



}
