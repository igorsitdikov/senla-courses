#!/bin/bash

mvn clean -DskipTests install
cp application/target/application-1.0.0.jar ./bulletin-board.jar
java -jar bulletin-board.jar