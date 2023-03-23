# DAD_TravelFriendly
Repositorio de práctica Desarrollo de Aplicaciones Distribuidas

Nombre del proyecto : Travel Friendly

URL del repositorio de GitHub :  https://github.com/MegaTrece/DAD_TravelFriendly

URL Trello : https://trello.com/b/41KZsuYL/travelfriendly

Miembros del proyecto : Pedro Barquero Torres
                        Sergio López Elvira
			Violeta Trujillo Almeida

Nombre de la aplicación: Travel Friendly

Descripción aplicación: consiste en una aplicación para que los clientes puedan contactar con conductores particulares, que ofrecen viajes en coche, y así adquirir sus servicios.

Funcionalidades públicas: al entrar sin registrase, el usuario podrá ver los viajes que se ofertan.

Funcionalidades privadas: al entrar el usuario con su cuenta, podrá reservar sitio en los viajes que se ofertan, consultar su historial de viajes, chatear con el conductor del viaje y hacer valoraciones de los conductores de sus viajes.

Entidades:
1. Usuario: es el cliente que reserva un sitio en un viaje.
2. Conductor: oferta sitios en un viaje para que los usuarios lo adquieran.
3. Viaje: el desplazamiento que oferta un conductor.
4. Reserva: una vez reservado el viaje, el usuario tiene su reserva.
5. Valoración: es la opinión que se hace de cualquier usuario de la aplicación. Los conductores hacen valoraciones de sus clientes y los clientes de los conductores.

Actualización Fase 2: como no se ha podido hacer la diferenciacion entre las entidades usuario y conductor, ya que en esta fase no se podía implementar el inicio de sesión, se han incluido las entidades de chat y mensaje.

5. Chat: es una entidad que recoge dos usuarios (participantes del viaje, conductor y usuario) y la lista de mensajes entre ellos.
6. Mensajes: contienen un string con el cuerpo del mensaje.

Funcionalidades servicio interno:
1. Envío de correo de electrónico al registrarse un usuario.
2. Envío de correo electrónico cuando el usuario indica que ha olvidado su contraseña.
3. Generar reserva en el historial del usuario, al adquirir un sitio en un viaje.
4. Chat entre conductor y usuario.

## Fase_2
### Diagrama_E-R
![Image Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/ER.jfif)

### Diagrama_UML
![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/umlBueno.jfif)

### Capturas de pantalla
![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/login.JPG)

Actualmente en la fase 2, con meter un usuario y una contraseña, genera el usuario para navegar por la app (y lo guarda en la BD).

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/main.JPG)

Es una página de bienvenida, la aplicación saluda usando el nombre del usuario.

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/buscador.JPG)

Permite buscar viajes publicados en la web (por lo que están almacenados en la BD) y comprar un sitio en ese viaje.

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/publicar.JPG)

Permite publicar un viaje (y guardarlo en la BD) rellenando los diferentes campos.

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/chat.JPG)

Permite hablar con el usuario del viaje (si es usuario, habla con el conductor y si es el conductor habla con el usuario). Guarda el chat y sus mensajes en la BD

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/perfil.JPG)

Muestra el nombre de usuario y las opiniones que ha publicado.

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/tusViajes.JPG)

Muestra los viajes que ha publicado el usuario y los que ha comprado

![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/opinar.JPG)

Permite publicar una opinión sobre el usuario/conductor (y guardar en la BD) que ha comprado/publicado el viaje.

### Diagrama de pantallas
![Image_Text](https://github.com/MegaTrece/DAD_TravelFriendly/blob/main/esquemas/diagrama.png)

## Fase_3
## Generación del jar
Para la generación del jar podemos hacerlo por el IDE:  Run as ...> Maven install lo que nos generará un Fichero : `Fichero <nombreproyecto>_<version>.jar` en la carpeta target.
También podemos hacerlo de manera más directa mediante línea de comandos : 
```
$ cd ~/TravelFriendly
$ mvn package
```
Al igual que utilizando el IDE , el jar se encontrará en la carpeta target del proyecto.

## Subir el jar a la máquina virtual
Para subir el jar a la máquina virtual ejecutaremos el siguiente comando : 
```
$ scp -i Ruta_clave_Privada Archivo.jar ubuntu@Dirección_IP_Flotante:/Ruta_Copia
```
Utilizamos el comando scp que nos permite copiar archivos o directorios entre un sistema local y un sistema remoto. Este comando utiliza ssh para la transferencia de datos. 

# Acceder a la máquina virtual 
Para el acceso utilizaremos el protocolo SSH.
```
$ ssh -i Ruta_Clave_Privada ubuntu@Dirección_IP_Flotante
```

## Configuración de la máquina virtual 
Suponiendo que tenemos una máquina virtual inicialmente vacia tendríamos que realizar las siguientes operaciones: 

**Instalación del JDK**
```
$ sudo apt-get update
# Para actualizar los paquetes de la máquina
$sudo apt install openjdk-18-jre-headless
# Instalación del JDK
```
**Instalación Mysql Server**
```
sudo apt-get install mysql-server
#Instalación de Mysql
mysql -u root -p 'Password'
#Introducimos nuestra contraseña
ALTER USER 'root'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'nueva_contraseña';
#Cambiamos la contraseña a la que tengamos especificada en nuestra aplicación 
FLUSH PRIVILEGES;
#Actualizamos los privilegios 
create database dad;
#Creamos nuestro schema vacio
```

## Ejecución Aplicación 
Con todo lo anteriormente comentado , unicamente nos quedaria ejecutar nuestra aplicación. Para ello utilizaremos el siguiente comando: 
```
$ java -jar Archivo.jar
```

