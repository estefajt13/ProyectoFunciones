package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Lista de usuarios que vive aquí y se pasa a las demás activities
    ArrayList<AppUser> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Botón Administrador
    public void irAdmin(View v) {
        Intent intent = new Intent(this, AdminActivity.class);
        // pasamos la lista para que Admin pueda agregar usuarios
        intent.putExtra("listaUsuarios", listaUsuarios);
        startActivityForResult(intent, 1); // 1 = código para identificar que volvemos de Admin
    }

    // Botón Usuario
    public void irLogin(View v) {
        if (listaUsuarios.isEmpty()) {
            android.widget.Toast.makeText(this,
                    "No hay usuarios registrados. Ve a Administrador primero.",
                    android.widget.Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("listaUsuarios", listaUsuarios);
        startActivity(intent);
    }

    // Cuando volvemos de AdminActivity, actualizamos la lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // recibimos la lista actualizada con los nuevos usuarios
            listaUsuarios = (ArrayList<AppUser>) data.getSerializableExtra("listaUsuarios");
        }
    }
}