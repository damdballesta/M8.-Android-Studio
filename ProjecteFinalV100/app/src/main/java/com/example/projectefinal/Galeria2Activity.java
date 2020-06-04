package com.example.projectefinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class Galeria2Activity extends AppCompatActivity {

    private final String CARPETA_ARREL = "ImatgesLaGosseta/";
    private final String RUTA_IMATGE = CARPETA_ARREL+"LaGossetaPresumida";
    private String path;
    final int COD_SELECCIONADA = 10;
    final int COD_FOTO = 20;
    final int COD_SHARE = 30;
    Bitmap bitmap;
    Button botonCargar;
    ImageView imatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria2);

        imatge = findViewById(R.id.imageViewGal);
        botonCargar = findViewById(R.id.btnCargarImg);

        if(validarPermisos()){
            botonCargar.setEnabled(true);
        }else {
            botonCargar.setEnabled(false);
        }
    }

    public void onClick(View view) {
        menuImatge();
    }

    private void menuImatge() {

        final CharSequence[] opcions = {"Fer Foto", "Carregar Imatge", "Compartir Imatge", "Cancelar"};
        final AlertDialog.Builder alertOpcions = new AlertDialog.Builder(Galeria2Activity.this);
        alertOpcions.setTitle("Selecciona una opció");
        alertOpcions.setItems(opcions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opcions[i].equals("Fer Foto")) {
                    fetFoto();

                }else if(opcions[i].equals("Compartir Imatge")){
                    imatge.buildDrawingCache();
                    bitmap = imatge.getDrawingCache();

                    try {
                        File file = new File(imatge.getContext().getCacheDir(), bitmap + ".png");
                        FileOutputStream fOut = null;
                        fOut = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        file.setReadable(true, false);
                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        intent.setType("image/png");
                        startActivityForResult(Intent.createChooser(intent,"Selecciona una aplicació"), COD_SHARE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    if(opcions[i].equals("Carregar Imatge")){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(Intent.createChooser(intent,"Selecciona una aplicació"), COD_SELECCIONADA);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpcions.show();
    }

    private void fetFoto() {
        File fileImatge = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
        boolean isCreada = fileImatge.exists();
        String nomImatge = "";

        if(isCreada==false){
            isCreada = fileImatge.mkdirs();
        }
        if(isCreada==true){
            nomImatge = (System.currentTimeMillis()/1000)+".jpg";
        }

        path = getExternalFilesDir(Environment.DIRECTORY_PICTURES) +File.separator+nomImatge;

        File imatge = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities = getApplicationContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(this,authorities,imatge);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imatge));
        }
        startActivityForResult(intent,COD_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONADA:
                    Uri miPath = data.getData();
                    imatge.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta d'emmagatzematge","Path: "+path);
                        }
                    });

                    bitmap = BitmapFactory.decodeFile(path);
                    imatge.setImageBitmap(bitmap);
                    break;
            }

        }
    }

    private boolean validarPermisos() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();

        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opcions = {"SI", "NO"};
        final AlertDialog.Builder alertOpcions = new AlertDialog.Builder(Galeria2Activity.this);
        alertOpcions.setTitle("Vols configurar els permisos manualment?");
        alertOpcions.setItems(opcions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opcions[i].equals("SI")){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Els permisos no s'han acceptat",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        alertOpcions.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(Galeria2Activity.this);
        dialogo.setTitle("Permisos Desactivats");
        dialogo.setMessage("S'han d'acceptar els permisos per al correcte funcionament de la App");

        dialogo.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);
            }
        });
        dialogo.show();
    }

}
