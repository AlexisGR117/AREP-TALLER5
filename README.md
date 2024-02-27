# TALLER 5: TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER

Aplicación web pequeña que usa el micro-framework de Spark java (http://sparkjava.com/). Con la aplicaicón se crea un container para docker, se desplega y se configura en la máquina local. Luego, se crea un repositorio en DockerHub y se sube la imagen al repositorio.

## Comenzando

Estas instrucciones te ayudarán a obtener una copia del proyecto en funcionamiento en tu máquina local para fines de desarrollo y pruebas.

### Requisitos previos

- Kit de desarrollo de Java (JDK) versión 11 o posterior
- Herramienta de construcción Maven
- Docker Desktop

### Instalando

1. Crear un nuevo contenedor a partir de la imagen que etsá en Docker Hub:
    ```
    docker run -d -p 35002:6000 --name dockercontainerspark alexisgr117/firstsprkwebapprepo
    ```
2. Abre un navegador web y accede a la aplicación en http://localhost:35002/calculadora.

## Arquitectura

La aplicación es una web app simple desarrollada con el micro-framework Spark Java y posteriormente contenerizada en Docker. Se compone de los siguientes componentes:

1. Frontend:
   - Implementado con HTML y Javascript.
   - Proporciona una interfaz gráfica para interactuar con la aplicación.
   - Permite ingresar valores y enviar peticiones a través de formularios.
   - Muestra los resultados obtenidos por las operaciones realizadas por el backend.
2. Backend:
   - Desarrollado con Spark Java.
   - Define dos endpoints:
     - /calculadora: Muestra la interfaz gráfica inicial.
     - /computar: Recibe las peticiones del cliente, procesa los datos y retorna el resultado.
   - Utiliza métodos de reflexión para invocar funciones matemáticas de la librería Java Math en base al comando recibido.
   - Maneja operaciones como:
     - Cálculo de seno y coseno.
     - Determinar si una cadena es palíndromo.
     - Calcular la magnitud de un vector
3. Docker:
   - Se utiliza para empaquetar la aplicación en un contenedor estandarizado.
   - El Dockerfile define las instrucciones para construir la imagen del contenedor, incluyendo las dependencias y la configuración de la aplicación.

## Generar las imágenes

## Evaluación

### Caso de prueba 1:

**Objetivo:** Verificar que la API /calculadora funciona correctamente.

**Entrada:** Solicitud a la API /calculadora sin parámetros.

**Salida:** La API debe devolver una respuesta HTTP 200 con el contenido de la página web.

![Caso1.png](img/Caso1.png)

Se realizó la petición a http://localhost:35002/calculadora la cual obtuvo como respuesta exitosa el contenido de la página web.

### Caso de prueba 2:

**Objetivo:** Verificar que los diferentes cálculos funciona correctamente.

**Entrada:** Solicitud para cada uno de los diferentes cálculos que se pueden hacer.

**Salida:** Se deben mostrar los diferentes resultados de los cálculos correctamente.

![Caso2.png](img/Caso2.png)

Se realizaron las peticiones GET para cada uno de los cáculos al ingresar los valores correspondientes y oprimir los botones calcular, de esto se obtuvo como respuesta exitosa los resultados de los diferentes cálculos.

## Construido con

- Java 11
- Maven
- Docker

## Autores

* Jefer Alexis Gonzalez Romero