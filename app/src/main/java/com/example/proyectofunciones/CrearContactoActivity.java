package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CrearContactoActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etCedula, etFecha,
            etCorreo, etDireccion, etCiudad, etDepartamento;
    private TextView tvTitulo, tvGustos, tvPreferencias;

    // guardamos los seleccionados cuando regresan de Gustos/Preferencias
    private ArrayList<String> gustosSeleccionados    = new ArrayList<>();
    private ArrayList<String> preferenciasSeleccionadas = new ArrayList<>();

    private int posicion = -1; // -1 = nuevo, >= 0 = modificando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        etNombre      = findViewById(R.id.etNombre);
        etApellido    = findViewById(R.id.etApellido);
        etCedula      = findViewById(R.id.etCedula);
        etFecha       = findViewById(R.id.etFecha);
        etCorreo      = findViewById(R.id.etCorreo);
        etDireccion   = findViewById(R.id.etDireccion);
        etCiudad      = findViewById(R.id.etCiudad);
        etDepartamento= findViewById(R.id.etDepartamento);
        tvTitulo      = findViewById(R.id.tvTitulo);
        tvGustos      = findViewById(R.id.tvGustos);
        tvPreferencias= findViewById(R.id.tvPreferencias);

        // verificar si venimos a modificar un contacto existente
        AgendaContacto contacto =
                (AgendaContacto) getIntent().getSerializableExtra("contacto");
        posicion = getIntent().getIntExtra("posicion", -1);

        if (contacto != null) {
            // modo modificar — cargar los datos existentes
            tvTitulo.setText("Modificar Contacto");
            etNombre.setText(contacto.getNombre());
            etApellido.setText(contacto.getApellido());
            etCedula.setText(String.valueOf(contacto.getCedula()));
            etFecha.setText(contacto.getFechaNacimiento());
            etCorreo.setText(contacto.getCorreo());
            etDireccion.setText(contacto.getDireccion());
            etCiudad.setText(contacto.getCiudad());
            etDepartamento.setText(contacto.getDepartamento());
            gustosSeleccionados     = contacto.getGustos();
            preferenciasSeleccionadas = contacto.getPreferencias();
            actualizarIndicadores();
        }
    }

    public void irGustos(View v) {
        Intent intent = new Intent(this, GustosActivity.class);
        // pasar los ya seleccionados para que aparezcan marcados
        intent.putExtra("gustosSeleccionados", gustosSeleccionados);
        startActivityForResult(intent, 10);
    }

    public void irPreferencias(View v) {
        Intent intent = new Intent(this, PreferenciasActivity.class);
        intent.putExtra("preferenciasSeleccionadas", preferenciasSeleccionadas);
        startActivityForResult(intent, 20);
    }

    // recibe los resultados de Gustos y Preferencias
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 10) {
                gustosSeleccionados =
                        (ArrayList<String>) data.getSerializableExtra("gustosSeleccionados");
                actualizarIndicadores();
            } else if (requestCode == 20) {
                preferenciasSeleccionadas =
                        (ArrayList<String>) data.getSerializableExtra("preferenciasSeleccionadas");
                actualizarIndicadores();
            }
        }
    }

    // muestra cuántos gustos/preferencias se seleccionaron
    private void actualizarIndicadores() {
        if (gustosSeleccionados.isEmpty()) {
            tvGustos.setText("Gustos: ninguno seleccionado");
        } else {
            tvGustos.setText("Gustos: " + gustosSeleccionados.size()
                    + " seleccionados — " + gustosSeleccionados.toString());
        }

        if (preferenciasSeleccionadas.isEmpty()) {
            tvPreferencias.setText("Preferencias: ninguna seleccionada");
        } else {
            tvPreferencias.setText("Preferencias: " + preferenciasSeleccionadas.size()
                    + " seleccionadas — " + preferenciasSeleccionadas.toString());
        }
    }

    public void guardarContacto(View v) {
        String nombre      = etNombre.getText().toString().trim();
        String apellido    = etApellido.getText().toString().trim();
        String cedulaStr   = etCedula.getText().toString().trim();
        String fecha       = etFecha.getText().toString().trim();
        String correo      = etCorreo.getText().toString().trim();
        String direccion   = etDireccion.getText().toString().trim();
        String ciudad      = etCiudad.getText().toString().trim();
        String departamento= etDepartamento.getText().toString().trim();

        // validaciones
        if (nombre.isEmpty() || apellido.isEmpty() || cedulaStr.isEmpty()) {
            Toast.makeText(this, "Nombre, apellido y cédula son obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int cedula;
        try {
            cedula = Integer.parseInt(cedulaStr);
        } catch (Exception e) {
            Toast.makeText(this, "La cédula debe ser un número", Toast.LENGTH_SHORT).show();
            return;
        }

        // crear el objeto con todos los datos
        AgendaContacto contacto = new AgendaContacto(
                nombre, apellido, cedula, fecha, correo, direccion, ciudad, departamento);
        contacto.setGustos(gustosSeleccionados);
        contacto.setPreferencias(preferenciasSeleccionadas);

        // devolver a AgendaActivity
        Intent resultado = new Intent();
        resultado.putExtra("contacto", contacto);
        resultado.putExtra("posicion", posicion);
        setResult(RESULT_OK, resultado);
        finish();
    }
}