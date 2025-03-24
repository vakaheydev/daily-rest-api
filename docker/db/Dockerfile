FROM postgres

RUN mkdir /sql-scripts

WORKDIR /sql-scripts

COPY db-scripts .

RUN chmod +x /sql-scripts/re-create.sh
