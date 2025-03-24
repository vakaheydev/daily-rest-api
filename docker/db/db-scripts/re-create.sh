#!/bin/sh

# re-create.sh
psql -U postgres -d vaka_daily_db -f /sql-scripts/drop.sql
psql -U postgres -d vaka_daily_db -f /sql-scripts/create.sql
psql -U postgres -d vaka_daily_db -f /sql-scripts/insert.sql