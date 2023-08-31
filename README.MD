# How to Run the Backend

### Requiremnts

- JDK 17+
- Any IDE with Java EE support
- MySQL installation
- Internet connection (first time build only)

### Steps

1. clone the project
2. In `/codex-website-backend/src/main/resources/application.properties` ,change the credentials to your MySQL details
3. Run the Schema generator.sql file to generate the database
4. Run the `CodexApplication.java` at the root of `com.woxsen.codex` package.
5. The application will run on localhost:8080 by default and all APIs can be accessed from there.

## API Description

### Refrences -

User -

```
id - String (UUID),
username - String,
password - String
```

Member -

```
id - String (UUID),
name - String,
linkedInURL - String,
githubURL - String,
memberRole - String [Can only be EXECUTIVE, PRESIDENT, VICEPRESIDENT, CORE, MENTOR]
```

Event -

```
id - String (UUID),
title - String,
description - String,
priority - Integer,
date - String (in format - 'YYYY-MM-DD'),
url - String | null,
customField - String | null
```

EmptyResponseBody -

```
success: String,
statusCode: Integer
```

FileResponse -

```
filename: String,
fileDownloadUri: String,
fileType: String,
size: Long
```

ErroResponse -

```
message: String,
httpCode: Integer,
success: String
```

### General Notes:

- Every PATCH request requires you to send all the fields in the request body or else the field will be updated to `null`
- In endpoint, <(some name)>, refers to that area being a path variable

### Endpoint Descriptions

User

| Method | Endpoint        | Takes             | Returns           | Description                                                                          |
| ------ | --------------- | ----------------- | ----------------- | ------------------------------------------------------------------------------------ |
| POST   | /api/user       | User (without ID) | EmptyResponseBody | Adds a user with given username and password                                         |
| POST   | /api/user/login | User (without ID) | EmptyResponseBody | Checks if user with credentials exists. statusCode is 200 (OK) or 401 (Unauthorized) |
| POST   | /api/user       | User (Without ID) | EmptyResponseBody | Deletes user if credentials are correct                                              |

Member

| Method | Endpoint                       | Takes                                                                    | Returns                        | Description                                     |
| ------ | ------------------------------ | ------------------------------------------------------------------------ | ------------------------------ | ----------------------------------------------- |
| GET    | /api/member                    | Nothing                                                                  | Member[]                       | Returns list of all members                     |
| GET    | /api/member/\<id>              | ID path variable                                                         | Member                         | Returns member with given ID                    |
| POST   | /api/member                    | Member (Without ID)                                                      | JSON - { "id": String (UUID) } | Adds Member to database                         |
| DELETE | /api/member/\<id>              | ID path variable                                                         | EmptyResponseBody              | Deletes Member with given ID                    |
| PATCH  | /api/member/\<id>              | ID path variable AND Member                                              | Updated Member                 | Updated Member in DB with given Member's fields |
| PUT    | /api/member/uploadFile/\<id>   | ID Path variable AND Form with key "image" and file attached to that key | FileResponse                   | Uploads file to server and returns a URI to it  |
| GET    | /api/member/downloadFile/\<id> | ID path Variable                                                         | Image                          | Image of Member with given ID                   |

Event
| Method | Endpoint | Takes | Returns | Description |
| ------ | ------------------------------ | ------------------------------------------------------------------------ | ------------------------------ | ----------------------------------------------- |
|GET|/api/event|Nothing|Event[]|**Not Recommended** unless user specifically requests all events. Retrieves all events|
|POST|/api/event/dateRange|JSON format - {<br>from: String ('YYYY-MM-DD'), <br>to: String ('YYYY-MM-DD')<br>}|Event[]|**Recommended**. Retrieves events between given dates. if to and from are same, only retrieves records for that date|
|GET|/api/event/\<id>|ID path variable|Event|Returns event with given ID|
|POST|/api/event|Event (without ID)|JSON Format - <br>{ id: String (UUID) }|Adds given event to DB
|DELETE|/api/event/\<id>|ID path variable|EmptyResponseBody|Deletes Event with given ID|
|PATCH|/api/event/\<id>|ID path variable AND Event as request body|Updated Event|Updates Event in DB with given Event|
|PUT|/api/event/uploadFile/\<id>|ID Path variable AND Form with key "image" and file attached to that key|FileResponse|Uploads image for given Event to DB|
|GET|/api/event/downloadFile/\<id>|ID Path variable|Image|Gets image associated with given event
