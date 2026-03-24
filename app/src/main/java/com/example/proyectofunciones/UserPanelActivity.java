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

    // lista de contactos de la agenda — vive aquí para que sea compartida
    static ArrayList<AgendaContacto> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        tvBienvenido = findViewById(R.id.tvBienvenido);

        // recibir el usuario que se logueó
        usuarioActual = (AppUser) getIntent().getSerializableExtra("usuario");

        if (usuarioActual != null) {
            tvBienvenido.setText("Hola, " + usuarioActual.getNombre() + "!");
        }
    }

    public void irCalculadora(View v) {
        Intent intent = new Intent(this, CalculadoraActivity.class);
        startActivity(intent);
    }

    public void irAgenda(View v) {
        Intent intent = new Intent(this, AgendaActivity.class);
        startActivity(intent);
    }
}