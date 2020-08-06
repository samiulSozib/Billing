package com.sportsprediction.bettingtipssportsprediction1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Free_Tips extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    List<Today_Class> today_list;
    TodayAdapter todayAdapter;
    TextView dateText,textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free__tips);

        databaseReference= FirebaseDatabase.getInstance().getReference("Free_Tips");
        databaseReference1=FirebaseDatabase.getInstance().getReference("Free_Tips_Date");
        databaseReference2=FirebaseDatabase.getInstance().getReference("Ad_in_free_tips");
        today_list=new ArrayList<>();

        todayAdapter=new TodayAdapter(Free_Tips.this,today_list);



        listView=findViewById(R.id.today_list);


        dateText=findViewById(R.id.date_id);
        textView=findViewById(R.id.ad_in_safe_picks);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot3:dataSnapshot.getChildren()){
                    AddClass addClass=dataSnapshot3.getValue(AddClass.class);
                    textView.setText(addClass.text);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                    DateClass dateClass=dataSnapshot2.getValue(DateClass.class);
                    dateText.setText(dateClass.date);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onStart() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                today_list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Today_Class today_class=dataSnapshot1.getValue(Today_Class.class);
                    today_list.add(today_class);

                }

                listView.setAdapter(todayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));finish();
    }
}
