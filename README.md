# Circula QA Automation Project
This repository contains an automated UI test suite for the Circula application using Selenium WebDriver, JUnit 5, and WebDriverManager.

Prerequisites
Before running the tests, ensure you have the following installed:

Java 15 or later
Maven (latest version recommended)
Google Chrome (latest version)
Git (optional, for cloning the repository)

Setup Instructions
1. Clone the Repository
   
git clone https://github.com/ChivuDan/super-disco123.git
cd super-disco123

2. Install Dependencies
Run the following command in the project root directory:
mvn clean install

3. Configure the Test Properties
Edit the config.properties file in src/main/resources/ to update test credentials:

base_url=https://circula-qa-challenge.vercel.app/users/sign_up

test_email=test@example.com

test_password=Test@1234

timeout=10

4. Run Tests
a.Run All Tests
mvn test

b.Run a Specific Test
mvn -Dtest=SignUpTest test

5. View Test Reports
After running the tests, reports are generated in:

target/surefire-reports/
