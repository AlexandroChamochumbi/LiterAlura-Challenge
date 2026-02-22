package com.alexandro.literalura;

import com.alexandro.literalura.modelo.Autor;
import com.alexandro.literalura.modelo.Libro;
import com.alexandro.literalura.Repositories.AutorRepository;
import com.alexandro.literalura.Repositories.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try (Scanner teclado = new Scanner(System.in)) {
            int opcion = -1;

            while (opcion != 0) {

            System.out.println("""
                    ------------------------------
                    1 - Buscar libro por título
                    2 - Listar libros guardados
                    3 - Listar libros por idioma
                    4 - Listar autores
                    5 - Listar autores vivos en determinado año
                    6 - Mostrar estadísticas
                    7 - Estadísticas de descargas
                    8 - Top 10 libros más descargados
                    9 - Buscar autor por nombre
                    10 - Listar autores nacidos después de determinado año
                    0 - Salir
                    ------------------------------
                    """);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
                continue;
            }

            switch (opcion) {

                case 1 -> {
                    System.out.println("Escribe el título del libro:");
                    String titulo = teclado.nextLine();
                    buscarLibroPorTitulo(titulo);
                }

                case 2 -> listarLibros();

                case 3 -> {
                    System.out.println("Escribe el idioma (ej: en, es, fr):");
                    String idioma = teclado.nextLine();
                    listarPorIdioma(idioma);
                }

                case 4 -> listarAutores();

                case 5 -> {
                    System.out.println("Ingrese el año:");
                    try {
                        int anio = Integer.parseInt(teclado.nextLine());
                        listarAutoresVivos(anio);
                    } catch (NumberFormatException e) {
                        System.out.println("Año inválido.");
                    }
                }
                case 6 -> mostrarEstadisticas();

                case 7 -> estadisticasDescargas();

                case 8 -> top10Libros();

                case 9 -> {
                    System.out.println("Escribe el nombre del autor:");
                    String nombre = teclado.nextLine();
                    buscarAutorPorNombre(nombre);
                }

                case 10 -> {
                    System.out.println("Ingrese el año:");
                    try {
                        int anio = Integer.parseInt(teclado.nextLine());
                        autoresNacidosDespues(anio);
                    } catch (NumberFormatException e) {
                        System.out.println("Año inválido.");
                    }
                }

                case 0 -> System.out.println("Cerrando aplicación...");

                default -> System.out.println("Opción inválida.");
            }
        }
        }
    }

    private void buscarLibroPorTitulo(String tituloBuscado) throws Exception {

        ConsumoApi consumo = new ConsumoApi();
        String url = "https://gutendex.com/books/?search=" + tituloBuscado.replace(" ", "%20");

        String json = consumo.obtenerDatos(url);

        ObjectMapper mapper = new ObjectMapper();
        RespuestaDatos datos = mapper.readValue(json, RespuestaDatos.class);

        if (datos.getResults().isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }

        LibroDatos libroDatos = datos.getResults().get(0);

        String titulo = libroDatos.title();
        String idioma = libroDatos.languages().get(0);
        Integer descargas = libroDatos.downloadCount();

        String nombreAutor = libroDatos.authors().get(0).name();
        Integer nacimiento = libroDatos.authors().get(0).birthYear();
        Integer fallecimiento = libroDatos.authors().get(0).deathYear();

        Optional<Autor> autorExistente = autorRepository.findByNombre(nombreAutor);

        Autor autor = autorExistente.orElseGet(() ->
                autorRepository.save(new Autor(nombreAutor, nacimiento, fallecimiento))
        );

        if (libroRepository.existsByTitulo(titulo)) {
            System.out.println("El libro ya está guardado.");
            return;
        }
        Libro libro = new Libro(titulo, idioma, descargas, autor);
        libroRepository.save(libro);

        System.out.println("Libro guardado exitosamente:");
        System.out.println(libro);
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos(int anio) {
        if (anio <= 0) {
            System.out.println("Ingrese un año válido.");
            return;
        }
        List<Autor> conFallecimiento = autorRepository
            .findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);

        List<Autor> sinFallecimiento = autorRepository
            .findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(anio);

        conFallecimiento.addAll(sinFallecimiento);

        if (conFallecimiento.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            conFallecimiento.forEach(System.out::println);
        }

        }

    private void mostrarEstadisticas() {
        long cantidadIngles = libroRepository.countByIdioma("en");
        long cantidadEspanol = libroRepository.countByIdioma("es");

        System.out.println("""
            ------------------------------
             Estadísticas de libros
            ------------------------------
            Inglés (en): %d libros
            Español (es): %d libros
            ------------------------------
            """.formatted(cantidadIngles, cantidadEspanol));
}
    private void estadisticasDescargas() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        DoubleSummaryStatistics stats = libros.stream()
            .filter(l -> l.getNumeroDescargas() != null)
            .collect(Collectors.summarizingDouble(Libro::getNumeroDescargas));

        System.out.println("""
            Estadísticas de descargas
            --------------------------------
            Cantidad de libros: %d
            Total descargas: %.0f
            Promedio descargas: %.2f
            Máximo descargas: %.0f
            Mínimo descargas: %.0f
            --------------------------------
            """.formatted(
            stats.getCount(),
            stats.getSum(),
            stats.getAverage(),
            stats.getMax(),
            stats.getMin()
        ));
    }

    private void top10Libros() {

        List<Libro> top = libroRepository.findTop10ByOrderByNumeroDescargasDesc();

        if (top.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("Top 10 libros más descargados:");
        top.forEach(System.out::println);
    }

    private void buscarAutorPorNombre(String nombre) {

        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void autoresNacidosDespues(int anio) {
     List<Autor> autores = autorRepository.findByAnioNacimientoGreaterThan(anio);
     if (autores.isEmpty()) {
        System.out.println("No se encontraron autores.");
     } else {
        autores.forEach(System.out::println);
    }
}
}