🏥 Hospital Management System
A Java desktop application for managing hospital patient records. Built using Java Swing for the front-end, Hibernate ORM for database interactions, and MySQL for persistent storage. The project follows Maven for dependency management and modular structure.

✨ Features
Add, View, Search, Update, and Delete Patient Records

Clean and Professional UI with Java Swing

Database integration using Hibernate (ORM)

Form validations and error handling

Navigation bar for seamless switching between operations

Patient attributes include:

Name, Room Number, DOB, Gender, Address, Phone Number, Disease, Treated (Yes/No)

🛠️ Technologies Used
Tech	Purpose
Java (Swing)	UI Design
Hibernate	ORM for DB communication
MySQL	Database
Maven	Build & Dependency Management

🚀 How to Run the Project
🔧 Prerequisites
Java JDK 8 or above

MySQL Server running locally (with a database configured)

Maven installed

📦 Clone and Build
git clone https://github.com/your-username/hospital-management-system.git
cd hospital-management-system
mvn clean package

🏃 Run the JAR
java -jar target/hospital-management-system-jar-with-dependencies.jar
🗃️ Database Configuration
Make sure you have a MySQL database created and update the credentials in hibernate.cfg.xml:

<property name="connection.url">jdbc:mysql://localhost:3306/hospital_db</property>
<property name="connection.username">root</property>
<property name="connection.password">your_password</property>

🙋‍♂️ Author
Kartik Thakur
