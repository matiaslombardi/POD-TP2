# Programación de Objetos Distribuidos - TP2

## Autores

- [Azul Candelaria Kim](https://github.com/AzuCande)
- [Matias Lombardi](https://github.com/matiaslombardi)
- [Patrick M. Dey](https://github.com/patrickmdey)
- [Santos Rosati](https://github.com/srosati)
- [Uriel Mihura](https://github.com/uri-99)

---------------------------------------------------------------------------------

## Objetivo

El objetivo de este Trabajo Práctico es implementar una aplicación de consola que utilice el modelo de programación MapReduce junto con el framework HazelCast para el procesamiento de
datos de sensores de peatones.
Más información sobre la consigna en el pdf encontrado en este mismo directorio.

---------------------------------------------------------------------------------

## Estructura del proyecto
En base a lo visto en clase, la estructura de este proyecto está dividida en 3 partes:
Api - Server - Client

### Api
Contiene los modelos y clases que deben conocer todos los nodos tales como los mapper y reducer.

### Server
Levanta una instancia de Hazelcast con cierta configuración.

### Client
Contiene parsers necesarios para cada query a invocar y se encarga de crear el trabajo que la resuelva.

---------------------------------------------------------------------------------

## Compilación y ejecución
Antes de comenzar a compilar y ejecutar el proyecto, modifique la variable TP_PATH en el archivo `compile.sh` con la ruta absoluta del directorio donde se encuentra el proyecto.
Luego, ejecute:
```bash
> ./compile.sh
```

### Server
Para ejecutar el servidor, ubíquese en el directorio `tpe2-g6-server-1.0-SNAPSHOT` y ejecute:
```bash
> run-server.sh
```
Esto levantará una instancia de hazelcast

### Client
Para ejecutar cualquiera de las consultas, ubíquese en el directorio `tpe2-g6-client-1.0-SNAPSHOT` y ejecute alguno de los scripts. Para ejecutar una consulta:
```bash
> ./queryX -Daddresses='xx.xx.xx.xx:yyyy' -DinPath=XX -DoutPath=YY [params]
```
donde:
* `X` es el número de la consulta a ejecutar
* `-Daddresses=xx.xx.xx.xx:yyyy` donde `xx.xx.xx.xx` es la dirección IP del servidor y `yyyy` es el puerto en el que se ejecuta alguno de los nodos del cluster. 
Pueden indicarse varias IPs, separadas por punto y coma (;).
* `-DinPath=XX` donde `XX` es la ruta donde se encuentran los archivos de entrada `sensors.csv` y `readings.csv`.
* `-DoutPath=YY` donde `YY` es la ruta donde se desea guardar los archivos de salida `queryX.csv` y `timeX.txt`, siendo `X` el número de la consulta.
* `[params]` son los parámetros extras que corresponden para algunas queries.

#### Parámetros adicionales de la consulta 3
* `-Dmin` es la cantidad mínima de peatones registrados en la medición más alta de un sensor activo.

#### Parámetros adicionales de la consulta 4
* `-Dn` es la cantidad de sensores a mostrar en el resultado.
* `-Dyear` es el año para el cual se desea obtener el resultado.
