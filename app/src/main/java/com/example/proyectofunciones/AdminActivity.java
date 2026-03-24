package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private EditText etNombre, etUsername, etPassword;
    private TextView tvListaUsuarios;
    private ArrayList<AppUser> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etNombre         = findViewById(R.id.etNombre);
        etUsername       = findViewById(R.id.etUsername);
        etPassword       = findViewById(R.id.etPassword);
        tvListaUsuarios  = findViewById(R.id.tvListaUsuarios);

        // recibir la lista que viene de MainActivity
        listaUsuarios = (ArrayList<AppUser>) getIntent()
                .getSerializableExtra("listaUsuarios");

        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<>();
        }

        actualizarLista();
    }

    public void guardarUsuario(View v) {
        String nombre   = etNombre.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validación: campos vacíos
        if (nombre.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación: username repetido
        for (AppUser u : listaUsuarios) {
            if (u.getUsername().equals(username)) {
                Toast.makeText(this, "Ese username ya existe", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Crear y agregar el usuario
        AppUser nuevo = new AppUser(nombre, username, password);
        listaUsuarios.add(nuevo);

        Toast.makeText(this, "Usuario " + username + " creado", Toast.LENGTH_SHORT).show();

        // Limpiar campos
        etNombre.setText("");
        etUsername.setText("");
        etPassword.setText("");

        actualizarLista();

        // Devolver la lista actualizada a MainActivity
        Intent resultado = new Intent();
        resultado.putExtra("listaUsuarios", listaUsuarios);
        setResult(RESULT_OK, resultado);
    }

    // Muestra los usuarios creados en pantalla
    private void actualizarLista() {
        if (listaUsuarios.isEmpty()) {
            tvListaUsuarios.setText("Usuarios registrados: ninguno");
            return;
        }
        StringBuilder sb = new StringBuilder("Usuarios registrados:\n");
        for (AppUser u : listaUsuarios) {
            sb.append("• ").append(u.getNombre())
                    .append(" (").append(u.getUsername()).append(")\n");
        }
        tvListaUsuarios.setText(sb.toString());
    }

    // Al presionar atrás también devolvemos la lista
    @Override
    public void onBackPressed() {
        Intent resultado = new Intent();
        resultado.putExtra("listaUsuarios", listaUsuarios);
        setResult(RESULT_OK, resultado);
        super.onBackPressed();
    }
}