package main.jnakshansh.bottomnavigationbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mulai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai);
        Button btn = (Button) findViewById(R.id.mulai);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent il= new Intent(Mulai.this, LoginActivity.class);
                startActivity(il);
            }
        });
    }
}