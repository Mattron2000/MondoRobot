#!/bin/bash

# #
# #	script shell di javadoc per i linuxiani
# #

#	creo il processo per creare javadoc nell'apposita cartella
exec javadoc -d ./javadoc/ -package ./src/mondo_robot/MVC_Main.java ./src/mondo_robot/View/*.java ./src/mondo_robot/Controller/*.java ./src/mondo_robot/Model/*.java &

#	memorizzo il processo parallelo per poter aspettare la sua conclusione
javadocPID=$
wait $javadocPID

#	concluso la generazione della documentazione javadoc, 
#	apro la pagine index.html con il browser di default tramite xdg-utils 
#	di www.freedesktop.org/wiki/Software/xdg-utils/
exec xdg-open ./javadoc/index.html