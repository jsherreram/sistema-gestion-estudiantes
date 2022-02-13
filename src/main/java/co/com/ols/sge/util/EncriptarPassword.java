/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.util;

import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author jsherreram
 */
public class EncriptarPassword {

    public static void main(String[] args) {

        String password = JOptionPane.showInputDialog("Ingrese la contraseña a encriptar");
        System.out.println("Contraseña a encriptar: " + password);

        int num = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de contraseñas a encriptar"));
        System.out.println("Ingrese el número de contraseñas a encriptar: " + num);
        for (int i = 0; i < num; i++) {
            System.out.println("Contraseña " + (i + 1) + " encriptada: " + encriptarPassword(password));
        }

    }

    public static String encriptarPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
