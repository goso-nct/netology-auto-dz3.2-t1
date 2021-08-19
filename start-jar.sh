docker exec -it db /bin/sh -c "mysql --user=app --password=pass --database=app </sql/clear_db.sql >/dev/null 2>&1"
java -jar ./artifacts/app-deadline.jar
