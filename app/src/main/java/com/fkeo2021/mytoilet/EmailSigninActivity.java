package com.fkeo2021.mytoilet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EmailSigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_signin);

        //툴바에 업버튼 보이기
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

    }

    //업버튼 눌렀을 때 액티비티 종료
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void clickSignIn(View view) {

        EditText etmail= findViewById(R.id.et_email);
        EditText etPassword= findViewById(R.id.et_password);

        String email= etmail.getText().toString();
        String password= etPassword.getText().toString();

        //Firebase Firestore DB에서 이메일과 비밀번호 확인
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("emailUsers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(DocumentSnapshot document : queryDocumentSnapshots) {
                    if(email.equals(document.getData().get("email").toString() ) ){
                        if(password.equals(document.getData().get("password").toString()) ){
                            //로그인 성공.!!
                            String id= document.getId();
                            G.user= new UserAccount(id, email);

                            //Main화면으로 이동
                            Intent intent= new Intent(EmailSigninActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            return;
                        }else{
                            break;
                        }
                    }
                }

                //로그인 실패
                new AlertDialog.Builder(EmailSigninActivity.this).setMessage("이메일과 비밀번호를 다시 확인해주시기 바랍니다.").show();
                etmail.requestFocus();
                etmail.selectAll();


            }
        });

    }
}