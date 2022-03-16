package com.example.aplicacion_concesionario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Conexion_Sqlite extends SQLiteOpenHelper {


    public Conexion_Sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE TblCliente(idcliente text primary key," +
        "nomcliente text not null, usuario text not null, clave text not null,activo text not null default 'si')");
        sqLiteDatabase.execSQL("Create table TblVehiculo(placa text primary key, marca text not null,modelo text not null" +
                ",color text not null, costo text not null, activo text not null default 'si')");
        sqLiteDatabase.execSQL("create table TblFactura(codFactura text primary key,"+"fecha text not null,idcliente text not null," +
                "placa text not null,activo text not null default 'no',constraint pkFctura foreign key (idcliente) references TblCliente(idcliente)," +
                "foreign key(placa)references TblVehiculo(placa))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE TblCliente");{
            onCreate(sqLiteDatabase);
        }
        sqLiteDatabase.execSQL("DROP TABLE TblVehiculo");{
            onCreate(sqLiteDatabase);
        }
        sqLiteDatabase.execSQL("DROP TABLE TblFactura");{
            onCreate(sqLiteDatabase);
        }
    }
}
