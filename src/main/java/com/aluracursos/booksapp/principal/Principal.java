package com.aluracursos.booksapp.principal;

import com.aluracursos.booksapp.model.Datos;
import com.aluracursos.booksapp.model.DatosLibros;
import com.aluracursos.booksapp.service.ConsumoAPI;
import com.aluracursos.booksapp.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner scanner = new Scanner(System.in);

    //creo método que muestre un menú
    public void muestraMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        //System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        //System.out.println(datos);

        //Top 10 Libros más descargados
        System.out.println("\n****** Top 10 Libros más descargados *******");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        // búsqueda por parte del título
        System.out.println("\n**** Ingrese el Título del Libro que desea encontrar: ");
        var busquedaLibro = scanner.nextLine();
        var busquedaRegex = busquedaLibro.replace(" ", "+");
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + busquedaRegex);
        //convierto los datos
        var busquedaConvertida = conversor.obtenerDatos(json, Datos.class);
        //tratamiento de datos
        Optional<DatosLibros> libroBuscado = busquedaConvertida.resultados().stream()
                .filter(t -> t.titulo().toUpperCase().contains(busquedaRegex.toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()) {
            System.out.println("Libro encontrado \n" + libroBuscado.get());
        } else {
            System.out.println("Ooops El libro no fue encontrado");
        }

        //Trabajando con estadísticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas() > 0.0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("***** El máximo de descargas es de: " + est.getMax());
        System.out.println("***** El mínimo de descargas es: " + est.getMin());
        System.out.println("***** La media de descargas es de: " + est.getAverage());
        System.out.println("***** Cantidad de registros evaluados: " + est.getCount());




    }
}
