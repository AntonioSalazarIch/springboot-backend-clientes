# springboot-backend-clientes
Creando un backend con spring boot

Lenguaje de Definición de Datos (DDL) (HIBERNATE)
Es un lenguaje de programación para definir estructuras de datos, proporcionado por los sistemas gestores de bases de datos, en este caso PostgreSQL. En inglés, Data Definition Language, de ahí sus siglas DDL. Te recuerdo que si necesitas un amplio conocimiento en DDL deberías ver nuestro Curso de Sentencias DDL, DML, DCL y TCL. Si estás empezando y quieres conocer Postgre, quizás te interese nuestro Curso de introducción a PostgreSQL.
Este lenguaje permite a los programadores de un sistema gestor de base de datos, como Postgres, definir las estructuras que almacenarán los datos así como los procedimientos o funciones que permitan consultarlos.
Para definir las estructura disponemos de tres sentencias:
CREATE, se usa para crear una base de datos, tabla, vistas, etc.
ALTER, se utiliza para modificar la estructura, por ejemplo añadir o borrar columnas de una tabla.
DROP, con esta sentencia, podemos eliminar los objetos de la estructura, por ejemplo un índice o una secuencia.

Lenguaje de Manipulación de Datos (DML)
También es un lenguaje proporcionado por los sistemas gestores de bases de datos. En inglés, Data Manipulation Language (DML).

Utilizando instrucciones de SQL, permite a los usuarios introducir datos para posteriormente realizar tareas de consultas o modificación de los datos que contienen las Bases de Datos.

Los elementos que se utilizan para manipular los datos, son los siguientes:

SELECT, esta sentencia se utiliza para realizar consultas sobre los datos.
INSERT, con esta instrucción podemos insertar los valores en una base de datos.
UPDATE, sirve para modificar los valores de uno o varios registros.
DELETE, se utiliza para eliminar las filas de una tabla.

/* TIPO VEHICULOS */
INSERT INTO public.tipo_vehiculos (descripcion) VALUES('CAMIONETA');

#############################################################################

Anotaciones, herencia e implementaciones

########################## EN LAS ENTIDADES #################
@Entity

@Table( name = "cargos")

@Id

@GeneratedValue( strategy = GenerationType.IDENTITY )
#############################################################

########################## EN LOS DAOS ######################
hereda de JpaRepository
findAll()
findById()
save()
deleteById()
#############################################################

########################## EN LAS INTERFACES ################
public List<Cargo> findAll();

public Cargo findById( Long id );
	
public Cargo save( Cargo cargo );
	
public void deleteById ( Long id );
#############################################################

########################## EN LAS IMPLEMENTACIONES ##########
@Autowired
Esta anotación se aplica a campos, métodos de “setters” y constructores. La anotación @Autowired inyecta la dependencia del objeto implícitamente.
Se usara esta anotacion en las interfaces

@Service
Esta anotación se usa en una clase. @Service marca una clase Java que realiza algún servicio, como ejecutar lógica de negocios, realizar cálculos y llamar a API externas. Esta anotación es una forma especializada de la anotación @Component destinada a ser utilizada en la capa de servicio.
@Transactional
Sirve para hacer rollback a una ejecucion fallida, tambien para anotar a un metodo de solo lectura

@Transactional( readOnly = true )
@Transactional
#############################################################

########################## EN LOS CONTROLADORES #############
@Autowired

@Controller
La anotación @Controller se usa para indicar que la clase es un controlador Spring. Esta anotación se puede utilizar para identificar controladores para Spring MVC o Spring WebFlux.

@ResponseBody
Es una anotación de Spring que vincula el valor de retorno de un método al cuerpo de la respuesta web. No se interpreta como un nombre de vista. Utiliza convertidores de mensajes HTTP para convertir el valor devuelto en un cuerpo de respuesta HTTP, según el tipo de contenido en el encabezado HTTP de la solicitud.

@RestController
Spring 4.0 introdujo la anotación @RestController para simplificar la creación de servicios web RESTful. Es una anotación conveniente que combina @Controller y @ResponseBody , lo que elimina la necesidad de anotar cada método de manejo de solicitudes de la clase del controlador con la anotación @ResponseBody .

@RequestMapping
Nos permite ejecutar métodos y fragmentos de código cada vez que el usuario final llega a un punto final con una solicitud HTTP. En este caso, es una asignación de raíz simple que devuelve la cadena «¡Hola mundo!» cuando se alcanza el punto final raíz.
Mapeo de clase 
@RequestMapping( value="/api" )
@RequestMapping( "/api" )
Mapeo de metodo
@RequestMapping(value="/resultadoPeticiones", method=RequestMethod.GET)
@RequestMapping(value="/resultadoPeticiones", method=RequestMethod.POST)

@Service
Esta anotación se usa en una clase. @Service marca una clase Java que realiza algún servicio, como ejecutar lógica de negocios, realizar cálculos y llamar a API externas. Esta anotación es una forma especializada de la anotación @Component destinada a ser utilizada en la capa de servicio.


@PathVariable
En pocas palabras, la anotación @PathVariable se puede usar para manejar variables de plantilla en el mapeo de URI de solicitud y establecerlas como parámetros de método.
@GetMapping("/api/employees/{id}")
@ResponseBody
public String getEmployeesById(@PathVariable String id) {
    return "ID: " + id;
}
@GetMapping("/api/employeeswithvariable/{id}")
@ResponseBody
public String getEmployeesByIdWithVariableName(@PathVariable("id") String employeeId) {
    return "ID: " + employeeId;
}
@GetMapping("/api/employees/{id}/{name}")
@ResponseBody
public String getEmployeesByIdAndName(@PathVariable String id, @PathVariable String name) {
    return "ID: " + id + ", name: " + name;
}

#############################################################
