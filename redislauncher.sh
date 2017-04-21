#!/usr/bin/env bash
cd conf/redis/master
redis-server redis.conf&
redis-server sentinel.conf --sentinel&
cd ../slave01
redis-server redis.conf&
redis-server sentinel.conf --sentinel&
cd ../slave02
redis-server redis.conf&
redis-server sentinel.conf --sentinel&

# kill master
# redis-cli -p 6380 debug segfault

# check who is the master
# redis-cli -p 16380 sentinel get-master-addr-by-name redis-cluster