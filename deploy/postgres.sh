#!/bin/bash
docker run --name pg-13.3 -p 8075:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=masterkey -v /srv/db/postgres:/var/lib/postgresql/data:rw -d postgres:13.3
