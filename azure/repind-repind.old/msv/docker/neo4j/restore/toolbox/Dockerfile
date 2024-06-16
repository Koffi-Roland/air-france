FROM ubuntu

RUN apt-get update
RUN apt-get install -y bash curl wget gnupg apt-transport-https apt-utils lsb-release

RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN mv ./kubectl /usr/local/bin

RUN mkdir /data/
ADD restore.sh /scripts/restore.sh
RUN chmod +x /scripts/restore.sh
ADD launch_restore.sh /scripts/launch_restore.sh
RUN chmod +x /scripts/launch_restore.sh

CMD exec /bin/bash -c "trap : TERM INT; sleep infinity & wait"