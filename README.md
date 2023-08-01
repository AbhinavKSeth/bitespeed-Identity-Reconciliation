# bitespeed-Identity-Reconciliation #



### Steps to run service locally ###
   * __Dependencies__: 
        Docker installed and running with permission
   * Run `app.sh` file , present in the repo's root dir.

Alternative from your repo's root, run:
   * `docker build -t identify-docker-image:latest .`
   * `docker compose up`
    
**Now your service is running on localhost:8080**

### Sample request curl ##:
```
curl --location 'localhost:8080/bitespeed/identify' \
--header 'Content-Type: application/json' \
--data-raw '{
"phoneNumber":"9080870606",
"email": "amazon@gmail.com"
}'
```
