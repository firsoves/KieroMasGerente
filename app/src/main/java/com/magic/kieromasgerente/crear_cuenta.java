package com.magic.kieromasgerente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class crear_cuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        //para la barra de arriba
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.secundario_app));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void back(View view) {
        Intent intentInicio = new Intent(crear_cuenta.this, MainActivity.class);
        startActivity(intentInicio);

        finish();
    }

    public void CrearCuenta(View view) {
        Intent intentInicio = new Intent(crear_cuenta.this, config_inicio_negocio.class);
        startActivity(intentInicio);

        finish();
    }
}