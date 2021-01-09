### Spring Security
* At launch it generate random password  and default user ***user***

### Basics
* **Authentication:** prouves that user is who is claiming to be (who are you ?)
    * Knowledge based auth (with a password, pin code, ...)
    * Possession asek auth (send msg, email, ...)
    * Multifactor: Knowledge + Pessession
* **Authorization:**  Can user do what he's trying to do
* **Principal:** currently logged user  (account)
* **Granted Authority (Permissions):** what user is granted to do ?
* **Roles:** set of Granted Authorities

### Authentication types
* In Spring boot we configure Authentication Manger to enable Auth
* It uses filter to manage security (delegating filters proxy)
* Authentication Porvider class is reponsible for auth
    * An app can implement multiple Authentication Provider (LDAP, JDBC, ...)
    * Spring coordinates Providers using AuthicationManager class
    * Auth Provider must specify with Authentication it supports
* When auth is **OK** principal is sotred in Thread Context (local)

1. **Form basic auth**

2. **Basic Auth**
    * Every request sent to server must specify user and password
    * user and password are encoded as **Basic64 ??**
    * Redirect by default to page **index** inside **resources/static** folder
    * **Drawbacks**
        * We cannot logout (because evry request must indicate user and password)

<img src="screeshots/basic_auth.PNG"/> 

3. **OAuth2**
    * Get access from third party (ex: from Gmail, Facebook)
    * App A ask App B to be authiticate. If App B accepte (user accept) it will send him a Token

3. **Role based Auth**
    * We may need to deny access to some api

4. **Permissions based Auth**
    * We can define it using **antMatcher** (No clear !!)
    *  Order matters within **antMatcher**

5. **OAuth**

6. **JWT**

- Used for **authorization**, user should be authenticated yet
- Useful with microservice
- Format: 
    - Header: algorithm and type
    - Payload: Subject, name and expiration date
    - Verify signature    


### Single Sign On (SSO)

1. **Security Assertion Markup Language (SAML)**
    * Use one single point to authentication user to different applications
    * Compromised of:
        * User: to be identified
        * Identity provider
        * Service provider: applications to which user want to access
    * Only one authentication is needed and then the SAML will provide an assertion

2. **Kerberos**

### Multiple authentication provider (LDAP, Database, In memory ...)
* They are queried in the order they where declared
* Actually we find:
    * Database
    * Ldap
    * Kerberos

### Encrypt password
* BCrypt
### User
* It must have:
    * Username
    * Password (must be encoded)
    * Role(s) : is a set of permissions, (e.g. Role **ADMIN** has permissions: write, read, execute)
    * Authorities / Permissions

* Docker image

     ```bash
        sudo docker run -d \
            --name postgres-db \
            -h postgres-server \
            -e POSTGRES_PASSWORD=password \
            -p 5432:5432 \
            postgres
     ```

https://www.youtube.com/watch?v=her_7pa0vrg
https://www.youtube.com/watch?v=EPv9-cHEmQw
https://www.youtube.com/watch?v=vtPkZShrvXQ&t=1319s

https://dzone.com/articles/database-connection-pooling-in-java-with-hikaricp