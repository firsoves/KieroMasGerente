package com.magic.kieromasgerente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

import java.util.Objects;

public class select_intolerancia extends AppCompatActivity {


    CheckBox[] optionsCheckboxes = {
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_intolerancia);

        optionsCheckboxes[0] = findViewById(R.id.checkBox7);
        optionsCheckboxes[1] = findViewById(R.id.checkBox8);
        optionsCheckboxes[2] = findViewById(R.id.checkBox10);
        optionsCheckboxes[3] = findViewById(R.id.checkBox9);
        optionsCheckboxes[4] = findViewById(R.id.checkBox13);
        optionsCheckboxes[5] = findViewById(R.id.checkBox20);
        optionsCheckboxes[6] = findViewById(R.id.checkBox16);
        optionsCheckboxes[7] = findViewById(R.id.checkBox15);
        optionsCheckboxes[8] = findViewById(R.id.checkBox18);
        optionsCheckboxes[9] = findViewById(R.id.checkBox14);
        optionsCheckboxes[10] = findViewById(R.id.checkBox19);
        optionsCheckboxes[11] = findViewById(R.id.checkBox17);
        optionsCheckboxes[12] = findViewById(R.id.checkBox21);
        optionsCheckboxes[13] = findViewById(R.id.checkBox23);


        //para la barra de arriba
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.secundario_app));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Intent previousIntent = getIntent();
        Bundle extras = previousIntent.getExtras();
        if (extras != null){
            if (extras.containsKey("selectedIntolerances")){
                int selectedCheckboxes[] = previousIntent.getIntArrayExtra("selectedIntolerances");
                int loopId=0;
                for (CheckBox intolerancia : optionsCheckboxes){
                    if (selectedCheckboxes[loopId]==1){
                        intolerancia.setChecked(true);
                    }
                    else {
                        intolerancia.setChecked(false);
                    }
                    loopId++;
                }
            }
        }

    }

    public void confirmar(View view) {
        int[] selectedCheckboxes = {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        int loopId = 0;
        for (CheckBox intolerancia : optionsCheckboxes) {
            if (intolerancia.isChecked()) {
                selectedCheckboxes[loopId] = 1;
            } else {
                selectedCheckboxes[loopId] = 0;
            }
            loopId++;
        }
        Intent intentInicio = new Intent(select_intolerancia.this, CrearProducto.class);

        intentInicio.putExtra("selectedIntolerances", selectedCheckboxes);
        startActivity(intentInicio);


        finish();
    }
}