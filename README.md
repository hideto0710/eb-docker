eb-docker
===

## development
```
$ sbt -Dplay.evolutions.db.default.autoApply=true \
-Dconfig.file=conf/local.conf \
run
```