# javac - java compiler
# -d - output directory
# -sourcepath - path to all classes that are going to be used
# The last path is a path to the Main.class
javac -d ./target/classes -sourcepath ./src/main/java ./src/main/java/ua/com/alevel/Main.java
# java - java virtual machine that will execute the following program
# -cp - path to the root package location, that contains
# all classes for the program in question
# The last path is a path to the Main class
echo "This program is running via classes" && sleep 1s
cd ./target && java -cp ./classes ua.com.alevel.Main
# create a META-INF/MANIFEST.MF file
mkdir -p ./META-INF && echo "Manifest-Version: 1.0
Created-By: NightCrack
Build-Jdk-Spec: $(javac --version | sed -e 's/javac //' -e 's/\..*//')
Main-Class: ua.com.alevel.Main" > ./META-INF/MANIFEST.MF
# use jar command to bundle the compiled classes and the manifest into an executable JAR file
jar cfm hw_1_redo.jar ./META-INF/MANIFEST.MF -C ./classes/ .
echo "this program is running via jar" && sleep 1s
java -jar hw_1_redo.jar