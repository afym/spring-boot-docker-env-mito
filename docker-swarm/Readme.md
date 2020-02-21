docker stack deploy --compose-file docker-compose.yml student-stack
docker service ls
docker stack ls
docker stack rm student-stack
docker service update --image nginx application-server