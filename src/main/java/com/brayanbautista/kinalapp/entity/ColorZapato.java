package com.brayanbautista.kinalapp.entity;

public enum ColorZapato {
    NEGRO("Negro"),
    CAFE("Café"),
    GENA("Gena"),
    BEIGE("Beige"),
    BLANCO("Blanco");

    private final String etiqueta;

    ColorZapato(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
