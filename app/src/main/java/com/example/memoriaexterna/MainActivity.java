package com.example.memoriaexterna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button guardar;
    Button leer;
    TextView textoPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        guardar = findViewById( R.id.button );
        leer = findViewById( R.id.button2 );
        textoPrincipal = findViewById( R.id.textView );
        textoPrincipal.setText( verificarSD().toString() );
        guardar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //File ruta_sd = Environment.getExternalStorageDirectory();
                    File ruta_sd = getExternalFilesDir(Environment.DIRECTORY_MUSIC);

                    File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");

                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    new FileOutputStream(f));

                    fout.write("Texto de prueba.");
                    fout.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
                }
            }
        } );
        leer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //File ruta_sd = Environment.getExternalStorageDirectory();
                    File ruta_sd = getExternalFilesDir(Environment.DIRECTORY_MUSIC);

                    File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");

                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(f)));

                    String texto = fin.readLine();
                    textoPrincipal.setText( texto );
                    fin.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                }
            }
        } );
    }

    private Boolean verificarSD(){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;

        //Comprobamos el estado de la memoria externa (tarjeta SD)
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED))
        {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
        {
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else
        {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
        return sdAccesoEscritura;
    }
}