#!/bin/bash

# re-create.sh
psql -U postgres -d vaka_daily_db -f drop.sql
psql -U postgres -d vaka_daily_db -f create.sql
psql -U postgres -d vaka_daily_db -f insert.sql