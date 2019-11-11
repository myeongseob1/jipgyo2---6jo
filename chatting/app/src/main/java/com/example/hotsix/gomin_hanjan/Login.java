package com.example.hotsix.gomin_hanjan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hwan.chatting.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    private static final String BASE = "http://192.168.0.18:3000";

    EditText position;
    Button getButton, button_developer;
    String info_id, info_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText id = (EditText) findViewById(R.id.edittext_id);
        final EditText password = (EditText) findViewById(R.id.edittext_password);
        Button login = (Button) findViewById(R.id.button_login);
        Button signup = (Button) findViewById(R.id.button_signup);
        Button button_developer = (Button) findViewById(R.id.button_developer);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GetService service = retrofit.create(GetService.class);
                String id1 = String.valueOf(id.getText().toString());
                String password1 = String.valueOf(password.getText().toString());
                info_id=id1;
                info_password=password1;
                Call<List<Dummy>> call = service.listDummies(id1, password1);
                call.enqueue(dummies);
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivityForResult(intent,100);
            }
        });

        button_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChattingRoom.class);
                String[] information = new String[] {"admin", "admin"};
                intent.putExtra("strings", information);
                startActivityForResult(intent,100);
            }
        });
    }

    Callback dummies = new Callback<List<Dummy>>() {

        @Override
        public void onResponse(Call<List<Dummy>> call, Response<List<Dummy>> response) {
            if (response.isSuccessful()) {
                List<Dummy> dummies = response.body();
                StringBuilder builder = new StringBuilder();
                for (Dummy dummy : dummies) {
                    builder.append(dummy.toString());
                }
                if(builder.toString().equals("1")){
                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),ChattingRoom.class);
                    String[] information = new String[] {info_id, info_password};
                    intent.putExtra("strings", information);
                    startActivityForResult(intent,100);
                }
                else if(builder.toString().equals("2")){
                    Toast.makeText(getApplicationContext(), "ID, Password를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("3")){
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if(builder.toString().equals("4")){
                    Toast.makeText(getApplicationContext(), "Password를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "회원가입을 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<List<Dummy>> call, Throwable t) {

        }
    };

}
