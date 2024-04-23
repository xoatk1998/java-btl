# How to start
1. Create mysql container via docker compose
2. Run initial_xxx.sql file by order
3. Run main method in SchoolManagementApplication

# Maven spotless
mvn spotless::apply

# Generate Jwt keypair 
1. Install openssl
2. Run command (in bin folder of openssl): openssl genrsa -aes256 -out private_key_original.pem 2048 && openssl pkcs8 -topk8 -nocrypt -in private_key_original.pem -out private_key.pem
3. Run command (in bin folder of openssl): openssl rsa -pubout -in private_key.pem -out public_key.pem
4. (Option) Using Base64 to encode text in public_key.pem and private_key.pem to retrieve public_key and private_key

# Swagger page (Example with localhost:8080)
http://localhost:8080/swagger-ui/index.html#/

