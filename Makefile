PG_USER := postgres
PG_PASS := postgres
HADOOP_USER := hduser
HIVE_JDBC := "jdbc:hive2://localhost:10000/default"
SPARK_DEPLOY_MODE := cluster

define JSON_TODO
curl -X 'POST' \
  'http://localhost:8080/todo' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "description": "string",
  "done": true,
  "dueDate": {
    "due": "2021-05-07",
    "start": "2021-05-07"
  },
  "title": "string"
}'
endef
export JSON_TODO

define JSON_TODO_KAFKA
endef
export JSON_TODO_KAFKA

# Tools
todo:
	@echo $$JSON_TODO | bash

list:
	@curl -X "GET" "http://localhost:8080/todo" -H 'accept: */*' | jq .

# Postgres
psql:
	@PGPASSWORD=$(PG_PASS) psql -h localhost -U $(PG_USER)

psql-dump:
	@PGPASSWORD=$(PG_PASS) pg_dump -h localhost -U $(PG_PASS) --data-only --table=todos | \grep -E "^[0-9]+.*" > dump.sql