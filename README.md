eb-docker
===

## development
```
$ sbt -Dplay.evolutions.db.default.autoApply=true \
-Dconfig.file=local/application.conf \
-Dlogger.file=local/logback.xml \
run
```

## deployment
```
$ eb create -db -db.engine mysql
```