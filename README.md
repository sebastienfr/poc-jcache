# PoC JCache
`docker-compose up -d` pour lancer redis avec 2 slaves

 `docker-compose stop && docker-compose rm -f` pur tout arrêter
 
 Les tests sont dans `poc-jcache/src/test/java/fr/kiabi/poc/jcache/domain/utils/CacheTest.java`
 
 Le fichier de conf redisson est dans `poc-jcache/src/main/resources/redisson-jcache.yaml`