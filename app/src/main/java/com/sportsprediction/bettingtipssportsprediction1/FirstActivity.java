package com.sportsprediction.bettingtipssportsprediction1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;

public class FirstActivity extends AppCompatActivity {

    public static void open(Activity activity, String text) {
        Intent intent = new Intent(activity, FirstActivity.class);
        intent.putExtra("text", text);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }
}
