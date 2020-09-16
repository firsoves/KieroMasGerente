package com.magic.kieromasgerente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Objects;

public class crop_image extends AppCompatActivity {

    CropImageView cropImageView;
    Uri imageCroppedUri;
    String nombreFotoTemporal = "";
    String rutaFotoPlato = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        cropImageView = findViewById(R.id.cropImageView);

        cropImageView.setFixedAspectRatio(true);
        if (getIntent().getStringExtra("from").equals("com.magic.kieromasgerente.mi_local")) {
            cropImageView.setAspectRatio(1, 1);
        } else {
            cropImageView.setAspectRatio(4, 3);
        }
        cropImageView.setImageUriAsync(getIntent().getData());

        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.secundario_app));

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void rotateImage(View view) {
        cropImageView.rotateImage(90);
    }

    public void flipImage(View view) {
        cropImageView.flipImageHorizontally();
    }

    public void acceptCrop(View view) {
        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                try {
                    cropComplete(result);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        File imageCroppedFile = null;
        //imageCroppedFile = File.createTempFile("crop_" + (System.currentTimeMillis() / 1000), ".png", this.getExternalFilesDir(null));
        nombreFotoTemporal = "fileTemp.png";
        rutaFotoPlato = MainActivity.RUTA_FILE_FOTO_PLATOS + "/" + nombreFotoTemporal;
        MainActivity.nombreFotoTemporalPlatoCompleto = rutaFotoPlato;
        imageCroppedFile = new File(rutaFotoPlato);

        //imageCroppedUri = Uri.parse(imageCroppedFile.toURI().toString());
        imageCroppedUri = Uri.fromFile(imageCroppedFile);
        cropImageView.saveCroppedImageAsync(imageCroppedUri);
    }

    public void cropComplete(CropImageView.CropResult result) throws ClassNotFoundException {
        Intent intentCroppedSuccess = new Intent(crop_image.this, Class.forName(getIntent().getStringExtra("from")));
        intentCroppedSuccess.setData(imageCroppedUri);
        startActivity(intentCroppedSuccess);
    }
}