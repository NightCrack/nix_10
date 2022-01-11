#{
#[ -f ./target/hw_4_logs_and_test.jar ] && java -jar ./target/hw_4_logs_and_test.jar
#}  ||
#{
#[ -f ./target/classes/ua/com/alevel/Main.class ] &&
#java -cp target/classes/:$M2/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar ua.com.alevel.Main
#}  ||
#{
#javac -d target/classes/ -sourcepath src/main/java/ -cp $M2/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar src/main/java/ua/com/alevel/Main.java
#java -cp target/classes/:$M2/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar ua.com.alevel.Main
#}