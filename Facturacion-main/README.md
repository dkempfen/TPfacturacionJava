SISTEMA DE FACTURACIÓN
1. Descripción del Proyecto
Este proyecto consiste en una aplicación desarrollada en Java que permite gestionar las ventas de un comercio. Incluye funcionalidades como la administración de clientes, productos y facturas, además de un manejo automatizado del inventario. También se conecta con un servicio REST externo para obtener la fecha de emisión de las facturas.

2. Requisitos del Sistema
Java: Versión 17 o superior
IDE: Se recomienda IntelliJ IDEA Community Edition (gratuito)
MySQL: Versión 5.5.5
XAMPP: Para configurar un servidor local de MySQL
Swagger: Para documentar y probar la API
Postman: Para verificar los endpoints y realizar pruebas funcionales
3. Instalación y Configuración
Paso 1: Configuración del Entorno
Descargar IntelliJ IDEA: Instálalo desde la página oficial de JetBrains.
Crear el Proyecto: Usa Spring Initializr para configurar un nuevo proyecto.
Tipo de Proyecto: Maven
Lenguaje: Java
Versión de Spring Boot: Compatible con Java 17
Dependencias Básicas:
Spring Web
Spring Data JPA
MySQL Driver
Lombok (opcional, para evitar código repetitivo como getters y setters).
Descarga el proyecto generado y descomprímelo.

Paso 2: Abrir el Proyecto
Abre la carpeta del proyecto en IntelliJ IDEA. Revisa el archivo pom.xml para asegurarte de que todas las dependencias necesarias están incluidas. Si falta alguna, agrégala manualmente.

Paso 3: Configuración de Base de Datos
Inicia el servidor MySQL con XAMPP.
Crea una base de datos llamada facturacion.
Configura las credenciales en el archivo application.properties de Spring Boot:
properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/facturacion  
spring.datasource.username=tu_usuario  
spring.datasource.password=tu_contraseña  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
Paso 4: Ejecutar la Aplicación
En IntelliJ, selecciona el archivo principal y ejecútalo. La aplicación estará disponible en http://localhost:8080.

4. Uso de la API
La API incluye funcionalidades para administrar clientes, productos y facturas. También incorpora un servicio para obtener la fecha y hora actual mediante un endpoint específico.

Ejemplo de Endpoint para la Fecha Actual
Ruta: /api/time/now
Método: GET
Respuesta Ejemplo:
json
Copiar código
{ 
  "dateTime": "2024-11-23T10:45:00"
}
5. Gestión de Recursos
Clientes
Crear Cliente

Endpoint: /api/clientes
Método: POST
Ejemplo de Solicitud:
json
Copiar código
{
  "nombre": "Juan Pérez",
  "dni": "12345678"
}
Eliminar Cliente

Endpoint: /api/clientes/{id}
Método: DELETE
Respuesta Ejemplo:
json
Copiar código
{
  "status": 200,
  "message": "Cliente eliminado correctamente"
}
Productos
Actualizar Producto

Endpoint: /api/productos/{id}
Método: PUT
Ejemplo de Solicitud:
json
Copiar código
{
  "nombre": "Producto Modificado",
  "precio": 75.5,
  "stock": 50
}
Facturas
Crear Factura

Endpoint: /api/facturas
Método: POST
Ejemplo de Solicitud:
json
Copiar código
{
  "clienteId": "12345",
  "productos": [
    {
      "productoId": "67890",
      "cantidad": 2
    }
  ]
}
6. Manejo de Excepciones
El sistema está diseñado para devolver mensajes de error claros en diferentes situaciones:

400 Bad Request: Datos inválidos o faltantes.
404 Not Found: Recurso no encontrado.
409 Conflict: Conflicto en la operación, como falta de stock.
500 Internal Server Error: Problemas internos, como errores en la lógica de negocio.
Gestión de Facturas
En esta sección se detalla cómo manejar las operaciones relacionadas con las facturas: creación, consulta, actualización y eliminación. Cada acción se ha diseñado para garantizar la exactitud de los datos y verificar la disponibilidad del stock antes de completarse.

Crear Factura
Endpoint: /api/facturas
Método: POST
Descripción: Crea una nueva factura asociada a un cliente y una lista de productos. El sistema valida la existencia del cliente y los productos, confirma la disponibilidad del stock y aplica el precio vigente al momento de la transacción.
Ejemplo de Solicitud:
json
Copiar código
{
  "clienteId": "49d7fb2e-1435-41a2-8cc2-020bfeeb4151",
  "productos": [
    {
      "productoId": "0cccbc88-0793-42f0-b76f-ee7bdeedcedd",
      "cantidad": 3
    }
  ]
}
Ejemplo de Error:
json
Copiar código
{
  "timestamp": "2024-10-31T09:45:00",
  "status": 409,
  "error": "Conflict",
  "message": "Stock insuficiente para el producto solicitado",
  "path": "/api/facturas"
}
Obtener Factura por ID
Endpoint: /api/facturas/{id}
Método: GET
Descripción: Recupera los datos de una factura específica utilizando su ID único.
Ejemplo de Error:
json
Copiar código
{
  "timestamp": "2024-10-31T09:50:00",
  "status": 404,
  "error": "Not Found",
  "message": "Factura no encontrada",
  "path": "/api/facturas/1"
}
Actualizar Factura
Endpoint: /api/facturas/{id}
Método: PUT
Descripción: Modifica una factura existente, validando la disponibilidad de stock y actualizando los precios según las tarifas actuales.
Ejemplo de Solicitud:
json
Copiar código
{
  "clienteId": "49d7fb2e-1435-41a2-8cc2-020bfeeb4151",
  "productos": [
    {
      "productoId": "0cccbc88-0793-42f0-b76f-ee7bdeedcedd",
      "cantidad": 5
    }
  ]
}
Ejemplo de Error:
json
Copiar código
{
  "timestamp": "2024-10-31T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Datos inválidos para actualizar la factura",
  "path": "/api/facturas/1"
}
Eliminar Factura
Endpoint: /api/facturas/{id}
Método: DELETE
Respuesta Exitosa:
json
Copiar código
{
  "status": 200,
  "message": "Factura eliminada correctamente"
}
Ejemplo de Error:
json
Copiar código
{
  "timestamp": "2024-10-31T10:05:00",
  "status": 404,
  "error": "Not Found",
  "message": "Factura no encontrada",
  "path": "/api/facturas/1"
}
Manejo de Excepciones
El sistema responde a errores mediante códigos HTTP y mensajes detallados:

400 Bad Request: Datos incompletos o inválidos.
404 Not Found: Recurso no encontrado.
409 Conflict: Conflicto en la operación, como falta de stock.
500 Internal Server Error: Problemas internos, como fallas en la lógica del negocio.
Los errores se manejan con controladores globales que aseguran respuestas claras y consistentes.

Uso de RestTemplate
RestTemplate facilita consumir APIs externas mediante solicitudes HTTP (GET, POST, PUT y DELETE). Es ideal para interactuar con servicios REST desde Spring Boot.
Ejemplo de uso:

java
Copiar código
RestTemplate restTemplate = new RestTemplate();
ResponseEntity<String> response = restTemplate.getForEntity("http://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires", String.class);
System.out.println(response.getBody());
