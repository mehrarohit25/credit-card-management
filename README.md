# credit-card-management
This is a credit card management project for credit card providers.
It helps add credit card and list down all the credit cards in the system.
--------------------
### Prerequisites

* [Maven 3.8.1](https://maven.apache.org/)
* [openjdk_11.0.12(Java 11)](https://openjdk.java.net/)
* Helm installation
* Docker installation 
* Kubectl configured
* A local kubernetes cluster e.g. Minikube

### Build

	mvn clean install (Builds with tests)
	mvn clean install -DskipTests (Builds without tests)

### Running the application

        cd credit-card-management
        mvn spring-boot:run

### Rest End Points

    POST(Add Credit card): http://localhost:8080/v1/cards/credit-card @RequestBody (#refer CreditCardRequest)
    GET(Get all credit cards) :  http://localhost:8080/v1/cards/credit-cards


### Sample Request body for Post operation

    {
        "name":"Rohit Mehra",
        "cardNumber":"4242424242426742",
        "limit":40.22
    }
### Sample Response for Post operation

    {
        "name": "Rohit Mehra",
        "cardNumber": "4242424242426742",
        "limit": "£40.22",
        "balance": "£0.0"
    }

### Sample Response for Get operation

    [
    {
        "name": "Rohit Mehra",
        "cardNumber": "4242424242426742",
        "limit": "£40.22",
        "balance": "£0.00"
    },
    {
        "name": "Raj Mehra",
        "cardNumber": "4444333322221111455",
        "limit": "£10.00",
        "balance": "£0.00"
    }
]

### Postman Collection:

Refer File: Credit Card Management.postman_collection.json

### Database details:

    Console: http://localhost:8081/h2
    Jdbc url : jdbc:h2:mem:carddb
    UserName: sa
    Password:
    Table Name  : T_CREDIT_CARD

### Docker and helm integration:
Build docker image and push it to docker hub:

docker-compose build

docker push <imagename:tagname>

Helm steps :

kubectl create ns creditcard-helm

kubectl config set-context --current --namespace=creditcard-helm

helm install demo -n creditcard-helm ./creditcard-helm/







