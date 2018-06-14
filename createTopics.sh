#!/usr/bin/env bash
kafka-topics --create --topic mobileCoordinates --zookeeper localhost:2181 --partitions 2 -replication-factor 1
kafka-topics --create --topic flights --zookeeper localhost:2181 --partitions 2 -replication-factor 1
