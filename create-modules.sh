#!/bin/bash

mvn install:install-file -Dfile="di/target/di-1.0.0.jar" -DgroupId=com.senla -DartifactId=di -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile="model/target/model-1.0.0.jar" -DgroupId=com.senla -DartifactId=model -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile="ui/target/ui-1.0.0.jar" -DgroupId=com.senla -DartifactId=ui -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true