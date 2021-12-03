package com.fkeo2021.mytoilet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlaceUrlActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_url);

        //이 액티비티를 실행해준 택배기사님(Intent)에게 가지고 온 추가데이터를 얻어오기(택배받기)
        String placeUrl= getIntent().getStringExtra("place_url");

        wv= findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient());   //웹문서안에서 경고창 띄우는기술
        
        wv.getSettings().setJavaScriptEnabled(true);    //자바스크립트 허용 (이거 써줘야 자바스크립작동됨)
        wv.getSettings().setAllowFileAccess(true);      //ajax실행 허용

        //웹뷰에게 장소정보 url을 읽어서 보여주도록
        wv.loadUrl(placeUrl);
    }

        //뒤로가기 버튼 눌렀을 때 발동하는 메소드
    @Override
    public void onBackPressed() {
        if(wv.canGoBack()) wv.goBack();
        else super.onBackPressed();
    }
}