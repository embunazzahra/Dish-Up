package com.dishupproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;
import com.dishupproject.data.model.DefaultResponse;
import com.dishupproject.data.model.User;
import com.dishupproject.data.services.RetrofitServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is class for register activity.
 *
 * @author Dhau' Embun Azzahra
 */
public class RegisterActivity extends AppCompatActivity {
    private RetrofitServices retrofitServices;
    /**
     * Form for user's password.
     */
    private EditText etPassword;
    /**
     * Form for user's email.
     */
    private EditText etEmail;
    /**
     * Form for user's username.
     */
    private EditText etUsername;
    /**
     * Button for moving to Login Activity.
     */
    private Button loginBtn;
    /**
     * Button to process the register request.
     */
    private Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etPassword = findViewById(R.id.etRegisterPassword);
        etEmail = findViewById(R.id.etRegisterEmail);
        etUsername = findViewById(R.id.etRegisterUsername);
        loginBtn = findViewById(R.id.btnToLogin);
        regBtn = findViewById(R.id.btnRegister);

        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(username,email,password);

        Call<DefaultResponse> call = retrofitServices.register(user);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse defaultResponse;
                if(response.code()==200){
                    defaultResponse = response.body();
                    String message = defaultResponse.getMessage();
                    Toast.makeText(RegisterActivity.this, message,Toast.LENGTH_SHORT).show();
                    if (message.equalsIgnoreCase("Register Success.")) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}