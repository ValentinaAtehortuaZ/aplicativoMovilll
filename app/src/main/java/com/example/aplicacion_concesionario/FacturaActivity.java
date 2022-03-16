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

import java.util.Date;

public class FacturaActivity extends AppCompatActivity {

    EditText jetcodigo,jetfecha,jetidentificacion,jetplaca;
    Button jbtguardar,jbtconsultar,jbtanular,jbtcancelar,jbbtregresar;
    long resp;
    String codigo;
    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        getSupportActionBar().hide();
        jetcodigo=findViewById(R.id.etcodigo);
        jetfecha=findViewById(R.id.etfecha);
        jetidentificacion=findViewById(R.id.etidentificacion);
        jetplaca=findViewById(R.id.etplaca);
        jbtguardar=findViewById(R.id.btguardar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbtanular=findViewById(R.id.btanular);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbbtregresar=findViewById(R.id.btregresar);



    }

    public void Guardar(View view) {
        String codigo, identificacion, placa, fecha;


        codigo = jetcodigo.getText().toString();
        placa = jetplaca.getText().toString();
        identificacion = jetidentificacion.getText().toString();
        fecha = jetfecha.getText().toString();

        if (codigo.isEmpty() || placa.isEmpty() || identificacion.isEmpty()
                || fecha.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        } else {
            Conexion_Sqlite admin = new Conexion_Sqlite(this, "concesionario1.db", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("placa ", placa);
            registro.put("identificacion", identificacion);
            registro.put("fecha", fecha);

            Consultar_Factura();
            if (sw == 1) {
                sw = 0;
                resp = db.update("TblFactura", registro, "codigo" + codigo + "'", null);
            } else {
                resp = db.insert("TblFactura", null, registro);
            }

            if (resp > 0) {
                Limpiar_campos();
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }

            String activo1="no";

            Cursor fila1=db.rawQuery("select * from TblFactura where placa='" + placa + "'",null);
            if (fila1.moveToNext()){
                Toast.makeText(this, "La placa no se encuentra resgistrada", Toast.LENGTH_SHORT).show();
                jetplaca.requestFocus();
            }
            Cursor fila2=db.rawQuery("select * from TblFactura where idcliente='" + identificacion + "'",null);
            if(fila2.moveToNext()){
                Toast.makeText(this, "La identificacion no se encuentra resgistrada", Toast.LENGTH_SHORT).show();
                jetidentificacion.requestFocus();
            }
            Cursor fila3=db.rawQuery("select * from TblVehiculo where placa='" + placa + "'" ,null);
            if(fila3.moveToNext()){
                if(activo1.equals(fila3.getString(4))){
                    Toast.makeText(this, "No se puede vender", Toast.LENGTH_SHORT).show();
                }
                else{
                    Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
                    SQLiteDatabase db=admin.getReadableDatabase();

                        sw=1;
                        jetplaca.setText(fila3.getString(1));
                        jetfecha.setText(fila3.getString(2));
                        jetidentificacion.setText(fila3.getString(3));


                }
            }

            else{
                Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
                SQLiteDatabase db=admin.getReadableDatabase();
                Cursor fila=db.rawQuery("select * from TblFactura where codigo='" + codigo + "'",null);
                if (fila.moveToNext()){
                    sw=1;
                    jetplaca.setText(fila.getString(1));
                    jetfecha.setText(fila.getString(2));
                    jetidentificacion.setText(fila.getString(3));

                }

                else{
                    Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
            db.close();
        }
    }

        public void Consultar(View view){Consultar_Factura();}


    public void Anular(View view){
        Consultar_Factura();
        if (sw == 1){

            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues dato=new ContentValues();
            dato.put("codigo",codigo);
            dato.put("activo","no");
            resp=db.update("TblFactura",dato,"codigo='" + codigo + "'",null);
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

        private void Consultar_Factura(){

            codigo=jetcodigo.getText().toString();
            if (codigo.isEmpty()){
                Toast.makeText(this, "El codigo es requerido para buscar", Toast.LENGTH_SHORT).show();
                jetcodigo.requestFocus();
            }
            else{
                Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
                SQLiteDatabase db=admin.getReadableDatabase();
                Cursor fila=db.rawQuery("select * from TblFactura where codigo='" + codigo + "'",null);
                if (fila.moveToNext()){
                    sw=1;
                    jetplaca.setText(fila.getString(1));
                    jetfecha.setText(fila.getString(2));
                    jetidentificacion.setText(fila.getString(3));

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
        jetcodigo.setText("");
        jetidentificacion.setText("");
        jetfecha.setText("");
        jetcodigo.requestFocus();
    }

    public void Cancelar(View view){
        Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmenu=new Intent(this,MenuActivity.class);
        startActivity(intmenu);
    }}

