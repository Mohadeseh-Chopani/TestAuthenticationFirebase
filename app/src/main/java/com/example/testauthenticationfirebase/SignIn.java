package com.example.testauthenticationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.testauthenticationfirebase.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    View btn_signin;
    TextInputEditText et_email, et_password;
    TextInputLayout email_box,password_box;

    String emailPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn_signin = findViewById(R.id.btn_sign_in);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        email_box = findViewById(R.id.email_box);
        password_box = findViewById(R.id.password_box);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressDialog = new ProgressDialog(this);


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_login();
            }
        });
    }

    void profile_login() {

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (!email.matches(emailPatter))
            email_box.setError("Invalid email");
        else if (password.isEmpty() || password.length() < 6) {
            password_box.setError("Invalid password");
        } else {
            progressDialog.setTitle("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUser();
                        Toast.makeText(SignIn.this, "sign in successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignIn.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    void sendUser() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}