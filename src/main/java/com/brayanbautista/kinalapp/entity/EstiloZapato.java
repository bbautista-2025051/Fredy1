package com.brayanbautista.kinalapp.entity;

public enum EstiloZapato {
    LUKA_2("Luka 2"),
    LUKA_6("Luka 6"),
    LUKA_9("Luka 9"),
    MINERO("Minero"),
    VAQUERA("Vaquera"),
    LUKA("Luka");

    private final String etiqueta;

    EstiloZapato(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
