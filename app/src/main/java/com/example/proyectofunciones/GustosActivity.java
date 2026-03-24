package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GustosActivity extends AppCompatActivity {

    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;

    // los textos de cada checkbox en orden
    private String[] opciones = {
            "Música", "Deportes", "Cine", "Videojuegos", "Lectura",
            "Viajes", "Cocina", "Arte y pintura", "Fotografía", "Tecnología"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gustos);

        cb1  = findViewById(R.id.cb1);
        cb2  = findViewById(R.id.cb2);
        cb3  = findViewById(R.id.cb3);
        cb4  = findViewById(R.id.cb4);
        cb5  = findViewById(R.id.cb5);
        cb6  = findViewById(R.id.cb6);
        cb7  = findViewById(R.id.cb7);
        cb8  = findViewById(R.id.cb8);
        cb9  = findViewById(R.id.cb9);
        cb10 = findViewById(R.id.cb10);

        // si venimos a modificar, marcar los que ya estaban seleccionados
        ArrayList<String> yaSeleccionados =
                (ArrayList<String>) getIntent().getSerializableExtra("gustosSeleccionados");

        if (yaSeleccionados != null) {
            CheckBox[] checkboxes = {cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10};
            for (int i = 0; i < checkboxes.length; i++) {
                if (yaSeleccionados.contains(opciones[i])) {
                    checkboxes[i].setChecked(true);
                }
            }
        }
    }

    public void guardarGustos(View v) {
        ArrayList<String> seleccionados = new ArrayList<>();

        CheckBox[] checkboxes = {cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10};
        for (int i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].isChecked()) {
                seleccionados.add(opciones[i]);
            }
        }

        if (seleccionados.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos un gusto",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultado = new Intent();
        resultado.putExtra("gustosSeleccionados", seleccionados);
        setResult(RESULT_OK, resultado);
        finish(); // regresa a CrearContactoActivity
    }
}