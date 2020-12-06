1. clone project 

    ```
        git clone --depth 1 --branch Final https://<login>:<password>@git-courses.senlainc.com/senla-java-MINSK1-group/sitdzikau_ihar.git
    ```

1. change directory to directory with project 

    ```
        cd sitdzikau_ihar
    ```

1. enter to mysql 

    ```
        mysql -u <user> -p
        Enter password: <password>
    ```

1. run sql script `db.sql` for database init

    ```
        source db.sql
    ```
1. exit from mysql terminal
    
   ```
        mysql> exit
        Bye
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
   
1. instead of steps 6-8 run script `run.sh`

   ```
        chmod +x run.sh
        ./run.sh
   ```