package com.vinnie.preciousfamily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnExit;
    CardView c1,c2,c3,c4;
    RelativeLayout rl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl1 = findViewById(R.id.rlayout1);
        btnExit = findViewById(R.id.btnExit);
        c1 = findViewById(R.id.card_view);
        c2 = findViewById(R.id.card_view2);
        c3 = findViewById(R.id.card_view3);
        c4 = findViewById(R.id.card_view4);

        c1.setRadius(20);c2.setRadius(20);c3.setRadius(20);c4.setRadius(20);

        c1.setBackgroundResource(R.drawable.card_bg);c2.setBackgroundResource(R.drawable.card_bg);
        c3.setBackgroundResource(R.drawable.card_bg);c4.setBackgroundResource(R.drawable.card_bg);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Members.class));
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Blog clicked", Toast.LENGTH_SHORT).show();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Accounts.class);
                startActivity(myIntent);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
