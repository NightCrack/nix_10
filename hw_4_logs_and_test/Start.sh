{
[ -f ./target/hw_4_logs_and_test.jar ] && java -jar ./target/hw_4_logs_and_test.jar
}  ||
{
[ -f ./target/classes/ua/com/alevel/Module1Main.class ] && java -cp target/classes/ ua.com.alevel.Module1Main
}  ||
{
javac -d target/classes/ -sourcepath src/main/java/ src/main/java/ua/com/alevel/Main.java
java -cp target/classes/ ua.com.alevel.Main
}