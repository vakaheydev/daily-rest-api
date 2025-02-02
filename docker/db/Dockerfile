FROM postgres

RUN mkdir /sql-scripts

WORKDIR /sql-scripts

COPY db-scripts .

RUN chmod +x re-create.sh