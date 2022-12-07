package com.dishupproject.ui;

import com.dishupproject.R;
import com.dishupproject.data.RetrofitInstance;;
import com.dishupproject.data.model.GetUserResponse;
import com.dishupproject.data.model.User;
import com.dishupproject.data.services.RetrofitServices;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is class for user login activity.
 *
 * @author Dhau' Embun Azzahra
 */
public class LoginActivity extends AppCompatActivity {

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
     * Button to process the login request.
     */
    private Button loginBtn;
    /**
     * Button for moving to Register Activity.
     */
    private Button regBtn;
    /**
     * For saving current logged account.
     */
    private static User loggedAccount = null;

    /**
     * Method to return user object logged in
     * on this app.
     *
     * @return user account.
     */
    public static User getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        int userId = sessionManager.getSession();

        //the account id is initialized by -1 (nobody is logged in)
        if(userId!=-1){
            User loggedUser = new User(userId);
            Call<GetUserResponse> call = retrofitServices.getUserByUserId(loggedUser);
            call.enqueue(new Callback<GetUserResponse>() {
                @Override
                public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                    if(response.code()==200){
                        GetUserResponse resp = response.body();
                        if(resp.getUser()!=null){
                            loggedAccount = resp.getUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }else{
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPassword = findViewById(R.id.etLoginPassword);
        etEmail = findViewById(R.id.etLoginEmail);
        loginBtn = findViewById(R.id.button2);
        regBtn = findViewById(R.id.button3);

        retrofitServices = RetrofitInstance.getInstance().create(RetrofitServices.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(email,password);

        Call<GetUserResponse> call = retrofitServices.login(user);
        call.enqueue(
                new Callback<GetUserResponse>() {
                    @Override
                    public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                        if(response.code()==200){
                            GetUserResponse resp = response.body();
                            Toast.makeText(LoginActivity.this, resp.getMessage(),Toast.LENGTH_SHORT).show();
                            if(resp.getUser()!=null){
                                loggedAccount = resp.getUser();
                                if(resp.getMessage().equalsIgnoreCase("log in success")){
                                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                    sessionManager.saveSession(loggedAccount);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        }else{
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}