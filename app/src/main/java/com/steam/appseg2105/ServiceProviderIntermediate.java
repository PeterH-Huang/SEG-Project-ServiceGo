package com.steam.appseg2105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ServiceProviderIntermediate extends AppCompatActivity {
    Button addEditAvail;
    Button seeAvail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_intermediate);
        addEditAvail = findViewById(R.id.addEditAvailBut);
        seeAvail = findViewById(R.id.seeAvailBut);
        addEditAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddAvailabilities.class);
                intent.putExtra("item",getIntent().getStringExtra("item"));
                startActivity(intent);
            }
        });
        seeAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeeAvailabilities.class);
                intent.putExtra("item",getIntent().getStringExtra("item"));
                startActivity(intent);
            }
        });
    }
}
