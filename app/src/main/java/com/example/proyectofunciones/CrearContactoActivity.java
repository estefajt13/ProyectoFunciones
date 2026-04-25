package com.example.proyectofunciones;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class CrearContactoActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etCedula, etCorreo, etDireccion;
    private TextView tvTitulo, tvGustos, tvPreferencias, tvFecha;
    private Button btnEditar, btnGuardar, btnFecha;
    private Spinner spinnerDepartamento, spinnerCiudad;

    private ArrayList<String> gustosSeleccionados    = new ArrayList<>();
    private ArrayList<String> preferenciasSeleccionadas = new ArrayList<>();
    private String fechaSeleccionada = "";

    private int posicion = -1;
    private boolean modoEdicion = false;

    // Departamentos y sus ciudades principales
    private HashMap<String, String[]> ciudadesPorDepartamento = new HashMap<>();
    private String[] departamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        etNombre       = findViewById(R.id.etNombre);
        etApellido     = findViewById(R.id.etApellido);
        etCedula       = findViewById(R.id.etCedula);
        etCorreo       = findViewById(R.id.etCorreo);
        etDireccion    = findViewById(R.id.etDireccion);
        tvTitulo       = findViewById(R.id.tvTitulo);
        tvGustos       = findViewById(R.id.tvGustos);
        tvPreferencias = findViewById(R.id.tvPreferencias);
        tvFecha        = findViewById(R.id.tvFecha);
        btnEditar      = findViewById(R.id.btnEditar);
        btnGuardar     = findViewById(R.id.btnGuardar);
        btnFecha       = findViewById(R.id.btnFecha);
        spinnerDepartamento = findViewById(R.id.spinnerDepartamento);
        spinnerCiudad       = findViewById(R.id.spinnerCiudad);

        configurarDepartamentosYCiudades();

        AgendaContacto contacto =
                (AgendaContacto) getIntent().getSerializableExtra("contacto");
        posicion = getIntent().getIntExtra("posicion", -1);

        if (contacto != null) {
            // modo VER
            tvTitulo.setText("Detalle Contacto");
            etNombre.setText(contacto.getNombre());
            etApellido.setText(contacto.getApellido());
            etCedula.setText(String.valueOf(contacto.getCedula()));
            etCorreo.setText(contacto.getCorreo());
            etDireccion.setText(contacto.getDireccion());
            fechaSeleccionada = contacto.getFechaNacimiento();
            tvFecha.setText("Fecha de nacimiento: " + fechaSeleccionada);
            gustosSeleccionados      = contacto.getGustos();
            preferenciasSeleccionadas = contacto.getPreferencias();
            actualizarIndicadores();

            // seleccionar departamento y ciudad guardados
            seleccionarEnSpinner(spinnerDepartamento, contacto.getDepartamento());
            cargarCiudades(contacto.getDepartamento());
            seleccionarEnSpinner(spinnerCiudad, contacto.getCiudad());

            bloquearCampos(true);
            btnEditar.setVisibility(View.VISIBLE);
            btnGuardar.setVisibility(View.GONE);
        } else {
            modoEdicion = true;
            bloquearCampos(false);
            btnEditar.setVisibility(View.GONE);
            btnGuardar.setText("Guardar Contacto");
            btnGuardar.setVisibility(View.VISIBLE);
        }
    }

    private void configurarDepartamentosYCiudades() {
        ciudadesPorDepartamento.put("Amazonas", new String[]{"Leticia", "Puerto Nariño"});
        ciudadesPorDepartamento.put("Antioquia", new String[]{"Medellín", "Bello", "Envigado", "Itagüí", "Rionegro", "Turbo"});
        ciudadesPorDepartamento.put("Arauca", new String[]{"Arauca", "Saravena", "Tame"});
        ciudadesPorDepartamento.put("Atlántico", new String[]{"Barranquilla", "Soledad", "Malambo", "Sabanalarga"});
        ciudadesPorDepartamento.put("Bolívar", new String[]{"Cartagena", "Magangué", "Turbaco"});
        ciudadesPorDepartamento.put("Boyacá", new String[]{"Tunja", "Duitama", "Sogamoso", "Chiquinquirá"});
        ciudadesPorDepartamento.put("Caldas", new String[]{"Manizales", "Villamaría", "La Dorada"});
        ciudadesPorDepartamento.put("Caquetá", new String[]{"Florencia", "San Vicente del Caguán"});
        ciudadesPorDepartamento.put("Casanare", new String[]{"Yopal", "Aguazul", "Villanueva"});
        ciudadesPorDepartamento.put("Cauca", new String[]{"Popayán", "Santander de Quilichao", "Puerto Tejada"});
        ciudadesPorDepartamento.put("Cesar", new String[]{"Valledupar", "Aguachica", "Codazzi"});
        ciudadesPorDepartamento.put("Chocó", new String[]{"Quibdó", "Istmina", "Riosucio"});
        ciudadesPorDepartamento.put("Córdoba", new String[]{"Montería", "Lorica", "Cereté", "Sahagún"});
        ciudadesPorDepartamento.put("Cundinamarca", new String[]{"Bogotá D.C.", "Soacha", "Facatativá", "Zipaquirá", "Chía", "Fusagasugá"});
        ciudadesPorDepartamento.put("Guainía", new String[]{"Inírida"});
        ciudadesPorDepartamento.put("Guaviare", new String[]{"San José del Guaviare"});
        ciudadesPorDepartamento.put("Huila", new String[]{"Neiva", "Pitalito", "Garzón"});
        ciudadesPorDepartamento.put("La Guajira", new String[]{"Riohacha", "Maicao", "Uribia"});
        ciudadesPorDepartamento.put("Magdalena", new String[]{"Santa Marta", "Ciénaga", "Fundación"});
        ciudadesPorDepartamento.put("Meta", new String[]{"Villavicencio", "Acacías", "Granada"});
        ciudadesPorDepartamento.put("Nariño", new String[]{"Pasto", "Tumaco", "Ipiales"});
        ciudadesPorDepartamento.put("Norte de Santander", new String[]{"Cúcuta", "Ocaña", "Pamplona"});
        ciudadesPorDepartamento.put("Putumayo", new String[]{"Mocoa", "Puerto Asís"});
        ciudadesPorDepartamento.put("Quindío", new String[]{"Armenia", "Calarcá", "Montenegro"});
        ciudadesPorDepartamento.put("Risaralda", new String[]{"Pereira", "Dosquebradas", "Santa Rosa de Cabal"});
        ciudadesPorDepartamento.put("San Andrés", new String[]{"San Andrés", "Providencia"});
        ciudadesPorDepartamento.put("Santander", new String[]{"Bucaramanga", "Floridablanca", "Girón", "Barrancabermeja"});
        ciudadesPorDepartamento.put("Sucre", new String[]{"Sincelejo", "Corozal", "Sampués"});
        ciudadesPorDepartamento.put("Tolima", new String[]{"Ibagué", "Espinal", "Melgar"});
        ciudadesPorDepartamento.put("Valle del Cauca", new String[]{"Cali", "Buenaventura", "Palmira", "Tuluá", "Buga"});
        ciudadesPorDepartamento.put("Vaupés", new String[]{"Mitú"});
        ciudadesPorDepartamento.put("Vichada", new String[]{"Puerto Carreño"});

        // ordenar departamentos alfabéticamente
        departamentos = ciudadesPorDepartamento.keySet().toArray(new String[0]);
        Arrays.sort(departamentos);

        ArrayAdapter<String> adapterDep = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, departamentos);
        adapterDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamento.setAdapter(adapterDep);

        // cuando cambia departamento, actualiza ciudades
        spinnerDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarCiudades(departamentos[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void cargarCiudades(String departamento) {
        String[] ciudades = ciudadesPorDepartamento.get(departamento);
        if (ciudades == null) ciudades = new String[]{};
        ArrayAdapter<String> adapterCiu = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ciudades);
        adapterCiu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(adapterCiu);
    }

    private void seleccionarEnSpinner(Spinner spinner, String valor) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter == null) return;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(valor)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    // Abre el calendario
    public void abrirCalendario(View v) {
        if (!modoEdicion) {
            Toast.makeText(this, "Presiona Editar primero", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    fechaSeleccionada = day + "/" + (month + 1) + "/" + year;
                    tvFecha.setText("Fecha: " + fechaSeleccionada);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void activarEdicion(View v) {
        modoEdicion = true;
        bloquearCampos(false);
        tvTitulo.setText("Modificar Contacto");
        btnEditar.setVisibility(View.GONE);
        btnGuardar.setText("Guardar Edición");
        btnGuardar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Puedes editar los datos", Toast.LENGTH_SHORT).show();
    }

    private void bloquearCampos(boolean bloqueado) {
        etNombre.setEnabled(!bloqueado);
        etApellido.setEnabled(!bloqueado);
        etCedula.setEnabled(!bloqueado);
        etCorreo.setEnabled(!bloqueado);
        etDireccion.setEnabled(!bloqueado);
        
        // Controlar habilitación y visibilidad del botón de fecha
        btnFecha.setEnabled(!bloqueado);
        btnFecha.setVisibility(bloqueado ? View.GONE : View.VISIBLE);

        spinnerDepartamento.setEnabled(!bloqueado);
        spinnerCiudad.setEnabled(!bloqueado);

        int color = bloqueado ? 0xFFEEEEEE : 0xFFFFFFFF;
        etNombre.setBackgroundColor(color);
        etApellido.setBackgroundColor(color);
        etCedula.setBackgroundColor(color);
        etCorreo.setBackgroundColor(color);
        etDireccion.setBackgroundColor(color);
    }

    public void irGustos(View v) {
        if (!modoEdicion) {
            Toast.makeText(this, "Presiona Editar primero", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, GustosActivity.class);
        intent.putExtra("gustosSeleccionados", gustosSeleccionados);
        startActivityForResult(intent, 10);
    }

    public void irPreferencias(View v) {
        if (!modoEdicion) {
            Toast.makeText(this, "Presiona Editar primero", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, PreferenciasActivity.class);
        intent.putExtra("preferenciasSeleccionadas", preferenciasSeleccionadas);
        startActivityForResult(intent, 20);
    }

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

    private void actualizarIndicadores() {
        tvGustos.setText(gustosSeleccionados.isEmpty()
                ? "Gustos: ninguno seleccionado"
                : "Gustos: " + gustosSeleccionados.size() + " — " + gustosSeleccionados);
        tvPreferencias.setText(preferenciasSeleccionadas.isEmpty()
                ? "Preferencias: ninguna seleccionada"
                : "Preferencias: " + preferenciasSeleccionadas.size() + " — " + preferenciasSeleccionadas);
    }

    public void guardarContacto(View v) {
        String nombre       = etNombre.getText().toString().trim();
        String apellido     = etApellido.getText().toString().trim();
        String cedulaStr    = etCedula.getText().toString().trim();
        String correo       = etCorreo.getText().toString().trim();
        String direccion    = etDireccion.getText().toString().trim();
        String departamento = spinnerDepartamento.getSelectedItem() != null
                ? spinnerDepartamento.getSelectedItem().toString() : "";
        String ciudad       = spinnerCiudad.getSelectedItem() != null
                ? spinnerCiudad.getSelectedItem().toString() : "";

        if (nombre.isEmpty() || apellido.isEmpty() || cedulaStr.isEmpty()) {
            Toast.makeText(this, "Nombre, apellido y cédula son obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int cedula;
        try {
            cedula = Integer.parseInt(cedulaStr);
        } catch (Exception e) {
            Toast.makeText(this, "La cédula debe ser un número",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        AgendaContacto contacto = new AgendaContacto(
                nombre, apellido, cedula, fechaSeleccionada,
                correo, direccion, ciudad, departamento);
        contacto.setGustos(gustosSeleccionados);
        contacto.setPreferencias(preferenciasSeleccionadas);

        Intent resultado = new Intent();
        resultado.putExtra("contacto", contacto);
        resultado.putExtra("posicion", posicion);
        setResult(RESULT_OK, resultado);
        finish();
    }

    public void volver(View v) {
        finish();
    }
}