# Lambda Function

## Project Overview
The **Lambda Function** is part of the **AWS Acquaintance Project**. It is responsible for:
- Sending emails to users after:
  - File upload completion (triggered by the [Image Service](https://github.com/DenysLaptiev/image-service)).
  - File deletion notification (triggered by the Step Function).

## Features
- Integration with AWS SES (Simple Email Service) for email notifications.
- Subscribed to AWS SNS events for upload and deletion notifications.

## Deployment
- Deployed using **Terraform**.

## Related Services
- [User Service](https://github.com/DenysLaptiev/user-service)
- [Image Service](https://github.com/DenysLaptiev/image-service)



1. configure permissions andd SESFullAccess permission to lambda service role
2. add email to verified emails in SES AWS Service console

3. mvn clean package shade:shade


7. Create new GitHub repository lambda

8. In cmd
   - git init
   - git status
   - git add .gitignore

Add files to .gitignore

- git add .
- git status
- git commit -m "first commit"
- git remote add origin https://github.com/DenysLaptiev/lambda.git
- git push -u origin main

