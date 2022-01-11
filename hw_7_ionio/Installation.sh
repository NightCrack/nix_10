mvn install:install-file \
-Dfile=../custom_framework/target/custom_framework.jar \
-DgroupId=custom \
-DartifactId=custom_framework \
-Dversion=1.0 \
-Dpackaging=jar \
-DgeneratePom=true