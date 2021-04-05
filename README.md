[![Build Status](https://travis-ci.com/Massipssa/auth-service.svg?token=1WzPHJsFpvuFQPyV28Cy&branch=master)](https://travis-ci.com/Massipssa/auth-service)
# Auth

The current service is ... to allow other third party (client) to be able to perform the following actions:
- Get the users, groups, roles and permissions from authentication provider (LDAP, Database, Kerberos)
- Chose desired authentication (Basic, JWT, Kerberos, OAuth2)

## Architecture

The service is composed of three main components and each component will run inside Docker container.

- API: it exposes the services
- Authentication provider
- Database: stores all actions performed by the client on the service
