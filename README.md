# user-info-management-api
Spring Boot REST API project for userinfo management

This project is using Java 17 .  Install Java 17 before clone the project

# Steps to run the application

1. Clone the repository
2. Import the project to VS Code or STS
3. Run mvn clean install to build the project
4. Run the project as Spring boot application.
   
Application will run on http://localhost:8080/user-management

H2 console can viewed at http://localhost:8080/user-management/h2-console

Swagger url : http://localhost:8080/user-management/swagger-ui/index.html

# Steps to test the application

1. OpenAPI is enabled for this project to view request/response json models and to test the application
2. Once the applicaion is up and running , launch the swagger url http://localhost:8080/user-management/swagger-ui/index.html
3. You can also test the application methods using postman
4. Swagger will provide the multiple operations
   
# Test flow
 1. Expand registerUser operation and click on tryout. Provide user details. (images are optional and pass as null). This is will create user and retun the user id
 2. uploadImage - since Im using multipart file  , there is a file size limit. please upload small size for testing. you can run operation multiple times to upload. UploadImage operation provides Image details in response (Please save detail to test the other operations)
 3. associateImages - pass the user id recieved in step 1 and image details recieved in steps 2 to associate the images with user profile
 4. viewUserInfoWithImages - Pass the userid and you will recieve user information along with images 
   
