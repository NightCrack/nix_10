{
[ -f ./target/hw_3_start_oop.jar ] && java -jar ./target/hw_3_start_oop.jar
}  ||
{
[ -f ./target/classes/ua/com/alevel/CRUDApp.class ] && java -cp target/classes/ ua.com.alevel.CRUDApp
}  ||
{
javac -d target/classes/ -sourcepath src/main/java/ src/main/java/ua/com/alevel/CRUDApp.java
java -cp target/classes/ ua.com.alevel.CRUDApp
}