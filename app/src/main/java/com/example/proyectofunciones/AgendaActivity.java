package com.example.proyectofunciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AgendaActivity extends AppCompatActivity {

    private ListView lvContactos;
    private EditText etBuscar;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> nombresVisibles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        lvContactos = findViewById(R.id.lvContactos);
        etBuscar    = findViewById(R.id.etBuscar);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nombresVisibles);
        lvContactos.setAdapter(adapter);

        actualizarLista();

        // clic en un contacto de la lista → abrir para modificar
        lvContactos.setOnItemClickListener((parent, view, position, id) -> {
            AgendaContacto contacto = UserPanelActivity.listaContactos.get(position);
            Intent intent = new Intent(this, CrearContactoActivity.class);
            intent.putExtra("contacto", contacto);
            intent.putExtra("posicion", position);
            startActivityForResult(intent, 2);
        });
    }

    // se llama cada vez que volvemos a esta pantalla
    @Override
    protected void onResume() {
        super.onResume();
        actualizarLista();
    }

    public void nuevoContacto(View v) {
        Intent intent = new Intent(this, CrearContactoActivity.class);
        startActivityForResult(intent, 1); // 1 = nuevo, 2 = modificar
    }

    public void buscarContacto(View v) {
        String textoBuscar = etBuscar.getText().toString().trim();
        if (textoBuscar.isEmpty()) {
            Toast.makeText(this, "Ingresa una cédula para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        int cedula = Integer.parseInt(textoBuscar);

        // buscar en la lista
        for (int i = 0; i < UserPanelActivity.listaContactos.size(); i++) {
            AgendaContacto c = UserPanelActivity.listaContactos.get(i);
            if (c.getCedula() == cedula) {
                // encontrado — abrir para modificar
                Intent intent = new Intent(this, CrearContactoActivity.class);
                intent.putExtra("contacto", c);
                intent.putExtra("posicion", i);
                startActivityForResult(intent, 2);
                return;
            }
        }
        Toast.makeText(this, "No se encontró ningún contacto con esa cédula",
                Toast.LENGTH_SHORT).show();
    }

    // actualiza el ListView con los contactos actuales
    private void actualizarLista() {
        nombresVisibles.clear();
        for (AgendaContacto c : UserPanelActivity.listaContactos) {
            nombresVisibles.add(c.getNombre() + " " + c.getApellido()
                    + " — CC: " + c.getCedula());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            AgendaContacto contacto =
                    (AgendaContacto) data.getSerializableExtra("contacto");

            if (requestCode == 1) {
                // nuevo contacto
                UserPanelActivity.listaContactos.add(contacto);
            } else if (requestCode == 2) {
                // modificar contacto existente
                int posicion = data.getIntExtra("posicion", -1);
                if (posicion >= 0) {
                    UserPanelActivity.listaContactos.set(posicion, contacto);
                }
            }
            actualizarLista();
        }
    }
}