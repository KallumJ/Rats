CS-230 Group 41 Rats Game

Oracle JDK 8

The Oracle JDK 8 comes with JavaFX prepackaged, making compilation simpler.

To compile with the Oracle JDK 8, use the following commands:

mkdir build
path/to/javac -d ./build -sourcepath src src/Main.java
cd build
path/to/jar cfe Rats.jar Main *
mv Rats.jar ../
cd ..
path/to/java -jar Rats.jar