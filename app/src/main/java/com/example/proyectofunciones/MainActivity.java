package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<AppUser> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // cargar usuarios guardados al abrir la app
        listaUsuarios = Archivos.cargarUsuarios(this);
    }

    public void irAdmin(View v) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("listaUsuarios", listaUsuarios);
        startActivityForResult(intent, 1);
    }

    public void irLogin(View v) {
        if (listaUsuarios.isEmpty()) {
            Toast.makeText(this,
                    "No hay usuarios registrados. Ve a Administrador primero.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("listaUsuarios", listaUsuarios);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            listaUsuarios = (ArrayList<AppUser>) data.getSerializableExtra("listaUsuarios");
            // guardar en archivo cada vez que se actualiza la lista
            Archivos.guardarUsuarios(this, listaUsuarios);
        }
    }
}