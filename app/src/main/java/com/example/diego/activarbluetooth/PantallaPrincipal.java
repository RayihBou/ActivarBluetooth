package com.example.diego.activarbluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PantallaPrincipal extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABLITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        context = getApplicationContext();
        activity = this;
    }

    //Metodo para habilitar Bluetooth
    public void habilitarBluetooth(View v){

        solicitarPermiso();
        //Obtener el adaptador bluetooth por defecto
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null){
            Toast.makeText(PantallaPrincipal.this, "Tu dispositivo no tiene Bluetooth", Toast.LENGTH_SHORT).show();
        }

        //Habilitar el bluetooth con action request
        if (!mBluetoothAdapter.isEnabled()){
            Intent habilitarBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(habilitarBluetoothIntent, CODIGO_SOLICITUD_HABLITAR_BLUETOOTH);

        }
    }

    //Chequear si los permisos fueron otorgados o no
    public boolean checarStatusPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH); //Preguntando si el permiso existe en nuestra aplicacion
        if (resultado == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            return false;

        }
    }

    //Solicitud del permiso
    public void solicitarPermiso(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(PantallaPrincipal.this, "El permiso ya fue otrogado, si deseas desactivarlo puedes ir a los ajustes de la aplicacion", Toast.LENGTH_SHORT).show();
        } else{
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }


    //Gestion del Permiso Otorgado
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISO:
                if (checarStatusPermiso()) {
                    Toast.makeText(PantallaPrincipal.this, "Ya esta activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Toast.makeText(PantallaPrincipal.this, "No esta activo el permiso para Bluetooth", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
