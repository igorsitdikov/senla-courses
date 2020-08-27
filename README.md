### Fix trouble with query in mysql

1. `sudo nano /etc/mysql/my.cnf`

2. Add this to the end of the file
    ```
    [mysqld]  
    sql_mode = "STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"
    ```

3. `sudo service mysql restart` to restart MySQL
