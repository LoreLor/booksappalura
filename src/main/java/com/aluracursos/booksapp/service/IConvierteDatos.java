package com.aluracursos.booksapp.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
