#!/bin/bash

# #
# #	script shell di javadoc per i linuxiani
# #

#	creo il processo per creare javadoc nell'apposita cartella
exec javadoc -d ./javadoc/ -package ./src/mondo_robot/MVC_Main.java ./src/mondo_robot/View/*.java ./src/mondo_robot/Controller/*.java ./src/mondo_robot/Model/*.java &

#	memorizzo il processo parallelo per poter aspettare la sua conclusione
javadocPID=$
wait $javadocPID

#	controllo se nel sistema GNU/Linux sia preente il pacchetto 'xdg-utils'
REQUIRED_PKG="xdg-utils"
PKG_OK=$(dpkg-query -W --showformat='${Status}\n' $REQUIRED_PKG|grep "install ok installed")

if [ "" = "$PKG_OK" ]; 
then
	#	se non è presente, chiedo all'utente se installarlo
	read -p "$REQUIRED_PKG non è presente. Installare $REQUIRED_PKG? [Y/n]" -n 1 input
	echo ''                                         
	if [[ $input =~ ^[Yy]$ ]]; 
	then
		#	installo il pacchetto mancante con il permesso dell'utente
		exec sudo apt-get -y install $REQUIRED_PKG &

		#	aspettiamo la sua conclusione...
		aptPID=$
		wait $aptPID
	else
		#	se l'utente non vuole installarlo, mi fermo qua
		exit
	fi
fi

#	concluso la generazione della documentazione javadoc e accertato che esista il pacchetto xdg-utils, 
#	apro la pagina index.html con il browser di default tramite xdg-utils 
#	di www.freedesktop.org/wiki/Software/xdg-utils/
exec xdg-open ./javadoc/index.html &
