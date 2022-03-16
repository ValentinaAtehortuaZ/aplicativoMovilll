package com.example.aplicacion_concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button jbtvehiculos, jbtfactura, jbtlistar, jbtusuario,jbtsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().hide();
        jbtvehiculos=findViewById(R.id.btvehiculo);
        jbtfactura=findViewById(R.id.btfactura);
        jbtlistar=findViewById(R.id.btlistar);
        jbtusuario=findViewById(R.id.btusuario);
        jbtsalir=findViewById(R.id.btsalir);
    }

    public void Vehiculos(View view){
        Intent intvehiculo=new Intent(this,VehiculoActivity.class);
        startActivity(intvehiculo);
    }

    public void Factura(View view){
        Intent intfacturas=new Intent(this,FacturaActivity.class);
        startActivity(intfacturas);
    }

    public void Mostrar(View view){
        Intent intmostrar=new Intent(this,MostrarActivity.class);
        startActivity(intmostrar);
    }

    public void Usuarios(View view){
        Intent intusuario=new Intent(this,RegistrarseActivity.class);
        startActivity(intusuario);
    }

    public void Salir(View view){
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }
}