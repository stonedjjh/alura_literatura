package com.aluraapi.literatura.util;

import java.util.List;

public class LibroUtils {
    public static String obtenerIdiomaPrincipal(List<String> idiomas) {
        return (idiomas == null || idiomas.isEmpty()) ? "desconocido" : idiomas.get(0);
    }
}