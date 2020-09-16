package com.magic.kieromasgerente;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static String baseUrl = "";
    public static String baseUrlFoto = "";

    private static final int PERMISSION_REQUEST_CODE = 200;
    public static String PHP_FILE_LOGO_LOCALES = "";
    public static String PHP_FILE_FOTO_PLATOS = "";
    public static String PHP_FILE_FOTO_INGRIDIENTES_RETIRABLES = "";
    public static String PHP_FILE_LOGO_INTOLERANCIAS = "";

    public static String RUTA_FILE_FOTO_GENERAL = "";
    public static String RUTA_FILE_FOTO_PLATOS = "";

    public static String nombreFotoTemporalPlatoCompleto = "";

    public static Uri nombreFotoTemporalPlato;

    File fileFotoPlato;

    public static int idNuevoProducto = 0;

    public static int[] selectedProductCategories = {
            0, 0, 0, 0, 0, 0, 0
    };

    public static int[] selectedProductService = {
            0, 0, 0
    };

    public static final ArrayList<HashMap<String, String>> crearProductoIngredientesElegibles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //para la barra de arriba
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.secundario_app));
        //PARA QUE DESAPAREZCA LA BARRA DE NAVEGACIÓN
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        baseUrl = getString(R.string.baseUrl) + "/";
        baseUrlFoto = getString(R.string.baseUrlFoto) + "/";

        PHP_FILE_LOGO_LOCALES = getString(R.string.baseUrlLogoLocales);
        PHP_FILE_FOTO_PLATOS = getString(R.string.baseUrlFotoPlatos);
        PHP_FILE_FOTO_INGRIDIENTES_RETIRABLES = getString(R.string.baseUrlFotoIngridientes);
        PHP_FILE_LOGO_INTOLERANCIAS = getString(R.string.baseUrlFotoIntolerancias);

        RUTA_FILE_FOTO_GENERAL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KMG"; // Место для хранения всех фотографий
        RUTA_FILE_FOTO_PLATOS = Environment.getExternalStorageDirectory().getAbsolutePath() + "/KMG/PLA"; // Место для хранения фотографий блюд


// Получаю все разрешения и, фактически, начинаю работу. Этот модуль должен быть последним в классе.
        if (shouldAskPermissions()) {
            askPermissions();
        }
    }

    // МОДУЛЬ ОТВЕЧАЮЩИЙ ЗА РАЗРЕШЕНИЯ
    //
    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.INTERNET",
                "android.permission.CAMERA",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // Принимаем ответ пользователя.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) { // Проверяем код запроса. В данном случае: 200.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Анализирую ответ пользователя.
                // Разрешение предоставлено
                fileFotoPlato = new File(RUTA_FILE_FOTO_GENERAL);
                if (!fileFotoPlato.exists()) {
                    fileFotoPlato.mkdir();
                }
                fileFotoPlato = new File(RUTA_FILE_FOTO_PLATOS);
                if (!fileFotoPlato.exists()) {
                    fileFotoPlato.mkdir();
                }

                fileFotoPlato.getAbsolutePath();

                Intent intentInicio = new Intent(MainActivity.this, config_inicio.class);
                startActivity(intentInicio);
                finish();
            } else {
                // Разрешения НЕ получены
                finish();
            }
        }
    }
    //
    // МОДУЛЬ ОТВЕЧАЮЩИЙ ЗА РАЗРЕШЕНИЯ

    public void buttoniniciarsesion(View view) {
        Intent intentInicio = new Intent(MainActivity.this, config_inicio.class);
        startActivity(intentInicio);
        finish();
    }

    public void CrearCuenta(View view) {
        Intent intentInicio = new Intent(MainActivity.this, crear_cuenta.class);
        startActivity(intentInicio);
        finish();
    }
}