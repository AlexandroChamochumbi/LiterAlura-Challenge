package com.alexandro.literalura;

import com.alexandro.literalura.modelo.Autor;
import com.alexandro.literalura.modelo.Libro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(LiteraluraApplication.class, args);

        ConsumoApi consumo = new ConsumoApi();
        String url = "https://gutendex.com/books/?search=dostoevsky";
        String json = consumo.obtenerDatos(url);

        ObjectMapper mapper = new ObjectMapper();

        RespuestaDatos datos = mapper.readValue(json, RespuestaDatos.class);

        datos.getResults().forEach(libroDatos -> {

            List<Autor> autores = libroDatos.getAuthors()
                    .stream()
                    .map(a -> new Autor(a.getName()))
                    .toList();

            Libro libro = new Libro(
                    libroDatos.getTitle(),
                    autores,
                    libroDatos.getLanguages(),
                    libroDatos.getDownloadCount()
            );

            System.out.println(libro);
        });
    }
}