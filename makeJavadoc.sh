#!/bin/bash

# #
# #	script shell di javadoc per i linuxiani
# #

#	creo javadoc nell'apposita cartella 'javadoc'
exec javadoc -d ./javadoc/ -private ./src/mondo_robot/MVC_Main.java ./src/mondo_robot/View/*.java ./src/mondo_robot/Controller/*.java ./src/mondo_robot/Model/*.java