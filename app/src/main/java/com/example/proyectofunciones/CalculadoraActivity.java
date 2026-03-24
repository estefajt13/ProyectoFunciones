package com.example.proyectofunciones;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class CalculadoraActivity extends AppCompatActivity {

    private Button b9,b8,b7,b6,b5,b4,b3,b2,b1,b0,bbc,bpunto,bsuma,bresta,bmultiplicacion, bdivision, bresultado;
    private EditText  caja;
    private TextView tvHistorial, tvMemoria;

    String cadena = "";
    double acumulado = 0;
    String operacion = "";
    double memoria = 0;
    ArrayList<Double> historialMemoria = new ArrayList<>(); // guarda todos los valores

    // historial: guardamos las últimas 3 operaciones
    String historial = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculadora);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b9 = findViewById(R.id.b9);
        b8 = findViewById(R.id.b8);
        b7 = findViewById(R.id.b7);
        b6 = findViewById(R.id.b6);
        b5 = findViewById(R.id.b5);
        b4 = findViewById(R.id.b4);
        b3 = findViewById(R.id.b3);
        b2 = findViewById(R.id.b2);
        b1 = findViewById(R.id.b1);
        b0 = findViewById(R.id.b0);

        bbc = findViewById(R.id.bbc);
        bpunto = findViewById(R.id.bpunto);

        caja = findViewById(R.id.caja);

        bsuma = findViewById(R.id.bsuma);
        bresta = findViewById(R.id.bresta);
        bmultiplicacion = findViewById(R.id.bmultiplicacion);
        bdivision = findViewById(R.id.bdivision);

        tvHistorial = findViewById(R.id.tvHistorial);
        tvMemoria   = findViewById(R.id.tvMemoria);
    }


    // VALIDACIONES
    private boolean puedePonerOperador() {
        if (cadena.isEmpty()) {
            Toast.makeText(this, "Ingresa un número primero", Toast.LENGTH_SHORT).show();
            return false;
        }
        char ultimo = cadena.charAt(cadena.length() - 1);
        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            Toast.makeText(this, "Ya hay un operador", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void aplicarOperacion() {
        int posOp = Math.max(
                Math.max(cadena.lastIndexOf('+'), cadena.lastIndexOf('-')),
                Math.max(cadena.lastIndexOf('*'), cadena.lastIndexOf('/'))
        );
        String ultimoNum = (posOp == -1) ? cadena : cadena.substring(posOp + 1);

        if (ultimoNum.isEmpty()) return;

        double numeroActual = Double.parseDouble(ultimoNum);

        if (operacion.isEmpty()) {
            acumulado = numeroActual;
        } else {
            switch (operacion) {
                case "+": acumulado = acumulado + numeroActual; break;
                case "-": acumulado = acumulado - numeroActual; break;
                case "*": acumulado = acumulado * numeroActual; break;
                case "/":
                    if (numeroActual == 0) {
                        Toast.makeText(this, "No se puede dividir entre 0", Toast.LENGTH_LONG).show();
                        return;
                    }
                    acumulado = acumulado / numeroActual;
                    break;
            }
        }
    }

    // OPERADORES
    public void suma(View v) {
        if (!puedePonerOperador()) return;
        aplicarOperacion();                    // calcula lo pendiente
        agregarHistorial(acumulado);           // guarda en historial
        cadena = formatear(acumulado) + "+";   // muestra resultado + operador
        operacion = "+";
        visualizar();
    }

    public void resta(View v) {
        if (!puedePonerOperador()) return;
        aplicarOperacion();
        agregarHistorial(acumulado);
        cadena = formatear(acumulado) + "-";
        operacion = "-";
        visualizar();
    }

    public void multiplicacion(View v) {
        if (!puedePonerOperador()) return;
        aplicarOperacion();
        agregarHistorial(acumulado);
        cadena = formatear(acumulado) + "*";
        operacion = "*";
        visualizar();
    }

    public void division(View v) {
        if (!puedePonerOperador()) return;
        aplicarOperacion();
        agregarHistorial(acumulado);
        cadena = formatear(acumulado) + "/";
        operacion = "/";
        visualizar();
    }

    // IGUAL
    public void igual(View v) {
        if (cadena.isEmpty() || operacion.isEmpty()) {
            Toast.makeText(this, "Operación incompleta", Toast.LENGTH_SHORT).show();
            return;
        }
        char ultimo = cadena.charAt(cadena.length() - 1);
        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            Toast.makeText(this, "Falta el segundo número", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int posOp = Math.max(
                    Math.max(cadena.lastIndexOf('+'), cadena.lastIndexOf('-')),
                    Math.max(cadena.lastIndexOf('*'), cadena.lastIndexOf('/'))
            );

            // guardar el segundo número antes de limpiar
            String segundoNumStr = cadena.substring(posOp + 1);
            double segundoNum = Double.parseDouble(segundoNumStr);

            // guardar lo que necesitamos para el historial
            double primerNum   = acumulado;
            String opAntes     = operacion;

            cadena = segundoNumStr;
            aplicarOperacion();

            // historial con operación completa
            String linea = formatear(primerNum) + " " + opAntes + " "
                    + formatear(segundoNum) + " = " + formatear(acumulado);
            historial = linea + "\n" + historial;
            String[] lineas = historial.split("\n");
            if (lineas.length > 3) {
                historial = lineas[0] + "\n" + lineas[1] + "\n" + lineas[2];
            }
            tvHistorial.setText(historial);

            cadena = formatear(acumulado);
            operacion = "";
            visualizar();

        } catch (Exception e) {
            Toast.makeText(this, "Error en la operación", Toast.LENGTH_SHORT).show();
            cadena = "";
            operacion = "";
            visualizar();
        }
    }

    public void borrar(View v) {
        if (!cadena.isEmpty()) {
            char ultimo = cadena.charAt(cadena.length() - 1);
            if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
                operacion = "";
            }
            cadena = cadena.substring(0, cadena.length() - 1);
            visualizar();
        }
    }

    //  HISTORIAL
    private void agregarHistorial(double valor) {
        if (!operacion.isEmpty()) {
            String linea = formatear(valor) + " " + operacion;
            historial = linea + "\n" + historial;
            String[] lineas = historial.split("\n");
            if (lineas.length > 3) {
                historial = lineas[0] + "\n" + lineas[1] + "\n" + lineas[2];
            }
            tvHistorial.setText(historial);
        }
    }

    // MEMORIA
    public void memoria(View v) {
        if (cadena.isEmpty()) {
            Toast.makeText(this, "No hay número para guardar", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // solo guardar si cadena es un número puro, no "5+"
            char ultimo = cadena.charAt(cadena.length() - 1);
            if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
                Toast.makeText(this, "Completa la operación antes de guardar",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            memoria = Double.parseDouble(cadena);
            historialMemoria.add(memoria);
            actualizarMemoriaDisplay();
            Toast.makeText(this, "Guardado en memoria: " + formatear(memoria),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Completa la operación antes de guardar",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarMemoria(View v) {
        if (historialMemoria.isEmpty()) {
            Toast.makeText(this, "No hay nada en memoria", Toast.LENGTH_SHORT).show();
            return;
        }
        // muestra el valor guardado en la caja listo para usar
        cadena = formatear(memoria);
        operacion = ""; // limpia operación pendiente
        acumulado = memoria; // lo deja listo para seguir operando
        visualizar();
        Toast.makeText(this, "Memoria recuperada: " + formatear(memoria),
                Toast.LENGTH_SHORT).show();
    }

    private void actualizarMemoriaDisplay() {
        if (historialMemoria.isEmpty()) {
            tvMemoria.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder("Memoria: ");
        for (int i = 0; i < historialMemoria.size(); i++) {
            sb.append(formatear(historialMemoria.get(i)));
            if (i < historialMemoria.size() - 1) sb.append(", ");
        }
        tvMemoria.setText(sb.toString());
    }

    // quita el ".0" cuando el resultado es entero
    private String formatear(double num) {
        if (num == (long) num) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    // PUNTO DECIMAL
    public void punto(View v) {
        int ultimoOp = Math.max(
                Math.max(cadena.lastIndexOf('+'), cadena.lastIndexOf('-')),
                Math.max(cadena.lastIndexOf('*'), cadena.lastIndexOf('/'))
        );
        String ultimoNumero = cadena.substring(ultimoOp + 1);
        if (!ultimoNumero.contains(".")) {
            cadena += ".";
            visualizar();
        }
    }

    //NUMEROS
    public void t0(View v){

        cadena = cadena+"0";
        visualizar();
    }
    public void t1(View v){

        cadena = cadena+"1";
        visualizar();
    }
    public void t2(View v){

        cadena = cadena+"2";
        visualizar();
    }

    public void t3(View v){

        cadena = cadena+"3";
        visualizar();
    }
    public void t4(View v){

        cadena = cadena+"4";
        visualizar();
    }
    public void t5(View v){

        cadena = cadena+"5";
        visualizar();
    }
    public void t6(View v){

        cadena = cadena+"6";
        visualizar();
    }
    public void t7(View v){

        cadena = cadena+"7";
        visualizar();
    }

    public void t8(View v){

        cadena = cadena+"8";
        visualizar();
    }

    public void t9(View v){

        cadena = cadena+"9";
        visualizar();
    }

    public void visualizar() {
        caja.setText(cadena.isEmpty() ? "0" : cadena);
    }
}