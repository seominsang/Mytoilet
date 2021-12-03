package com.fkeo2021.mytoilet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.nhn.android.naverlogin.OAuthLogin;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //카카오 키해시 를 얻어오기
        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.i("keyhash",keyHash);
    }

    public void clickgo(View view) {
        //메인 엑티비티로 이동
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void clickSignUp(View view) {
        //회원가입 화면(액티비티)로 이동
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void clickeMailLogin(View view) {
        //이메일 로그인화면(액티비티)로 이동
        startActivity(new Intent(this,EmailSigninActivity.class));
    }

    public void clickLoginKakao(View view) {
        //카카오 계정 로그인 요청
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken!=null){
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    // 로그인 정보 얻어오기기
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if(user!=null){
                                String userid= user.getId()+"";
                                String email= user.getKakaoAccount().getEmail();

                                //다른 액티비티에서 마음대로 쓸 수 있도록..
                                G.user= new UserAccount(userid, email);

                             //   new AlertDialog.Builder(LoginActivity.this).setMessage(email+"").show();
                             //Main 화면 이동
                             startActivity( new Intent(LoginActivity.this, MainActivity.class));
                             finish();
                            }
                            return null;
                        }
                    });
                }
               return null;
            }
        });
    }

    public void clickLoginGoogle(View view) {
        //구글로 로그인 , 구글 개발자 콘솔에서 사용자 인증 키 등록을 해야됨 -oAuth [패키지명, SHA-1등록]
        //구글 로그인 기능 SDK 라이브러리 추가 : play-services-auth

        //구글 로그인으로 받아올 정보 옵션 [ id, email 요청]
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestId().requestEmail().build();

        GoogleSignInClient googleClient= GoogleSignIn.getClient(this, gso);
        //구글 로그인 화면 (액티비티)를 실행하는 Intent 객체
        Intent intent= googleClient.getSignInIntent();
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            try {
              GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                
                if(account!=null){
                    Toast.makeText(this, "로그인성공", Toast.LENGTH_SHORT).show();

                    String userid= account.getId();
                    String email= account.getEmail();

                    // new AlertDialog.Builder(LoginActivity.this).setMessage(email+"").show();
                    G.user= new UserAccount(userid, email);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickLoginNaver(View view) {

        new AlertDialog.Builder(this).setMessage("최송합니다. 네이버 서버 버전문제로 로그인 기능이 불가합니다. 다른 로그인방식을 선택해 주시기 바랍니다.").show();

        //주의! 네이버 로그인은 현재는 (2021.11.18) 타겟버전이 30버전 까지 가능함

        //1.네이버 로그인 인스턴스 초기화
//        OAuthLogin oAuthLogin= OAuthLogin.getInstance();
//        oAuthLogin.init(this,"","","");

    }
}