Covid Tracker

Este proyecto es una aplicación que realiza un seguimiento sobre COVID-19. Consume una API externa para obtener información sobre las regiones y provincias, y almacena estos datos en una base de datos. Los usuarios pueden consultar información sobre los casos confirmados, recuperados y fallecimientos en diferentes áreas.

## Estructura del Proyecto

- **client**: Contiene la clase `ApiClient`, que se comunica con la API externa para obtener los datos sobre las regiones, provincias y reportes de COVID-19.
- **config**: Configura las propiedades de la aplicación, como la conexión a la API y la base de datos.
- **model**: Define las entidades `Region`, `Province` y `Report`, que son las tablas en la base de datos.
- **repository**: Contiene las interfaces que interactúan con la base de datos para realizar operaciones.
- **service**: Gestiona la lógica del negocio, procesando y almacenando los datos obtenidos de la API.
- **covidapplication**: Contiene la clase principal para iniciar la aplicación Spring Boot.



