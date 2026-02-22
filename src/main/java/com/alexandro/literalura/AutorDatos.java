package com.alexandro.literalura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonAlias;


@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDatos(

        @JsonAlias("name")
        String name,

        @JsonAlias("birth_year")
        Integer birthYear,

        @JsonAlias("death_year")
        Integer deathYear

) {}