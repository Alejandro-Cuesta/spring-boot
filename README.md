# Proyecto API de Soporte Técnico

##  Descripción

Este proyecto consiste en el desarrollo de una API REST con Spring Boot para la gestión de solicitudes de soporte técnico (Requests) y sus temas asociados (Topics).
Incluye:
- Controladores REST para gestionar solicitudes y temas.
- Servicios con lógica de negocio.
- Utilizamos la base de datos H2.
- Manejo centralizado de excepciones.
- Tests unitarios con JUnit 5.
- Documentación de endpoints con Swagger y Postman.
- Diagramas para explicar la arquitectura.
- Reporte de cobertura de tests (83%).


##  Requisitos iniciales del Proyecto

- La solicitud contendrá los siguientes datos: nombre del solicitante, fecha de la solicitud, tema de la consulta, descripción
- El Frontend necesitará solicitar la lista de los temas de consulta seleccionable (lista preestablecida en base de datos)
- El departamento técnico debe poder solicitar todas la solicitudes en orden de creación (ASC)
- El departamento técnico debe saber si una solicitud está pendiente de asistencia
- El departamento técnico debe poder marcar una solicitud como atendida y quién la atendió (Con el nombre es suficiente) 
- El departamento técnico debe poder saber cuando se efectuó la asistencia
- El departamento técnico debe poder editar una solicitud ya registrada
- Si una solicitud es editada se deberá saber la fecha y la hora de la edición
- El departamento técnico debe poder eliminar una solicitud, siempre y cuando esta haya sido marcada previamente como atendida
- Cobertura de tests unitarios del 70%


## Tecnologías Utilizadas

- Java 21
- Spring & Spring Boot 
- Spring Data JPA
- Maven
- JUnit 5 
- Swagger 
- Postman
- H2 como base de datos


## Pasos para la instalación

1. Clonar el repositorio

git clone https://github.com/Alejandro-Cuesta/spring-boot.git
cd spring-boot

2. Ejecutar la aplicación

mvn spring-boot:run

3. Acceder a Swagger UI

http://localhost:8080/swagger-ui.html


## Gestión del Proyecto

El proyecto ha sido gestionado con **Jira Software**, donde se han creado y organizado las historias de usuario, tareas y sprints.

### Jira

 [Acceder al tablero en Jira](https://janittoss-1754311878213.atlassian.net/jira/software/projects/SAHS/boards/34/backlog?atlOrigin=eyJpIjoiNjgwOWZlN2I4YWFkNDI2Y2EzZDc2M2QzNWU5ZjUxYTQiLCJwIjoiaiJ9)

### Capturas del tablero Jira
Backlog en Jira ![Backlog Jira](/docs/JiraBacklog.png)  
Historia en Jira ![Historia Jira](/docs/JiraHistoria.png)

### Historias de Usuario

### Sprint 1

1. Historia 1: Criterios de aceptación 
2. Historia 2: Seleccionar tema de soporte  
3. Historia 3: Ver todas las solicitudes ordenadas  
4. Historia 4: Ver estado de la solicitud  


### Sprint 2

1. Historia 5: Marcar solicitud como atendida  
2. Historia 6: Editar solicitud existente  
3. Historia 7: Eliminar solicitud atendida 
4. Historia 8: Cobertura de tests mínima  
5. Historia 9: Correcciones
6. README final con toda la documentación y capturas.


##  Diagramas realizados

### Diagrama UML de Clases

![Diagrama UML](/docs/DiagramaClases.png)

### Diagrama ER (Entidad-Relación) (patas de gallo)

![Diagrama ER](/docs/DiagramaPatasGallo.png)

### Diagrama de Secuencia (Flujo de una Solicitud)

![Diagrama ER](/docs/DiagramaFlujoDatos.png)



##  Cobertura de Tests

### Sprint1

![Cobertura de Tests](/docs/CoberturaSprint1.png)

### Sprint2

![Cobertura de Tests](/docs/CoberturaSprint2.png)


## Uso de la API

### Postman (CRUD)
![Postman](/docs/PostmanPut.png)
![Postman](/docs/PostmanGet.png)
![Postman](/docs/PostmanPost.png)
![Postman](/docs/PostmanDelete.png)

### Swagger
![Swagger](/docs/Swagger.png)
![Swagger](/docs/SwaggerTopicController.png)
![Swagger](/docs/SwaggerTopicControllerPost.png)
![Swagger](/docs/SwaggerRequestController.png)
![Swagger](/docs/SwaggerRequestControllerPut.png)


### Autor

Alejandro Cuesta