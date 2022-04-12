cd dockerfiles
docker rm cdkcontainername
docker-compose run --name cdkcontainername servicecdk cdk deploy --require-approval never
