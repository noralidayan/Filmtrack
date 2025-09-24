package com.filmtrack.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogInDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

                // "Base de datos" simulada
                List<Usuario> usuarios = new ArrayList<>();
                usuarios.add(new Usuario("norali@gmail.com", "1234"));
                usuarios.add(new Usuario("pepe@gmail.com", "abcd"));

                System.out.print("Ingrese email: ");
                String email = sc.nextLine();

                System.out.print("Ingrese clave: ");
                String clave = sc.nextLine();

                // Llamamos al método de login
                boolean exito = login(email, clave, usuarios);

                if (exito) {
                    System.out.println("✅ Login exitoso. Bienvenido " + email);
                } else {
                    System.out.println("❌ Credenciales inválidas");
                }

                sc.close();
            }

            // Método separado para validar login
            public static boolean login(String email, String clave, List<Usuario> usuarios) {
                for (Usuario u : usuarios) {
                    if (u.getEmail().equals(email) && u.getClave().equals(clave)) {
                        return true;
                    }
                }
                return false;
            }
        }
       /* Scanner sc = new Scanner(System.in);

        // "Base de datos" simulada
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("norali@gmail.com", "1234"));
        usuarios.add(new Usuario("pepe@gmail.com", "abcd"));

        System.out.print("Ingrese email: ");
        String email = sc.nextLine();

        System.out.print("Ingrese clave: ");
        String clave = sc.nextLine();

        boolean encontrado = false;
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getClave().equals(clave)) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            System.out.println("✅ Login exitoso. Bienvenido " + email);
        } else {
            System.out.println("❌ Credenciales inválidas");
        }
    }
}*/
