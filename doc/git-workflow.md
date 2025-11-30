# Git Workflow

## Overview
This project follows the **GitFlow** branching model to ensure smooth collaboration and organized 
development processes. The GitFlow workflow provides a clear structure for managing feature development, 
releases, and hotfixes.

---

## Branch Types and Their Purposes

1. **Main Branch (`main`)**
    - Represents the production-ready code.
    - Only stable and thoroughly tested code is merged here.

2. **Develop Branch (`develop`)**
    - Acts as the integration branch for all features.
    - Always contains the latest completed changes that are ready for testing.

3. **Feature Branches (`feature/*`)**
    - Used for developing new features.
    - Created from `develop` and merged back into `develop` when the feature is complete.
    - Naming convention: `feature/<feature-name>`

4. **Release Branches (`release/*`)**
    - Used to prepare for a new production release.
    - Created from `develop` and merged into both `main` and `develop` after completion.
    - Naming convention: `release/<version-number>`

5. **Hotfix Branches (`hotfix/*`)**
    - Used to quickly fix critical issues in the production code.
    - Created from `main` and merged back into both `main` and `develop`.
    - Naming convention: `hotfix/<issue-name>`

---

## Workflow Steps

### 1. Setting Up the Repository
- Clone the repository:
  ```bash
  git clone <repository-url>
  ```
- Switch to the `develop` branch for regular development work:
  ```bash
  git checkout develop
  ```

### 2. Starting a Feature Branch
- Create a new feature branch from `develop`:
  ```bash
  git checkout -b feature/<feature-name> develop
  ```
- Work on the feature and commit changes:
  ```bash
  git add .
  git commit -m "<commit-message>"
  ```
- Push the feature branch to the remote repository:
  ```bash
  git push origin feature/<feature-name>
  ```

### 3. Completing a Feature Branch
- When the feature is complete, merge it into `develop`:
  ```bash
  git checkout develop
  git merge feature/<feature-name>
  ```
- Delete the feature branch locally and remotely:
  ```bash
  git branch -d feature/<feature-name>
  git push origin --delete feature/<feature-name>
  ```

### 4. Creating a Release Branch
- Create a release branch from `develop`:
  ```bash
  git checkout -b release/<version-number> develop
  ```
- Test and prepare the code for release.
- Merge the release branch into `main`:
  ```bash
  git checkout main
  git merge release/<version-number>
  ```
- Tag the release:
  ```bash
  git tag -a <version-number> -m "Release <version-number>"
  git push origin <version-number>
  ```
- Merge the release branch back into `develop`:
  ```bash
  git checkout develop
  git merge release/<version-number>
  ```
- Delete the release branch:
  ```bash
  git branch -d release/<version-number>
  git push origin --delete release/<version-number>
  ```

### 5. Creating a Hotfix Branch
- Create a hotfix branch from `main`:
  ```bash
  git checkout -b hotfix/<issue-name> main
  ```
- Fix the issue and commit changes.
- Merge the hotfix branch into `main`:
  ```bash
  git checkout main
  git merge hotfix/<issue-name>
  ```
- Tag the hotfix release:
  ```bash
  git tag -a <hotfix-version> -m "Hotfix <hotfix-version>"
  git push origin <hotfix-version>
  ```
- Merge the hotfix branch back into `develop`:
  ```bash
  git checkout develop
  git merge hotfix/<issue-name>
  ```
- Delete the hotfix branch:
  ```bash
  git branch -d hotfix/<issue-name>
  git push origin --delete hotfix/<issue-name>
  ```

---

## Best Practices
- Always keep `main` and `develop` branches protected.
- Regularly sync your local branches with the remote repository.
- Use meaningful and consistent branch names and commit messages.
- Conduct code reviews for all merge requests.

---

## References
- [Atlassian GitFlow Guide](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)
- [GitFlow Workflow](https://nvie.com/posts/a-successful-git-branching-model/)

