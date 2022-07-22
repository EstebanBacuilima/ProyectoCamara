package com.poco.camara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // Componetes
    Button BtnCamara;
    ImageView ImagenCamara;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar
        BtnCamara = findViewById(R.id.btnCamara);
        ImagenCamara = findViewById(R.id.imagen_tomada);

        // Envento al botono al dar click
        BtnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

    }

    // Metodo abrir camara

    private void openCamera () {
        Intent intetn = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //if (intetn.resolveActivity(getPackageManager()) != null ){

            File imagenArchivo = null;

            try {
                imagenArchivo = crearImagen();
            }catch (IOException ex){
                Log.e("Error", ex.toString());
            }

            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(this, "com.poco.camara.fileprovider", imagenArchivo);
                intetn.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intetn,1);

            }
        //}
    }

    // Mostrar imagen en textView
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imgBitmap = (Bitmap) extras.get("data");
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            ImagenCamara.setImageBitmap(imgBitmap);
        }
    }

    // Almacenar imagen

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(nombreImagen, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        rutaImagen = image.getAbsolutePath();
        return image;
    }

}