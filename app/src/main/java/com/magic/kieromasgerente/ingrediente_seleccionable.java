package com.magic.kieromasgerente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.magic.kieromasgerente.Datos.AdapterIngredientesSeleccionables;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Objects;

public class ingrediente_seleccionable extends AppCompatActivity {
    LayoutInflater inflter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediente_seleccionable);

        //para la barra de arriba
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.secundario_app));
        RecyclerView recyclerView = findViewById(R.id.listaIngredientesSeleccionables);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inflter= (LayoutInflater.from(this));
        recyclerView.setAdapter(new AdapterIngredientesSeleccionables(this, MainActivity.crearProductoIngredientesElegibles));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void confirmar_cambios(View view) {
        Intent intentInicio = new Intent(ingrediente_seleccionable.this, crear_producto.class);
        startActivity(intentInicio);

        finish();
    }

    public void crear(View view) {
        TextInputLayout nombreNuevoIngredienteSeleccionable = findViewById(R.id.outlinedTextField1);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id","1");
        hashMap.put("nombre",nombreNuevoIngredienteSeleccionable.getEditText().getText().toString());
        MainActivity.crearProductoIngredientesElegibles.add(hashMap);
        nombreNuevoIngredienteSeleccionable.getEditText().setText("");
        hideKeyBoard();
    }
    public void hideKeyBoard() {
        View view1 = this.getCurrentFocus();
        if(view1!= null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }
}