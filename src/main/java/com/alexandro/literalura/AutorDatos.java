package com.alexandro.literalura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDatos {

    private String name;

    public String getName() {
        return name;
    }
}