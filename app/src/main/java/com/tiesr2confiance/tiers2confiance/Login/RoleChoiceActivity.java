package com.tiesr2confiance.tiers2confiance.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.R;


public class RoleChoiceActivity extends AppCompatActivity {

    Button btnRoleCelib;
    Button btnRoleParrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choice);

        final GlobalClass globalVariables = (GlobalClass) getApplicationContext();

        btnRoleCelib    =   findViewById(R.id.btn_role_celib);
        btnRoleParrain    =   findViewById(R.id.btn_role_parrain);

        btnRoleCelib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.setUserRole(1L);
                startActivity(new Intent(RoleChoiceActivity.this, CreationProfilActivity.class));
            }
        });

        btnRoleParrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.setUserRole(2L);
                startActivity(new Intent(RoleChoiceActivity.this, CreationProfilActivity.class));
            }
        });


    }

}

