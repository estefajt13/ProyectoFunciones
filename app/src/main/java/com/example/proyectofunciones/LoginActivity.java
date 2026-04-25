package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private ArrayList<AppUser> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername    = findViewById(R.id.etUsername);
        etPassword    = findViewById(R.id.etPassword);

        // recibir la lista de usuarios desde MainActivity
        listaUsuarios = (ArrayList<AppUser>) getIntent()
                .getSerializableExtra("listaUsuarios");

        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<>();
        }
    }

    public void ingresar(View v) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validación: campos vacíos
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar el usuario en la lista
        AppUser usuarioEncontrado = null;
        for (AppUser u : listaUsuarios) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                usuarioEncontrado = u;
                break;
            }
        }

        // Si no encontró coincidencia
        if (usuarioEncontrado == null) {
            Toast.makeText(this, "Username o password incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login exitoso — ir al panel de usuario
        Toast.makeText(this, "Bienvenido " + usuarioEncontrado.getNombre(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, UserPanelActivity.class);
        intent.putExtra("usuario", usuarioEncontrado); // pasamos el usuario logueado
        startActivity(intent);
    }

    public void volver(View v) {
        finish(); // cierra la activity actual y regresa a la anterior
    }
}