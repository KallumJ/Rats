CS-230 Group 41 Rats Game

Compiled with Oracle's JDK 8

The Oracle JDK 8 comes with JavaFX prepackaged, making compilation simpler.

To compile with the JDK, use the following (with Java on $PATH):

mkdir build
javac -d ./build -sourcepath src src/Main.java
cd build
jar cfe Rats.jar Main *
mv Rats.jar ../
cd ..
java -jar Rats.jar
