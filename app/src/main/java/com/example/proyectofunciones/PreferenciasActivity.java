package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PreferenciasActivity extends AppCompatActivity {

    private CheckBox cbp1, cbp2, cbp3, cbp4, cbp5,
            cbp6, cbp7, cbp8, cbp9, cbp10;

    private String[] opciones = {
            "Comida italiana", "Comida japonesa", "Música pop", "Música rock",
            "Películas de acción", "Películas de terror", "Deportes al aire libre",
            "Gimnasio", "Redes sociales", "Trabajo remoto"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        cbp1  = findViewById(R.id.cbp1);
        cbp2  = findViewById(R.id.cbp2);
        cbp3  = findViewById(R.id.cbp3);
        cbp4  = findViewById(R.id.cbp4);
        cbp5  = findViewById(R.id.cbp5);
        cbp6  = findViewById(R.id.cbp6);
        cbp7  = findViewById(R.id.cbp7);
        cbp8  = findViewById(R.id.cbp8);
        cbp9  = findViewById(R.id.cbp9);
        cbp10 = findViewById(R.id.cbp10);

        // marcar los ya seleccionados si venimos a modificar
        ArrayList<String> yaSeleccionadas =
                (ArrayList<String>) getIntent()
                        .getSerializableExtra("preferenciasSeleccionadas");

        if (yaSeleccionadas != null) {
            CheckBox[] checkboxes = {cbp1,cbp2,cbp3,cbp4,cbp5,
                    cbp6,cbp7,cbp8,cbp9,cbp10};
            for (int i = 0; i < checkboxes.length; i++) {
                if (yaSeleccionadas.contains(opciones[i])) {
                    checkboxes[i].setChecked(true);
                }
            }
        }
    }

    public void guardarPreferencias(View v) {
        ArrayList<String> seleccionadas = new ArrayList<>();

        CheckBox[] checkboxes = {cbp1,cbp2,cbp3,cbp4,cbp5,
                cbp6,cbp7,cbp8,cbp9,cbp10};
        for (int i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].isChecked()) {
                seleccionadas.add(opciones[i]);
            }
        }

        if (seleccionadas.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos una preferencia",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultado = new Intent();
        resultado.putExtra("preferenciasSeleccionadas", seleccionadas);
        setResult(RESULT_OK, resultado);
        finish();
    }
}