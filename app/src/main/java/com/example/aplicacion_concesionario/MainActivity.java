package com.example.aplicacion_concesionario;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText jetusuario,jetclave;
    Button jbtingresar,jbtcancelar,jbtregistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        jetusuario=findViewById(R.id.etusuario);
        jetclave=findViewById(R.id.etclave);
        jbtingresar=findViewById(R.id.btingresar);
        jbtcancelar=findViewById(R.id.btcancelar);
        jbtregistrarse=findViewById(R.id.btregistrarse);
    }

    public void Ingresar(View view){
        String usuario,clave;
        usuario=jetusuario.getText().toString();
        clave=jetclave.getText().toString();
        if (usuario.isEmpty() || clave.isEmpty()){
            Toast.makeText(this, "Usuario y clave requeridos", Toast.LENGTH_SHORT).show();
            jetusuario.requestFocus();
        }
        else{
            Conexion_Sqlite admin=new Conexion_Sqlite(this,"concesionario1.db",null,1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblCliente where usuario='" + usuario + "' and clave='" + clave + "'",null);
            if (fila.moveToNext()){
                Intent intmenu=new Intent(this,MenuActivity.class);
                startActivity(intmenu);
            }
            else{
                Toast.makeText(this, "Usuario o clave invalidos", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }

    }

    public void Cancelar(View view){
        jetusuario.setText("");
        jetclave.setText("");
        jetusuario.requestFocus();
    }

    public void Registrarse(View view){
        Intent intregistrarse=new Intent(this,RegistrarseActivity.class);
        startActivity(intregistrarse);
    }

}