rmdir .\bin\sneerjartemp /s /q
mkdir .\bin\sneerjartemp

mkdir .\bin\sneerjartemp\strapjartemp
javac -target 1.5 -source 1.5 -encoding UTF-8 -sourcepath .\src;..\wheel\src -d .\bin\sneerjartemp\strapjartemp .\src\sneer\strap\Main.java
jar cf .\bin\sneerjartemp\strap.jar -C .\bin\sneerjartemp\strapjartemp .
rmdir .\bin\sneerjartemp\strapjartemp /s /q

javac -target 1.1 -source 1.2 -encoding UTF-8 -sourcepath .\src -d .\bin\sneerjartemp .\src\Boot.java
jar cfe .\bin\Sneer.jar Boot -C .\bin\sneerjartemp .
rmdir .\bin\sneerjartemp /s /q

