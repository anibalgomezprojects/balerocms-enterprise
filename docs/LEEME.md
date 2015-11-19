![BaleroCMS v2](images/logo.png)

## DOCUMENTACIÓN DE SOFTWARE

### Requerimientos del sistema

* Java 8 (jdk1.8.0_25)
* Apache Maven (3.2.5 or Higher)
* MySQL (5 o superior; Solo requerido paa produccción)

Para clonar o actualizar el proyecto:

* Git (2.3 o superior)

## Bienvenido a Balero CMS Enterprise.

### Acerca del proyecto

Balero CMS Enterprise es un sistema ultra-ligero, sencillo y un ultra-rápido gestionador de contenido web.
Utiliza el lenguaje de programación Java para la administración del Panel y AngularJS para el sistema de plantillas y sus servicios asincronos de API REST.

Balero CMS es una plataforma empresarial de contenido general que trabaja y se extiende mediante módulos, es decir
cualquier funcionalidad puede integrarse a traves de su arquitectura MVC y JPA mediante sus entidades y clases.

### Comienzo

Clonando el proyecto:

    $ git clone https://github.com/neblina-software/balerocms-enterprise.git

Navegue al directorio raíz y asigne permisos de lectura/escritura:

    $ sudo chmod -R 777 balerocms-enterprise
    $ cd balerocms-enterprise
    
Seleccione versión:

    $ git checkout v1.0-BETA

Y despues ejecute:

    $ mvn test
    
### Despliegue de desarrollo:

Para pruebas, desarrollo y demostración. (Base de datos en memoria RAM-H2)

    $ mvn spring-boot:run

Si tiene problemas, ejectue:

    $ mvn -Pdev spring-boot:run

Esto activara el perfíl de desarrollo.
    
### Despliegue de producción:

Para portales en producción (MySQL) con minificación de recursos (HTML/CSS/JS).
Primero necesitara crear una base de datos llamada 'balerocms_enterprise'.

    $ mvn -Pprod spring-boot:run
    
Nota: Asegurese que el servidor MySQL corre sobre el puerto 3306.

    telnet localhost 3306
    
Vea **Cambiando autentificación para producción**.

Felicidades!, ha instalado correctamente Balero CMS EE.
Abra http://localhost:8080/ en su navegador.

### Actualizando software

    $ cd balerocms-enterprise
    $ git pull
    $ git checkout NEW_VERSION
    
Nota: Si editó archivos, ejecute éste comando:

Puede enviar sus cambios después de combinar o el comando stash:

    $ git stash
    $ git merge origin/master
    $ git stash pop

Asegurese, que las migraciones se ejecutan con éxito. La acción será automática.

    Ejecute el comando:
    $ mvn test -P prod

Vea **Herramienta para migración/reparación de base de datos**.
    
#### Minificación para perfíl de producción

Minificación (también minimización o reducción al mínimo), es el proceso de eliminación de todos los innecesarios
caractéres de código fuente sin cambiar su funcionalidad. Estos caracteres innecesarios
suelen incluir caracteres blancos, espacios, caracteres de nueva línea, comentarios, y en ocasiones el bloque
delimitadores, que se utilizan para agregar la legibilidad del código, pero no son necesarios para que se ejecute.

Si usted lo activa, su aplicación cargará más rápido.

Se activa automaticamente cuando ejecuta el comando de producción:

Para habiliar/deshabilitar dentro de "config/":

    balerocms-minification: falso o verdadero

## Guía Rápida

Para logearse en su panel:

http://localhost:8080/admin/dashboard (oculto)

Y haga login con los datos de ejemplo:

* admin:admin

Para logearse como usuario:

http://localhost:8080/user/dashboard (público

Y hagan login con:

* user:user

**Note: en un portal en producciñon, borre el usuario 'user' y cambie la contraseña de administrador**.

## Configuracción

### Folder de configuración

Vaya a **src/main/resources/config**, toda la configuraciñon se encuentra ahi.

### Cambiando autentificación para el modo producción

Editar:

* "application-prod.properties" dentro del folder resources.

1. db.host = Host
2. db.name = Nombre de la base de datos
3. db.user = Usuario de MySQL
4. db.password = Password de MySWL
        
nota: Usualmente "root" y sin password "".

All Done!

**Notes Only For Developers and/or  Advanced Options**:

Balero CMS EE use a database called "balerocms_enterprise" on "localhost" (127.0.0.1) and MySQL port: 3306.
If you need to change these values you may need to edit:


### Configurando el puerto del servidor

El puerto por defecto es: 8080. Para usar otro **application.properties** -> server.port.

### Configurando Boletín Semanal (Cliente SMTP)

Editar **application.properties** dentro de resources, proporcione los datos
de autentificación para su cuenta de e-mail.

## Pruebas unitarias

Las pruebas unitarias se ejecutan en la memoria RAM, puede encontrar la configuración en: "config/".

### Pruebas unitarias con MySQL

A veces necesitará depurar una base de datos "real". Para hacer esto edite:
application-prod.properties. 
            
Usualmente "root" and "".        

Despues ejecute:

    $ mvn test -P prod
    
### Herramienta para reparación/migración de la base de datos

Si actualizo el software, recibió un error de arranque en base de datos, etc. Ejecute:

    Repair Command:
    $ mvn compile flyway:repair -P prod
    
    Migration Command:
    $ mvn compile flyway:migrate  -P prod

Vea **Cambiando autentificación para base de datos en producción**.

## Hot Swap

Balero CMS, al ser una aplicación de lenguaje compilado. Usted podrar re-compilar su aplicación
mientras se encuentre ejecutando:

    $ mvn compile
      
### Componentes Bower

Para instalar/actualizar librerias, ejecute:

$ bower install

Las librerias se descargarán en **/static/bower_components**.

## Bugs de seguridad / Feedback

Las pruebas se han hecho con el navegador Mantra. Si usted encuentra un bug, por favor reportelo.

### Contenido Responsivo

Para el contenido de su portal.

agregue éste segmento de código (Modo código fuente), ejemplo:

> &lt;div class="container-fluid"&gt;

> &lt;div class="row"&gt;

> ...

> &lt;/div&gt;

> &lt;/div&gt;
 
Por favor, lea la documentación de Twitter Bootstrap:

[http://getbootstrap.com/css/](http://getbootstrap.com/css/)

### Changelog Timestamps De Base De Datos

Dentro de **db/migration**m use la convención:

V-AÑO-MES-DIA.HORA-MINUTOS-SEGUNDOS__Mensaje.sql

Ejemplo:

V-20150107.010600__Mensaje_ejemplo_etc.sql

Convención De Version Control:

V1.0__Mensaje_ejemplo_etc.sql

### Internacionalización

Seleccione el idioma principal desde su Panel.

Vaya **Preferencias Globales > Lenguaje Principal**.

### Galeria de Imágenes

Plantilla de galeria:

    <div class="gallery">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 center">
            <img src="..." alt="..." class="img-gallery img-thumbnail" />
            <img src="..." alt="..." class="img-gallery img-thumbnail" />
            <img src="..." alt="..." class="img-gallery img-thumbnail" />
        </div>
    </div>
    
### Sistema De Plantillas

Balero CMS usa ThymeLeaf como sistema de plantillas, y se extiende con AngularJS.

Dentro de "resources/templates".

Estructura:

    /
    +- src/
       +- main/
          +- resources/
              +- templates/
                 +- $TemplateName
                    +- css
                    +- images
                    +- fragments
                        +- header.html
                        +- footer.html
                        +- mavbar.html
                        +- metas.html
                    *.html
                    
Todos los archivos dentro de **templates** será **publico**.

#### Llamando recursos desde las plantillas HTML

    <link rel="stylesheet" type="text/css" href="/$TemplateName/css/theme.css">

## NOTAS

## COMPILANDO DOCUMENTACIÓN

Para compilar está documentación ejecute:

    $ pandoc -o docs/documentacion.pdf docs/LEEME.md
    
Note: Requiere software pandoc.

Soporte Y contacto

Su feedback es bienvenido! anibalgomez@balerocms.com.

Éste proyecto se encuentra en desarrolo, utilizelo solo para pruebas antes de el día de su versión de lanzamiento.

## ACERCA DE

Proyecto en desarrollo por **Neblina Software** y **Anibal Gomez**.

Página oficial: [www.balerocms.com](http://www.balerocms.com/).

![Powered By](images/powered.png)
![Powered By](images/redhat.png)
![Powered By](images/osx.png)

Documentation Version: 0.0.1