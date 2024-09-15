#!/bin/bash

# Get the DATABASE_URL from Heroku (This pulls the URL dynamically for your app)
HEROKU_APP_NAME="informative-teacher"  # Set your Heroku app name here
DATABASE_URL=$(heroku config:get DATABASE_URL -a $HEROKU_APP_NAME)

# Split the DATABASE_URL into its components (user, host, etc.)
PGPASSWORD=$(echo $DATABASE_URL | sed -e 's/^.*:\(.*\)@.*$/\1/')
PGUSER=$(echo $DATABASE_URL | sed -e 's/^.*\/\/\(.*\):.*@.*$/\1/')
PGHOST=$(echo $DATABASE_URL | sed -e 's/^.*@\(.*\):.*$/\1/')
PGDATABASE=$(echo $DATABASE_URL | sed -e 's/^.*\/\(.*\)$/\1/')

BASEDIR=$(dirname $0)

# Run SQL scripts (schema, data, and users)
psql $DATABASE_URL -f "$BASEDIR/schema.sql" &&
psql $DATABASE_URL -f "$BASEDIR/data.sql" &&
psql $DATABASE_URL -f "$BASEDIR/user.sql"
