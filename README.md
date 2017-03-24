# Catalog API Demo App

Demo app providing examples of common Catalog API interactions.
  
## Usage

### Personal Access Token

In order to execute the examples in this demo, you'll need to access your personal access token in
Square's Developer Portal.
 
1. Visit https://connect.squareupstaging.com/apps
2. Click "New Application"
3. Create an application using the name "Catalog API Demo"
4. Copy the Personal Access Token

### List Available Examples

```
mvn compile exec:java "-Dexec.args=-list-example"
```

### Execute an Example

```
mvn compile exec:java "-Dexec.args=<example name> -token <accessToken>"
```

Example:
```
mvn compile exec:java "-Dexec.args=create_item -token abcdefghijk"
```
