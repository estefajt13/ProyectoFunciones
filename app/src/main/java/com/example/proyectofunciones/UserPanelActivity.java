package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class UserPanelActivity extends AppCompatActivity {

    private TextView tvBienvenido;
    private AppUser usuarioActual;

    static ArrayList<AgendaContacto> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        tvBienvenido = findViewById(R.id.tvBienvenido);

        usuarioActual = (AppUser) getIntent().getSerializableExtra("usuario");

        if (usuarioActual != null) {
            tvBienvenido.setText("Hola, " + usuarioActual.getNombre() + "!");
        }

        // cargar contactos guardados
        listaContactos = Archivos.cargarContactos(this);
    }

    public void irCalculadora(View v) {
        Intent intent = new Intent(this, CalculadoraActivity.class);
        startActivity(intent);
    }

    public void irAgenda(View v) {
        Intent intent = new Intent(this, AgendaActivity.class);
        startActivity(intent);
    }

    public void cerrarSesion(View v) {
        finish(); // regresa al MainActivity
    }
}