FROM ubuntu

RUN apt-get update
RUN apt-get install -y bash curl wget gnupg apt-transport-https apt-utils lsb-release
RUN wget -O - https://debian.neo4j.org/neotechnology.gpg.key | apt-key add -
RUN echo 'deb https://debian.neo4j.org/repo stable/' | tee -a /etc/apt/sources.list.d/neo4j.list

RUN echo "neo4j-enterprise neo4j/question select I ACCEPT" | debconf-set-selections
RUN echo "neo4j-enterprise neo4j/license note" | debconf-set-selections

RUN apt-get update
RUN apt-get install -y neo4j-enterprise

RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN mv ./kubectl /usr/local/bin

RUN mkdir /data
RUN mkdir /data/backupdir
ADD restore.sh /scripts/restore.sh
RUN chmod +x /scripts/restore.sh

CMD ["/scripts/restore.sh"]