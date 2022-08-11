::
::	script batch di javadoc per i finestrai
::

::	avvio un processo sincrono per creare il javadoc, 
::	ma i successivi start dovranno aspettare che questo start finisca.
START /W javadoc -d .\javadoc\ -package .\src\mondo_robot\MVC_Main.java .\src\mondo_robot\View\*.java .\src\mondo_robot\Controller\*.java .\src\mondo_robot\Model\*.java

::	apro con il browser di default la pagina .html principale di javadoc
::	nascondendo il propmt di comandi che tanto non devo mostrare niente
START /B .\javadoc\index.html

EXIT
