#!/bin/bash

cd ./stringreverselib
mvn clean package
cd ..
mvn install:install-file \
-Dfile=./stringreverselib/target/stringreverselib.jar \
-DgroupId=ua.com.nightcrack.lib \
-DartifactId=stringreverselib \
-Dversion=1.0 \
-Dpackaging=jar \
-DgeneratePom=true

mvn clean package
java -jar ./target/hw_2_redo.jar