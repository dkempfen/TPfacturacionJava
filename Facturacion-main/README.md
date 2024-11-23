# FACTURACION

# 1 Descripción del Proyecto
Este proyecto es una aplicación Java que permite administrar las ventas de un comercio. Facilita la gestión de clientes, productos y facturas, además de ofrecer manejo automático de stock e integración con un servicio REST externo para obtener la fecha de las facturas.

# 2 Requisitos del Sistema
• Java: 17.0.12
• IDE: Puedes utilizar IntelliJ IDEA Community (gratuito) como entorno de desarrollo
• MySQL: 5.5.5
• XAMPP: Para el servidor MySQL local
• Swagger: Para la documentación y pruebas de la API
• Postman: Para pruebas de endpoints y validación de funcionalidades

# 3 Instalación y Configuración Desde Cero
1. Instalar IntelliJ IDEA
Descarga e instala IntelliJ IDEA desde la página oficial de JetBrains. Abre IntelliJ y crea un nuevo proyecto desde cero o importando el archivo descargado en el siguiente paso.

2. Configurar el Proyecto con Spring Initializr
Visita Spring Initializr y configura tu proyecto:

• Tipo de Proyecto: Maven
• Lenguaje: Java
• Spring Boot: La versión más reciente compatible con Java 17
• Dependencias:
• Spring Web
• Spring Data JPA
• MySQL Driver
• Lombok (opcional, para reducir el código repetitivo)
• Otros necesarios para la conexión a MySQL, según las especificaciones de tu aplicación.

Descarga el archivo del proyecto y descomprímelo.

3. Abrir el Proyecto en IntelliJ
Abre IntelliJ, selecciona Open y localiza la carpeta del proyecto descargado. Una vez abierto, revisa el archivo pom.xml y, si necesitas, agrega las dependencias adicionales.

4. Configurar el Archivo pom.xml
En el archivo pom.xml, añade o verifica las siguientes dependencias necesarias:

<dependencies> • Spring Boot Starter Web <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-web</artifactId> </dependency> • Spring Data JPA <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-data-jpa</artifactId> </dependency> • MySQL Driver <dependency> <groupId>mysql</groupId> <artifactId>mysql-connector-java</artifactId> </dependency> • Lombok <dependency> <groupId>org.projectlombok</groupId> <artifactId>lombok</artifactId> <version>1.18.24</version> <scope>provided</scope> </dependency> </dependencies>
Guarda el archivo y permite que IntelliJ descargue e instale las dependencias.

Dependencias esenciales en el archivo pom.xml incluyen:
- Spring Boot Starter Web
- Spring Data JPA
- MySQL Driver
- Lombok (yo lo utilice porque ahora tiempo para no tener que estar escribiendo los getters and setters y el constructor)

5. Configurar la Base de Datos en XAMPP
Inicia el servidor MySQL en XAMPP y realiza los siguientes pasos:

• Crea una base de datos llamada facturacion.
• Configura las credenciales en el archivo application.properties de Spring Boot:

spring.datasource.url=jdbc:mysql://localhost:3306/facturacion
spring.datasource.username="tu_usuario"
spring.datasource.password="tu_contraseña"
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

6. Ejecutar la Aplicación
En IntelliJ, selecciona el archivo principal de la aplicación y ejecútalo. La aplicación estará disponible en http://localhost:8080.

# 4 Uso de la API
La API REST de este sistema permite gestionar de manera eficiente los recursos relacionados con la facturación, incluyendo clientes, productos, facturas, y un servicio de fecha y hora.

Clientes: Para crear, consultar, actualizar y eliminar clientes.
Productos: Para crear, consultar, actualizar y eliminar productos.
Facturas: Para gestionar las facturas, incluyendo la creación de nuevas facturas, la consulta de facturas existentes, la actualización de datos y su eliminación.
Fecha y Hora: A través de un endpoint específico, se obtiene la fecha y hora actual en el formato local de Argentina. Este servicio consume la API externa de Time API para garantizar que las fechas de las facturas sean precisas y reflejen la hora real en el momento de su generación. El endpoint es /api/time/now y se hace mediante un metodo GET:
{
"dateTime": "2024-10-27T10:22:53"
}
Cada endpoint responde con códigos HTTP adecuados para indicar el estado de la solicitud (por ejemplo, 200 para éxito, 400 para errores en la solicitud, 404 si no se encuentra el recurso, etc.).

# 5 Gestión de Clientes
Crear Cliente
Endpoint: /api/clientes
Método: POST
Ejemplo de Solicitud:

{
"nombre": "Maximiliano Sastre",
"dni": "11111111"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T10:22:50",
"status": 400,
"error": "Bad Request",
"message": "El cliente ya existe",
"path": "/api/clientes"
}

Obtener Cliente
Endpoint: /api/clientes/{id}
Método: GET
Ejemplo de Error

{
"timestamp": "2024-10-30T10:30:00",
"status": 404,
"error": "Not Found",
"message": "Cliente no encontrado",
"path": "/api/clientes/1"
}

Actualizar Cliente
Endpoint: /api/clientes/{id}
Método: PUT
Ejemplo de Solicitud:

{
"nombre": "Maximiliano Sastre",
"dni": "22222222"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:00:00",
"status": 400,
"error": "Bad Request",
"message": "Datos inválidos para actualizar el cliente",
"path": "/api/clientes/1"
}

Eliminar Cliente
Endpoint: /api/clientes/{id}
Método: DELETE
Ejemplo de Respuesta Exitosa

{
"status": 200,
"message": "Cliente eliminado correctamente"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:05:00",
"status": 404,
"error": "Not Found",
"message": "Cliente no encontrado",
"path": "/api/clientes/1"
}

# 6 Gestión de Productos
Crear Producto
Endpoint: /api/productos
Método: POST
Ejemplo de Solicitud:

{
"nombre": "Producto X",
"precio": 50.0,
"stock": 100
}

Ejemplo de Error

{
"timestamp": "2024-10-30T10:40:00",
"status": 400,
"error": "Bad Request",
"message": "El producto ya existe",
"path": "/api/productos"
}

Actualizar Producto
Endpoint: /api/productos/{id}
Método: PUT
Ejemplo de Solicitud:

{
"nombre": "Producto X Actualizado",
"precio": 60.0,
"stock": 90
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:15:00",
"status": 400,
"error": "Bad Request",
"message": "Error al actualizar el producto",
"path": "/api/productos/1"
}

Eliminar Producto
Endpoint: /api/productos/{id}
Método: DELETE
Ejemplo de Respuesta Exitosa

{
"status": 200,
"message": "Producto eliminado correctamente"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:20:00",
"status": 404,
"error": "Not Found",
"message": "Producto no encontrado",
"path": "/api/productos/1"
}

# 7 Gestión de facturas:
En esta sección se explica cómo gestionar las facturas, incluyendo la creación, obtención, actualización y eliminación de facturas. Cada solicitud está diseñada para garantizar la precisión de los datos y la disponibilidad del stock antes de completar las operaciones.

Crear Factura

Endpoint: /api/facturas
Método: POST
Descripción: Permite crear una nueva factura asociada a un cliente y uno o más productos. Al crear la factura, el sistema verifica que el cliente y los productos existen, valida el stock y aplica el precio vigente al momento de la creación.
Ejemplo de Solicitud:

{ "clienteId": "49d7fb2e-1435-41a2-8cc2-020bfeeb4151",
"productos": [
{
"productoId": "0cccbc88-0793-42f0-b76f-ee7bdeedcedd",
"cantidad": 3
}
]
}

Ejemplo de Error:
{ "timestamp": "2024-10-31T09:45:00",
"status": 409,
"error": "Conflict",
"message": "Stock insuficiente para el producto solicitado",
"path": "/api/facturas"
}

Obtener Factura por ID

Endpoint: /api/facturas/{id}
Método: GET
Descripción: Obtiene una factura específica a partir de su ID.
Ejemplo de Error:
{ "timestamp": "2024-10-31T09:50:00",
"status": 404,
"error": "Not Found",
"message": "Factura no encontrada",
"path": "/api/facturas/1"
}

Actualizar Factura

Endpoint: /api/facturas/{id}
Método: PUT
Descripción: Permite modificar una factura existente. Las actualizaciones deben validar la disponibilidad de stock y aplican los precios actuales de los productos.
Ejemplo de Solicitud:
{ "clienteId": "49d7fb2e-1435-41a2-8cc2-020bfeeb4151",
"productos": [
{
"productoId": "0cccbc88-0793-42f0-b76f-ee7bdeedcedd",
"cantidad": 5
}
]
}

Ejemplo de Error:
{ "timestamp": "2024-10-31T10:00:00",
"status": 400,
"error": "Bad Request",
"message": "Datos inválidos para actualizar la factura",
"path": "/api/facturas/1"
}

Eliminar Factura

Endpoint: /api/facturas/{id}
Método: DELETE
Ejemplo de Respuesta Exitosa:
{ "status": 200,
"message": "Factura eliminada correctamente"
}

Ejemplo de Error:
{ "timestamp": "2024-10-31T10:05:00",
"status": 404,
"error": "Not Found",
"message": "Factura no encontrada",
"path": "/api/facturas/1"
}

# 8 Manejo de Excepciones y Códigos de Error
El sistema maneja diferentes tipos de errores para proporcionar respuestas claras y consistentes. Los errores se gestionan con códigos HTTP y mensajes detallados. Aquí están los posibles códigos de error:

• 400 Bad Request: Cuando los datos enviados son inválidos o incompletos. Este error es común para datos de entrada incorrectos en los métodos POST y PUT.
• 404 Not Found: Devuelto cuando el recurso solicitado no existe, como cuando se intenta acceder a un cliente o producto inexistente con un ID específico.
• 409 Conflict: Devuelto cuando hay un conflicto en la operación, como al intentar actualizar un recurso que ya está en uso.
• 500 Internal Server Error: Ocurre por problemas internos, como errores en la conexión a la base de datos o excepciones no controladas en la lógica de negocio.

Cada excepción en el sistema está capturada en el controlador correspondiente o en clases globales de manejo de errores, lo cual asegura que se envíe un mensaje claro y un código de estado adecuado.

RestTemplate permite manejar errores HTTP a través de excepciones específicas, como HttpClientErrorException para errores del cliente y HttpServerErrorException para errores del servidor. Es importante manejar estas excepciones para que la aplicación se recupere adecuadamente o proporcione mensajes informativos cuando ocurren errores al consumir servicios externos.

Configuración
RestTemplate puede ser instanciado directamente o definido como un @Bean en la configuración de Spring. Esto último permite inyectarlo en múltiples clases y facilita su reuso en varias partes de la aplicación.
Con RestTemplate, Spring Boot permite trabajar fácilmente con APIs REST, lo que facilita que nuestra aplicación obtenga y procese datos de servicios externos.

# 9 Servicio REST para Fecha de la factura
El sistema obtiene la fecha del comprobante utilizando el servicio Time API. La dirección http://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires se configura en un archivo de configuración (en MainService) para permitir obtener la fecha en el formato local de Argentina y se llama desde el servicio de comprobantes cuando se necesita generar la fecha para una nueva factura.

# 10 Pruebas con Postman
Para probar los endpoints de la API, sigue estos pasos:

Crear una Nueva Colección
Abre Postman y crea una colección nueva con el nombre de tu proyecto, por ejemplo, “API de Facturación”. Esto te permitirá organizar todos los endpoints en un solo lugar para realizar pruebas de manera eficiente.

Configurar los Endpoints
Agrega cada uno de los endpoints a la colección. Para cada endpoint, especifica el método HTTP (GET, POST, PUT o DELETE), la URL y el cuerpo de la solicitud, si corresponde. A continuación, algunos ejemplos de configuración:

Crear Cliente

Método: POST
URL: http://localhost:8080/api/clientes
Cuerpo de la Solicitud:
{ "nombre": "Maximiliano Sastre", "dni": "11111111" }
Obtener Cliente por ID

Método: GET
URL: http://localhost:8080/api/clientes/{id}
Nota: Reemplaza {id} con el ID específico del cliente que deseas obtener.
Repite este proceso para todos los endpoints de cliente, producto y factura.

Enviar Solicitudes y Verificar las Respuestas
Envía cada solicitud desde Postman y observa la respuesta. Comprueba que los códigos de estado (como 200 para éxitos y 404 para recursos no encontrados) coincidan con los ejemplos descritos en este README. También verifica los datos en el cuerpo de la respuesta y compara con los ejemplos de error y éxito que has configurado.

Guardar la Colección para Futuras Pruebas
Una vez configurada, guarda la colección en Postman. Esto permite reutilizarla para futuras pruebas o modificaciones de la API.

# 11 Uso de Swagger
Para ver la documentación de la API y probar los endpoints, accede a http://localhost:8080/swagger-ui.html. Swagger ofrece una interfaz interactiva donde puedes ver y probar cada endpoint de la API. Swagger documenta automáticamente todos los endpoints de la aplicación y permite probar solicitudes directamente en el navegador sin necesidad de herramientas adicionales. Esto es útil para validar rápidamente los diferentes métodos y verificar las respuestas que genera el sistema, incluyendo ejemplos de datos de prueba, parámetros de entrada y respuestas de error.

#12 Explicación Detallada de Facturas

Controladores (Controllers)
• Los controladores actúan como el punto de entrada para todas las solicitudes HTTP que la aplicación recibe, con métodos específicos para cada tipo de solicitud (GET, POST, PUT, DELETE).
• Por ejemplo, el controlador de ClienteController recibe solicitudes para crear, buscar, actualizar o eliminar clientes y asegura que los datos entrantes cumplen con las validaciones antes de enviarlos a los servicios.
• También formatea las respuestas de la aplicación en JSON, permitiendo comunicación con herramientas externas.
• Gestionan errores y devuelven códigos HTTP apropiados (como 400 para solicitudes incorrectas o 404 para elementos no encontrados).

Servicios (Services)
• Gestionan la lógica de negocio de la aplicación y las validaciones complejas. En InvoiceService, por ejemplo, se verifica el stock disponible antes de crear una factura, y se asegura que el precio de un producto registrado en una factura no cambie, manteniendo la integridad de los datos históricos.
• Los servicios son intermediarios entre los controladores y los repositorios, asegurando que se apliquen reglas de negocio antes de que los datos se almacenen en la base de datos.
• Separan la lógica de negocio, permitiendo que los controladores gestionen únicamente la interacción con los usuarios y los repositorios se enfoquen en la interacción directa con la base de datos.

Repositorios (Repositories)
• Los repositorios interactúan directamente con la base de datos mediante JPA. Cada repositorio contiene métodos para crear, leer, actualizar y eliminar entidades.
• Pueden incluir consultas personalizadas, como un método para buscar productos por nombre o clientes por DNI.
• Mantienen la eficiencia de las consultas, optimizando el acceso a los datos y asegurando que solo la información necesaria se traiga a la aplicación.

Entidades (Entities)
• Las entidades reflejan las tablas de la base de datos en el código. Por ejemplo, la entidad Producto tiene atributos nombre, precio, y stock, que corresponden a columnas en la base de datos.
• Las relaciones, como OneToMany entre Factura y Producto, se gestionan con anotaciones para reflejar las relaciones entre tablas y asegurar la integridad referencial.
• Las entidades mantienen la estructura de los datos y limitan la entrada de datos incorrectos, asegurando que los registros sean consistentes con el diseño de la base de datos.

Configuraciones (Configuration)
• Las configuraciones de la aplicación están definidas en application.properties, donde se especifican las credenciales de la base de datos y otros parámetros esenciales.
• Configuran Swagger para documentar automáticamente los endpoints y permiten probar la API sin herramientas adicionales.
• Incluyen otras opciones de personalización que adaptan la aplicación a distintos entornos, facilitando ajustes rápidos según el entorno de desarrollo o producción.

Aclaracion: Los precios de los productos en las facturas se mantienen en el tiempo, incluso si el precio del producto cambia posteriormente.

# 13 Uso de RestTemplate en Spring Boot

RestTemplate es una herramienta de Spring que facilita realizar solicitudes HTTP para consumir APIs REST desde una aplicación Spring Boot. Es útil para enviar y recibir datos en formatos como JSON o XML, y permite integrar nuestra aplicación con servicios externos.
Principales Operaciones de RestTemplate

1.	GET - Obtener Recursos:
RestTemplate permite hacer solicitudes GET para obtener recursos de un servidor externo y mapear la respuesta directamente a una clase de Java especificada. Este método se usa para traer datos de una API y convertirlos en objetos Java.

2.	POST - Crear Recursos:
Para enviar datos a una API y crear un recurso, se usa el método POST. RestTemplate permite enviar un objeto como parte de la solicitud, y la API puede responder con el recurso recién creado.

3.	PUT - Actualizar Recursos:
El método PUT se utiliza para actualizar un recurso existente en el servidor sin esperar una respuesta en el cuerpo. Se envía un objeto que reemplaza o modifica el recurso identificado en el servidor.

4.	DELETE - Eliminar Recursos:
DELETE permite eliminar un recurso en el servidor. RestTemplate puede enviar esta solicitud sin necesidad de enviar datos en el cuerpo de la misma.

# Contacto
Autor: Maximiliano Sastre.
