# Set up

- `md db`
- `podman run --name postgres -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password -p 5432:5432 -v ./db -d postgres`
- `podman exec -ti postgres ash -c "psql -h localhost -U username -d <dbname>"`
