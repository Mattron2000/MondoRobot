::
::	script batch di javadoc per i finestrai
::

::	avvio un processo sincrono per creare il javadoc
START /W javadoc -d .\javadoc\ -package .\src\mondo_robot\MVC_Main.java .\src\mondo_robot\View\*.java .\src\mondo_robot\Controller\*.java .\src\mondo_robot\Model\*.java

EXIT
