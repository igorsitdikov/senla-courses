#!/bin/bash

mysql -uroot < create_db.sql
mysql -uroot < data.sql

echo '
[mysqld]
sql_mode = "STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"
' | sudo tee -a /etc/mysql/my.cnf

sudo service mysql restart

echo "db created!"
