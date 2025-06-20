# LiterAlura 📚

CHALLENGER LITERALURA, desafio propuesto por Alura Latam.

LiterAlura es una aplicación de consola y escritorio (GUI) diseñada para interactuar con la API de Gutendex y gestionar una colección local de libros y autores. Este proyecto te permite buscar, registrar y consultar información sobre libros y sus autores de una manera sencilla y eficiente.

## Tecnologías Utilizadas

* **Java 17+**: Lenguaje de programación principal del proyecto.
* **Spring Boot**: Framework que facilita la creación de aplicaciones Java empresariales, proporcionando una base sólida y una configuración simplificada.
* **Maven**: Herramienta de automatización de construcción y gestión de dependencias.
* **PostgreSQL**: Sistema de gestión de bases de datos relacional robusto y de código abierto, utilizado para persistir la información de libros y autores localmente.
* **Spring Data JPA**: Abstracción de JPA (Java Persistence API) que simplifica la implementación de las capas de acceso a datos, interactuando con PostgreSQL.
* **Jackson**: Librería utilizada internamente por Spring Boot para la serialización y deserialización de objetos Java a/desde JSON (Data Transfer Objects).
* **RestTemplate**: Cliente HTTP de Spring utilizado para consumir la API REST de Gutendex.
* **Swing (Java Swing)**: Utilizado para la creación de la interfaz de usuario gráfica (GUI) de la aplicación, proporcionando un menú interactivo.

## Estructura del Proyecto

El proyecto sigue una estructura de paquetes organizada para mantener la modularidad y la separación de responsabilidades:

* `com.aluraapi.literatura.client`: Contiene las interfaces y las implementaciones para interactuar con la API externa de Gutendex, manejando las peticiones HTTP y la recepción de datos.
    * `GutendexClient`: Interfaz que define el contrato para el cliente de Gutendex.
    * `GutendexClientImpl`: Implementación concreta de `GutendexClient` que utiliza `RestTemplate` para las llamadas a la API.
* `com.aluraapi.literatura.controller`: Podría contener controladores REST si la aplicación tuviera una API web, o se mantiene para una futura expansión.
    * `LibroController`: Potencial controlador para la gestión de libros.
* `com.aluraapi.literatura.dto`: Almacena las clases DTO (Data Transfer Objects) que modelan la estructura de los datos intercambiados con la API externa (Gutendex).
    * `AutorDTO`, `GutendexResponse`, `LibroDTO`: Representan las estructuras de autor, la respuesta general de Gutendex y la información de libro, respectivamente, tal como vienen de la API.
* `com.aluraapi.literatura.model`: Define las entidades de la base de datos local de la aplicación.
    * `Autor`: Entidad que representa a un autor, mapeada a una tabla en PostgreSQL.
    * `Libro`: Entidad que representa un libro, mapeada a una tabla en PostgreSQL.
* `com.aluraapi.literatura.principal`: Contiene la clase principal de la aplicación que orquesta la interacción del usuario.
    * `MenuOpciones`: Clase principal que presenta el menú gráfico al usuario y delega las operaciones a los procesos correspondientes.
* `com.aluraapi.literatura.procesos`: Agrupa la lógica de negocio específica para cada funcionalidad o "proceso" que el usuario puede ejecutar desde el menú.
    * `AutoresVivosEnAnio`, `AutoresVivosEntreAnios`, `BuscarAutorPorNombre`, `BusquedaLibroPorTitulo`, `LibrosRegistradosPorIdioma`, `ListarAutoresRegistrados`, `ListarLibrosRegistrados`, `Top13Gutendex`, `Top13LibrosPorIdiomaGutendex`, `Top13RegistroLocal`: Cada una encapsula una operación específica de la aplicación.
* `com.aluraapi.literatura.repository`: Contiene las interfaces de los repositorios de datos que interactúan con la base de datos PostgreSQL.
    * `AutorRepository`, `LibroRepository`: Interfaces de Spring Data JPA para operaciones CRUD y consultas sobre las entidades `Autor` y `Libro`.
* `com.aluraapi.literatura.service`: Alberga la lógica de negocio principal y coordina las operaciones entre los clientes de API y los repositorios de datos.
    * `AutorService`, `LibroService`: Clases de servicio que implementan las reglas de negocio para autores y libros.
* `com.aluraapi.literatura.util`: Contiene clases de utilidad o métodos auxiliares reutilizables.
    * `LibroUtils`: Clase con métodos auxiliares, como la obtención del idioma principal de una lista de idiomas.

## ️ Configuración y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/Oscar1900/desafio-LiterAlura]
    cd desafio-LiterAlura
    ```
2.  **Configurar PostgreSQL:**
    * Asegúrate de tener una instancia de PostgreSQL en ejecución.
    * Crea una base de datos llamada `literalura_db`.
    * Verifica tus credenciales de base de datos (`username`, `password`) en `src/main/resources/application.properties` y ajústalas si es necesario.
3.  **Compilar y Ejecutar:**
    * Puedes ejecutar la aplicación desde tu IDE (IntelliJ IDEA, Eclipse) ejecutando la clase `LiteraturaApplication`.
    * Alternativamente, desde la línea de comandos, navega hasta la raíz del proyecto y usa Maven:
        ```bash
        mvn spring-boot:run
        ```

Una vez que la aplicación se inicie, se mostrará el menú gráfico donde podrás interactuar con las diferentes opciones.

##  Características

* **Búsqueda de Libros y Autores**: Consulta la colección de Gutendex por título o autor.
* **Registro Local**: Preserva los libros y autores encontrados en tu base de datos PostgreSQL.
* **Listados**: Visualiza todos los libros y autores registrados localmente.
* **Filtrado de Autores**: Encuentra autores que estuvieron vivos en un año específico o dentro de un rango de años.
* **Libros por Idioma**: Lista libros filtrados por idioma, tanto de Gutendex y registrados
* **Top 13**: Consulta los libros más descargados, tanto desde Gutendex y registrados.
* **Interfaz Gráfica (GUI)**: Menú interactivo basado en Swing para una experiencia de usuario amigable.
