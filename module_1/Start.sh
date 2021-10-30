{
[ -f ./target/module_1.jar ] && java -jar ./target/module_1.jar
}  ||
{
[ -f ./target/classes/ua/com/alevel/Module1Main.class ] && java -cp target/classes/ ua.com.alevel.Module1Main
}  ||
{
javac -d target/classes/ -sourcepath src/main/java/ src/main/java/ua/com/alevel/Module1Main.java
java -cp target/classes/ ua.com.alevel.Module1Main
}