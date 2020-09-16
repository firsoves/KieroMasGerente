package com.magic.kieromasgerente;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.magic.kieromasgerente.Servidor.ApiDatos;
import com.magic.kieromasgerente.Servidor.ApiInterface;
import com.magic.kieromasgerente.Servidor.Retrofit2;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.magic.kieromasgerente.Servidor.SubidaFoto;

public class crear_producto extends AppCompatActivity {

    private List<Retrofit2> datos;
    private ApiInterface apiInterface;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_CODE = 101;
    private static final int PICK_IMAGE = 102;

    ImageView productImageView;
    TextInputLayout textInputLayoutNombreProducto;
    TextInputLayout textInputLayoutPrecioProducto;
    Uri image_uri;

    String nombreProducto = "";
    String precioProductoNormal = "";
    String precioProductoOferta = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        productImageView = findViewById(R.id.imageView9);
        textInputLayoutNombreProducto = findViewById(R.id.nombreProducto);
        textInputLayoutPrecioProducto = findViewById(R.id.precioProducto);
        Intent intentCropped = getIntent();

        if (intentCropped.getData() != null) productImageView.setImageURI(intentCropped.getData());
        MainActivity.nombreFotoTemporalPlato = intentCropped.getData();

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

    public void intolerancias(View view) {

        Intent intentInicio = new Intent(crear_producto.this, select_intolerancia.class);
        Intent previousIntent = getIntent();
        Bundle extras = previousIntent.getExtras();
        if (extras != null) {
            if (extras.containsKey("selectedIntolerances")) {
                intentInicio.putExtra("selectedIntolerances", previousIntent.getIntArrayExtra("selectedIntolerances"));
            }
        }
        startActivity(intentInicio);

        finish();
    }

    public void select_icon(View view) {
        Intent intentInicio = new Intent(crear_producto.this, select_icon.class);
        startActivity(intentInicio);

        finish();
    }


    public void ingredientes_seleccionables(View view) {
        Intent intentInicio = new Intent(crear_producto.this, ingrediente_seleccionable.class);
        startActivity(intentInicio);

        finish();
    }

    public void ingrediente_retirable(View view) {
        Intent intentInicio = new Intent(crear_producto.this, ingrediente_retirable.class);
        startActivity(intentInicio);

        finish();
    }

    public void ingrediente_anadible(View view) {
        Intent intentInicio = new Intent(crear_producto.this, ingrediente_anadibles.class);
        startActivity(intentInicio);

        finish();
    }

    public void hacerFoto(View view) {


        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    public void select_photo(View view) {
        Intent galeryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        galeryIntent.setType("image/*");
        startActivityForResult(galeryIntent, PICK_IMAGE);
    }

    private void dispatchTakePictureIntent() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From camera");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
            startActivityForResult(takePictureIntent, CAMERA_CAPTURE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            Intent intentCropperImage = new Intent(crear_producto.this, crop_image.class);
            intentCropperImage.putExtra("from", this.getClass().getName());
            intentCropperImage.setData(image_uri);
            startActivity(intentCropperImage);

            finish();
        }
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Intent intentCropperImage = new Intent(crear_producto.this, crop_image.class);
            intentCropperImage.putExtra("from", this.getClass().getName());
            intentCropperImage.setData(data.getData());
            startActivity(intentCropperImage);
            finish();
        }
    }

    public void generar_producto(View view) {

        nombreProducto = String.valueOf(textInputLayoutNombreProducto.getEditText().getText());
        precioProductoOferta = String.valueOf(textInputLayoutPrecioProducto.getEditText().getText());

        enviarDatos("0");


/*
        Intent intentInicio = new Intent(crear_producto.this, config_inicio.class);
        startActivity(intentInicio);

        finish();*/
    }

    public void enviarDatos(String actividad) {
        enviarDatosServidor(actividad);
    }

    private void enviarDatosServidor(String actividad) {
        apiInterface = ApiDatos.getDatosServidor().create(ApiInterface.class);
        Call<List<Retrofit2>> call = apiInterface.getDatos(actividad, "1", nombreProducto, precioProductoOferta, precioProductoOferta, "", "");

        call.enqueue(new Callback<List<Retrofit2>>() {
            @Override
            public void onResponse(Call<List<Retrofit2>> call, Response<List<Retrofit2>> response) { // Ответ с сервера.
                datos = response.body();

                MainActivity.idNuevoProducto = Integer.parseInt(datos.get(0).getDataString01());

                new SubidaFoto("").execute(); // Загружаю фото на сервер

                Intent intentInicio = new Intent(crear_producto.this, config_inicio.class);
                startActivity(intentInicio);
                finish();
            }

            @SuppressLint("LongLogTag")
            public void onFailure(Call<List<Retrofit2>> call, Throwable t) {
            }
        });
    }

    public void categorias_producto(View view) {
        Intent intentInicio = new Intent(crear_producto.this, SeleccionarCategoriaProducto.class);
        startActivity(intentInicio);

        finish();
    }

    public void changeServiceOptions(View view) {

        ToggleButton button = (ToggleButton) view;

        int arrayId = 0;
        switch (getResources().getResourceEntryName(button.getId())) {
            case "toggleButton5":
                arrayId = 0;
                break;
            case "toggleButton4":
                arrayId = 1;
                break;
            case "toggleButton3":
                arrayId = 2;
                break;
        }
        if (button.isChecked()) {
            MainActivity.selectedProductService[arrayId] = 1;
            button.setBackgroundColor(getColor(R.color.verde_suave));
        } else {
            MainActivity.selectedProductService[arrayId] = 0;
            button.setBackgroundColor(getColor(R.color.rojo_suave));
        }
    }

    public void vistaPrevia(View view) {
        Intent intentInicio = new Intent(crear_producto.this, VistaPreviaComponente.class);
        startActivity(intentInicio);

        finish();
    }

    public void Atras(View view) {
    }
}