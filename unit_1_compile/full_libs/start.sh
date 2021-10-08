javac -d build/classes/ -sourcepath src/ -cp libs/commons-lang3-3.12.0.jar src/ua/com/alevel/Main.java
java -cp build/classes/:libs/commons-lang3-3.12.0.jar ua.com.alevel.Main
