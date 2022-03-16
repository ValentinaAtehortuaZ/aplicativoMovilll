package com.example.aplicacion_concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VehiculoActivity extends AppCompatActivity {

    EditText jetplaca,jetmarca,jetmodelo,jetcosto,jetcolor;
    Button jbtguardar,jbtconsultar,jbtanular, jbtcancelar,jbtregresar;
    String placa;
    long resp;
    int sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);

        getSupportActionBar().hide();
        jetplaca=findViewById(R.id.etplaca);
        jetmarca=findViewById(R.id.etmarca);
        jetmodelo=findViewById(R.id.etmodelo);
        jetcolor=findViewById(R.id.etcolor);
        jetcosto=findViewById(R.id.etcosto);
        jbtguardar=findViewById(R.id.btguardar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbtanular=findViewById(R.id.btanular);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbtregresar=findViewById(R.id.btregresar);
        sw=0;
    }


    public void Guardar(View view) {
        String  marca, modelo, color, costo;

        placa = jetplaca.getText().toString();
        marca = jetmarca.getText().toString();
        modelo = jetmodelo.getText().toString();
        color = jetcolor.getText().toString();
        costo = jetcosto.getText().toString();
        if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty()
                || color.isEmpty() || costo.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        } else {
            Conexion_Sqlite admin = new Conexion_Sqlite(this, "concesionario1.db", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues guardar = new ContentValues();
            guardar.put("placa", placa);
            guardar.put("marca ", marca);
            guardar.put("modelo", modelo);
            guardar.put("color", color);
            guardar.put("costo", costo);
            Consultar_Vehiculo();
           if(sw == 1){
               sw=0;
             resp=db.update("TblVehiculo",guardar,"placa"+placa+"'",null);
            }
           else{
               resp=db.insert("TblVehiculo",null,guardar);
           }

            if (resp > 0) {
                Limpiar_campos();
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }


    }

  public void Consultar(View view){Consultar_Vehiculo();}

    public void Anular(View view){
        Consultar_Vehiculo();
        if (sw == 1){

            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues dato=new ContentValues();
            dato.put("placa",placa);
            dato.put("activo","no");
            resp=db.update("TblVehiculo",dato,"placa='" + placa + "'",null);
            if (resp > 0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else {
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }

    }
   private void Consultar_Vehiculo(){

        placa=jetplaca.getText().toString();
        if (placa.isEmpty()){
            Toast.makeText(this, "La placa es requerida para buscar", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblVehiculo where placa='" + placa + "'",null);
            if (fila.moveToNext()){
                sw=1;
                jetmarca.setText(fila.getString(1));
                jetmodelo.setText(fila.getString(2));
                jetcolor.setText(fila.getString(3));
                jetcosto.setText(fila.getString(4));

                }


            else{
                Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }



   private void Limpiar_campos(){
        sw=0;
        jetplaca.setText("");
        jetmarca.setText("");
        jetmodelo.setText("");
        jetmodelo.setText("");
        jetcolor.setText("");
        jetcosto.setText("");
        jetplaca.requestFocus();
    }


    public void Cancelar(View view){
      Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmenu=new Intent(this,MenuActivity.class);
        startActivity(intmenu);
    }

}

