
1. configure permissions andd SESFullAccess permission to lambda service role
2. add email to verified emails in SES AWS Service console

3. mvn clean package shade:shade


7. Create new GitHub repository user-service

8. In cmd
   git init
   git status
   git add .gitignore

Add files to .gitignore

git add .
git status
git commit -m "first commit"
git remote add origin https://github.com/DenysLaptiev/user-service.git
git push -u origin main


9. Add a collaborator (DenisLaptev) to github_codebuild_ebs in remote GitHub repository (of DenysLaptiev)
10. Add buildspec.yml
11. In cmd
    git add buildspec.yml
    git status
    git commit -m "added buildspec.yml"
    git push