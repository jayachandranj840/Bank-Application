# Bank Web Application

This web application for a bank includes customer and admin login functionality. It is built using Java EE technologies and runs on an Apache Tomcat server with a MySQL database.

## How to Run the Code

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Tomcat 9 or higher
- MySQL 5.7 or higher
- Maven 3.6 or higher

### Steps to Run the Application

1. **Clone the Repository**

   ```sh
   git clone https://github.com/yourusername/bank-web-application.git
   cd bank-web-application
   ```

2. **Set Up the Database**

   - Install MySQL and create a database named `bank_db`.
   - Import the provided SQL script to create the necessary tables:

     ```sh
     mysql -u root -p bank_db < path/to/database.sql
     ```

3. **Configure the Application**

   - Open `src/main/resources/config.properties` and update the database settings:

     ```properties
     db.url=jdbc:mysql://localhost:3306/bank_db
     db.username=root
     db.password=yourpassword
     ```

4. **Build the Project**

   ```sh
   mvn clean install
   ```

5. **Deploy to Tomcat**

   - Copy the `target/bank-web-application.war` file to the `webapps` directory of your Tomcat installation.
   - Start the Tomcat server:

     ```sh
     cd /path/to/tomcat/bin
     ./startup.sh
     ```

6. **Access the Application**

   - Open your web browser and go to `http://localhost:8080/bank-web-application`.
   - You should see the home page with options to log in as a customer or admin.
