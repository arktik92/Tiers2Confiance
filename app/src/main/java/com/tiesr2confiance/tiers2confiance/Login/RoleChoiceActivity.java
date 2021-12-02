package com.tiesr2confiance.tiers2confiance.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.tiesr2confiance.tiers2confiance.R;

public class RoleChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);


    }

    public void onChoiceClick(View v) {

        switch (v.getId()) {
            case R.id.btn_role_crelib:
                    CreationProfilActivity.role = 1;
                startActivity(new Intent(RoleChoiceActivity.this, CreationProfilActivity.class));
                break;
            case R.id.btn_role_parrain:
                    CreationProfilActivity.role = 2;
                startActivity(new Intent(RoleChoiceActivity.this, CreationProfilActivity.class));
                    break;
        }

    }
}

