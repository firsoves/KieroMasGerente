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

public class SeleccionarCategoriaProducto extends AppCompatActivity {

    CheckBox[] productCategoriesOptions = {
            null,
            null,
            null,
            null,
            null,
            null,
            null,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_categoria_producto);
        productCategoriesOptions[0]=findViewById(R.id.categoria_seleccionada1);
        productCategoriesOptions[1]=findViewById(R.id.categoria_seleccionada2);
        productCategoriesOptions[2]=findViewById(R.id.categoria_seleccionada3);
        productCategoriesOptions[3]=findViewById(R.id.categoria_seleccionada4);
        productCategoriesOptions[4]=findViewById(R.id.categoria_seleccionada5);
        productCategoriesOptions[5]=findViewById(R.id.categoria_seleccionada6);
        productCategoriesOptions[6]=findViewById(R.id.categoria_seleccionada7);

        int loopId=0;
        for(CheckBox option : productCategoriesOptions){
            if (MainActivity.selectedProductCategories[loopId]==1){
                option.setChecked(true);
            }else{
                option.setChecked(false);
            }
            loopId++;
        }
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

    public void confirmar_categorias(View view) {
        Intent intentInicio = new Intent(SeleccionarCategoriaProducto.this, CrearProducto.class);
        startActivity(intentInicio);

        int loopId=0;
        for(CheckBox option : productCategoriesOptions){
            if (option.isChecked()){
                MainActivity.selectedProductCategories[loopId]=1;
            }else{
                MainActivity.selectedProductCategories[loopId]=0;
            }
            loopId++;
        }
        finish();
    }
}