1. clone project 

    ```
        git clone --depth 1 --branch Final https://<login>:<password>@git-courses.senlainc.com/senla-java-MINSK1-group/sitdzikau_ihar.git
    ```

1. change directory to directory with project 

    ```
        cd sitdzikau_ihar
    ```

1. build project 

    ```
        mvn clean -DskipTests install
    ```
   
1. copy application to main folder with renaming

    ```
        cp application/target/application-1.0.0.jar ./bulletin-board.jar
    ```
   
1. run project 

    ```
        java -jar bulletin-board.jar
    ```
   
1. instead of steps 3-5 run script `run.sh`

   ```
        chmod +x run.sh
        ./run.sh
   ```
   
1. or use docker-compose, set in docker-compose.yml LOGIN and PASSWORD in args, after that start command

   ```
        docker-compose up --build
   ```

1. 