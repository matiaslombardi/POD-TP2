#!/bin/bash

TP_PATH=$(pwd)
cd $TP_PATH

mvn clean install

cd server/target
tar -xzf tpe2-g6-server-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g6-server-1.0-SNAPSHOT
chmod +x run-server.sh

cd $TP_PATH/client/target
tar -xzf tpe2-g6-client-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g6-client-1.0-SNAPSHOT
chmod +x query*
cd $TP_PATH
