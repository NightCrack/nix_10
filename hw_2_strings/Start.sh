cd ./library
mvn clean package
cd ..
mvn install:install-file \
-Dfile=./library/target/reversestring.jar \
-DgroupId=ua.com.alevel \
-DartifactId=reversestring \
-Dversion=1.0 \
-Dpackaging=jar \
-DgeneratePom=true
mvn clean package
java -jar target/hw_2_strings.jar