#!/bin/bash

mysql -uroot < create_db.sql
mysql -uroot < data.sql
echo "db created!"
