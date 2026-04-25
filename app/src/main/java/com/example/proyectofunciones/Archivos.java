package com.example.proyectofunciones;

import android.content.Context;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Archivos {

    // ── USUARIOS ──────────────────────────────────────

    public static void guardarUsuarios(Context ctx, ArrayList<AppUser> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ctx.openFileOutput("usuarios.dat", Context.MODE_PRIVATE));
            oos.writeObject(lista);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<AppUser> cargarUsuarios(Context ctx) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    ctx.openFileInput("usuarios.dat"));
            ArrayList<AppUser> lista = (ArrayList<AppUser>) ois.readObject();
            ois.close();
            return lista;
        } catch (Exception e) {
            return new ArrayList<>(); // si no existe el archivo, lista vacía
        }
    }

    // ── CONTACTOS ─────────────────────────────────────

    public static void guardarContactos(Context ctx, ArrayList<AgendaContacto> lista) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ctx.openFileOutput("contactos.dat", Context.MODE_PRIVATE));
            oos.writeObject(lista);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<AgendaContacto> cargarContactos(Context ctx) {
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    ctx.openFileInput("contactos.dat"));
            ArrayList<AgendaContacto> lista = (ArrayList<AgendaContacto>) ois.readObject();
            ois.close();
            return lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}