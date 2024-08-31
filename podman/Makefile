# Podman
PODNAME := hadoop
PG_USER := postgres
PG_PASS := postgres

pd-machine-create:
	@podman machine init --memory=16384 --cpus=4 --disk-size=100

pd-machine-start:
	@podman machine start

pd-machine-rm:
	@podman machine rm -f

pd-machine-recreate: pd-machine-rm pd-machine-create pd-machine-start

pd-pod-create:
# 8088 = Hadoop Resource Manager
# 8080 = Quarkus/Spark master
# 9088 =
# 9000 =
# 9083 = Hive thrift metastore
# 9864 = Hadoop datanode web port
# 9867 = Hadoop datanode IPC port
# 9870 = Hadoop namenode web port
# 9999 = Hive Metastore UI
# 10000 = Hive Thrift
# 50010 = Datanode transfer
# 50020 = Datanode IPC
# 50075 = Datanode UI
# 50030 = Jobtracker port
	@podman pod create -n $(PODNAME) --network bridge \
		-p 5432:5432 \
		-p 8080:8080 \
		-p 8081:8081 \
		-p 8090:8090 \
		-p 9092:9092 \
		-p 4040:4040 -p 4041:4041 -p 6066:6066 -p 7077:7077 -p 8020:8020 -p 8088:8088 -p 9000:9000 -p 9083:9083 -p 9864:9864 -p 9870:9870 -p 9999:9999 -p 10000:10000 -p 50030:50030

pd-pod-rm:
	@podman pod rm -f $(PODNAME)

pd-pod-recreate: pd-pod-rm pd-pod-create

pd-hadoop-build:
	@podman build --format docker -t custom-hadoop -f hadoop/Containerfile

pd-quarkus-build:
	mvn -f ../todo-service-debezium/pom.xml clean package -Dquarkus.container-image.build=true #-Dquarkus.container-image.push=true

pd-build: pd-hadoop-build pd-quarkus-build

pd-hadoop:
	@podman run -dit --name hadoop-single --pod=$(PODNAME) custom-hadoop:latest

pd-quarkus:
	@podman run -dit --name quarkus --pod=$(PODNAME) todo-service-debezium:0.1

pd-init: pd-machine-init pd-machine-start pd-pod-create pd-images-build

pd-start: pd-hadoop
