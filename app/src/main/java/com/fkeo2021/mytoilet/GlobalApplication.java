package com.fkeo2021.mytoilet;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //카카오 SDK의 초기화 설정
        KakaoSdk.init(this,"1a784411cafeed2fc84728c0a9dc747e");
    }
}
