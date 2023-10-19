package com.example.testauthenticationfirebase;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth.getInstance();
    }
}
