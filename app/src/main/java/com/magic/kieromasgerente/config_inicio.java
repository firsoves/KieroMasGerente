package com.magic.kieromasgerente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

public class config_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_inicio);
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

    public void Mi_Qr(View view) {
        Intent intentInicio = new Intent(config_inicio.this, qr_grande.class);
        startActivity(intentInicio);

        finish();
    }

    public void Crear_Producto(View view) {
        Intent intentInicio = new Intent(config_inicio.this, CrearProducto.class);
        startActivity(intentInicio);

        finish();
    }

    public void crear_notificacion(View view) {
        Intent intentInicio = new Intent(config_inicio.this, crear_notificacion.class);
        startActivity(intentInicio);

        finish();
    }

    public void modificar_recompensas(View view) {
        Intent intentInicio = new Intent(config_inicio.this, modificar_recompensas.class);
        startActivity(intentInicio);

        finish();
    }

    public void lista_productos(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_productos.class);
        startActivity(intentInicio);

        finish();
    }

    public void lista_reservas(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_reservas.class);
        startActivity(intentInicio);

        finish();

    }

    public void lista_pedidos(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_pedidos.class);
        startActivity(intentInicio);

        finish();
    }

    public void lista_categorias(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_categorias.class);
        startActivity(intentInicio);

        finish();
    }

    public void lista_oferta(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_ofertas.class);
        startActivity(intentInicio);

        finish();
    }

    public void lista_menu_dia(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_menu_dia.class);
        startActivity(intentInicio);

        finish();
    }

    public void historial_reservas(View view) {
        Intent intentInicio = new Intent(config_inicio.this, historial_reservas.class);
        startActivity(intentInicio);

        finish();
    }

    public void boton_especial(View view) {
        Intent intentInicio = new Intent(config_inicio.this, lista_locales.class);
        startActivity(intentInicio);

        finish();

    }

    public void config_reservas(View view) {
        Intent intentInicio = new Intent(config_inicio.this, config_reservas.class);
        startActivity(intentInicio);

        finish();
    }

    public void mi_local(View view) {
        Intent intentInicio = new Intent(config_inicio.this, mi_local.class);
        startActivity(intentInicio);

        finish();
    }

    public void config_general(View view) {
        Intent intentInicio = new Intent(config_inicio.this, config_general.class);
        startActivity(intentInicio);

        finish();
    }
}