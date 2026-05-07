# sp26-team8

## Title
HelpRent - Where landlord specials go to die.

## Team Members
Christian Batista

Thomas Lambert

## Description
> Allows landlords to provide renters with maintenance how-to's and the ability to request maintenance.
> The app will allow communication between the renters and landlords regarding maintenance.
> Provide guides on basic maintenance.
> Allows for scheduling of maintenance and repair between renter and maintenance staff.

## App Functions
1. Tenant:
    1. Submit maintenance requests
        - set title, category, priority (Low/Medium/High/Emergency), and description
        - view requests in Active, Completed, and Cancelled tabs
        - cancel open requests
    2. Confirm completed repairs 
        - close tickets marked as completed by maintenance
    3. View help guides
        - guides organized by fixture assigned to tenant's unit
        - supports images and PDFs
    4. Chat with assigned maintenance        
        - send text and file/image attachments
        - per-ticket chat threads
2. Landlord (Provider):
    1. Create and manage property 
        - add property with name and address
        - add and remove units
    2. Manage units
        - assign fixtures to units
        - create tenant accounts and assign to units
        - remove tenants from units
        - set unit status to maintenace
    3. Manage Fixtures
        - create fixtures with a title and description 
        - assign fixtures to units
        - link and unlink helpguides to fixtures
    4. create and upload helpGuides
        - upload PDF or image attachments
        - optionally link to a fixture
            - if not linked, it will appear as common to all units
        - set category and description
    5. Manage maintenance staff
        - create staff accounts
        - assign and remove staff from property
    6. Manage tickets
        - view all tickets across a property
        - assign tickets to staff
        - set priority and advance status
        - view unassigned tickets on dashboard
    7. Access ticket chats
        - view and send messages on any property ticket
3. Maintenance Staff:
    1. Manage assigned tickets 
        - view details of assigned tickets on dashboard
        - advance ticket status as they work on it
            - open -> in progress -> completed
    2. View property tickets
        - view all tickets on assigned property
        - view ticket history by assigned unit or by fixture
    3. Message tenants
        - view messages linked to assigned tickets
        - can send text and/or files/image attachements
    ---
    ## Running The Application
    ### Prerequisites
    - Java 24
    - Maven
    - PostgresSQL database (add/update application.properties with your db link)
    
    ### Steps to run
    1. Close repository (in github)
    2. Configure your database connection
        ```
        src/main/resources/application.properties
        ```
    3. Build and run
        - can be done in ide or by running the command below from the Application.java file's directory
        
        ```
        mvn spring-boot:run
        ```
    4. Open in browser
        - by default app runs on port 8080
    ``` 
    http://localhost:8080/
    ```
        
    --- 
    ## API Documentation

    This system allows tenants, landlords, and maintenance staff to manage rental maintenance.

    ---
    ## UI Endpoints
    All UI endpoints use Spring Security session-based authentication. The logged-in user is determined from the session (no userId parameter needed). 
    All POST forms require a CSRF token for session authentication.
    ### Authentication — `UserUiController`
 
    #### Login Page
    
    ```http
    GET /login
    ```
    
    Use Case: Display login form  
    Template: `login.ftlh`
    
    #### Login Submit
    
    ```http
    POST /login
    ```
    
    Use Case: Spring Security processes authentication, redirects to `/dashboard` on success  
    Note: handled by Spring Security
    
    #### Signup Page
    
    ```http
    GET /signup
    ```
    
    Use Case: Display landlord registration form  
    Template: `signup.ftlh`
    
    #### Signup Submit
    
    ```http
    POST /signup
    ```
    
    Use Case: Create a new landlord account, redirects to `/login`  
    Parameters: `email`, `password`, `firstName`, `lastName`
    
    #### Logout
    
    ```http
    POST /logout
    ```
    
    Use Case: End session, redirects to `/login`
    
    ---
    
    ### Dashboard — `UserUiController`
    
    #### Dashboard
    
    ```http
    GET /dashboard
    ```
    
    Use Case: Role-based dashboard  
    - **Landlord**: shows property info, unassigned tickets, all tickets  
    - **Maintenance**: shows assigned tickets  
    - **Tenant**: shows tickets, unit info, fixtures  
    Template: `dashboard.ftlh`
    ---
    
    ### Properties — `PropertyUiController`
    
    #### New Property Form
    
    ```http
    GET /properties/new
    ```
    
    Use Case: Display form to create a new property  
    Template: `property/propertyForm.ftlh`
    
    #### Create Property
    
    ```http
    POST /properties/new
    ```
    
    Use Case: Landlord creates a property, redirects to property detail  
    Parameters: `name`, `address`
    
    #### View Property
    
    ```http
    GET /properties/{propertyId}
    ```
    
    Use Case: Landlord views property detail (units, staff)  
    Template: `property/viewProperty.ftlh`
    
    #### New Staff Form
    
    ```http
    GET /properties/{propertyId}/staff/new
    ```
    
    Use Case: Display form to create a maintenance staff member  
    Template: `property/newStaffForm.ftlh`
    
    #### Create Staff
    
    ```http
    POST /properties/{propertyId}/staff/new
    ```
    
    Use Case: Landlord creates a staff account and assigns to property, redirects to property detail  
    Parameters: `email`, `password`, `firstName`, `lastName`
    
    #### Remove Staff
    
    ```http
    POST /properties/{propertyId}/staff/{staffId}/remove
    ```
    
    Use Case: Landlord removes a staff member from the property, redirects to property detail
    
    #### New Unit Form
    
    ```http
    GET /properties/{propertyId}/units/new
    ```
    
    Use Case: Display form to create a new unit  
    Template: `property/newUnitForm.ftlh`
    
    #### Create Unit
    
    ```http
    POST /properties/{propertyId}/units/new
    ```
    
    Use Case: Landlord creates a unit, redirects to property detail  
    Parameters: `unitNumber` (optional), `unitAddress` (optional)
    - handles null unitNumber as detached housing
    - handles null address as same address as property
    
    #### Remove Unit
    
    ```http
    POST /properties/{propertyId}/units/{unitId}/remove
    ```
    
    Use Case: Landlord deletes a unit, redirects to property detail
    
    ---
    
    ### Units — `UnitUiController`
    
    #### View Unit
    
    ```http
    GET /units/{unitId}
    ```
    
    Use Case: Landlord views unit detail (tenant, fixtures, tickets)  
    Template: `unit/viewUnit.ftlh`
    
    #### Add Fixture to Unit
    
    ```http
    POST /units/{unitId}/fixtures/add
    ```
    
    Use Case: Landlord assigns a fixture to the unit, redirects to unit detail  
    Parameters: `fixtureId`
    
    #### Remove Fixture from Unit
    
    ```http
    POST /units/{unitId}/fixtures/{fixtureId}/remove
    ```
    
    Use Case: Landlord removes a fixture from the unit, redirects to unit detail
    
    #### Set Unit Under Maintenance
    
    ```http
    POST /units/{unitId}/maintenance
    ```
    
    Use Case: Landlord sets unit status to MAINTENANCE, redirects to unit detail
    
    #### New Tenant Form
    
    ```http
    GET /units/{unitId}/tenant/new
    ```
    
    Use Case: Display form to create a tenant account  
    Template: `unit/newTenantForm.ftlh`
    
    #### Create Tenant
    
    ```http
    POST /units/{unitId}/tenant/new
    ```
    
    Use Case: Landlord creates a tenant account and assigns to unit, redirects to unit detail  
    Parameters: `email`, `password`, `firstName`, `lastName`
    
    #### Remove Tenant
    
    ```http
    POST /units/{unitId}/tenant/remove
    ```
    
    Use Case: Landlord removes tenant from unit, redirects to unit detail
    
    ---
    
    ### Tickets — `TicketUiController`
    
    #### View All Tickets
    
    ```http
    GET /tickets
    ```
    
    Use Case: Role-based ticket list  
    - **Landlord**: all tickets on their property  
    - **Maintenance**: their assigned tickets  
    - **Tenant**: their submitted tickets  
    Template: `ticket/list.ftlh`
    #### View Ticket Detail
    
    ```http
    GET /tickets/{ticketId}
    ```
    
    Use Case: View a single ticket with role-based actions (assign, status, cancel, complete, chat link)  
    Template: `ticket/viewTicket.ftlh`
    
    #### New Ticket Form (Landlord)
    
    ```http
    GET /tickets/new?unitId=1
    ```
    
    Use Case: Display form to create a ticket for a specific unit  
    Template: `ticket/newTicketForm.ftlh`
    
    #### Create Ticket (Landlord)
    
    ```http
    POST /tickets/new
    ```
    
    Use Case: Landlord creates a ticket, redirects to ticket detail  
    Parameters: `unitId`, `fixtureId` (optional), `title`, `description`, `category` (optional)
    
    #### Assign Ticket
    
    ```http
    POST /tickets/{ticketId}/assign
    ```
    
    Use Case: Landlord assigns ticket to staff, redirects to ticket detail  
    Parameters: `staffId`
    
    #### Set Priority
    
    ```http
    POST /tickets/{ticketId}/priority
    ```
    
    Use Case: Change ticket priority, redirects to ticket detail  
    Parameters: `priority` (LOW, MEDIUM, HIGH, EMERGENCY)
    
    #### Advance Status
    
    ```http
    POST /tickets/{ticketId}/status
    ```
    
    Use Case: Maintenance/landlord advances ticket status (OPEN → IN_PROGRESS → COMPLETED), redirects to ticket detail
    
    #### Complete Ticket
    
    ```http
    POST /tickets/{ticketId}/complete
    ```
    
    Use Case: Tenant/landlord confirms repair (COMPLETED → CLOSED), redirects to ticket detail
    
    #### Cancel Ticket
    
    ```http
    POST /tickets/{ticketId}/cancel
    ```
    
    Use Case: Cancel a ticket, redirects to ticket detail
    
    #### Tickets by Unit
    
    ```http
    GET /tickets/unit/{unitId}
    ```
    
    Use Case: View ticket history for a specific unit  
    Template: `ticket/list.ftlh`
    
    #### Tickets by Fixture
    
    ```http
    GET /tickets/fixture/{fixtureId}
    ```
    
    Use Case: View ticket history for a specific fixture  
    Template: `ticket/list.ftlh`
    
    #### Tenant Request Page
    
    ```http
    GET /tickets/request
    ```
    
    Use Case: Tenant views their request page with submit form and tabbed ticket list  
    Template: `ticket/request.ftlh`
    
    #### Tenant Submit Request
    
    ```http
    POST /tickets/request
    ```
    
    Use Case: Tenant submits a new maintenance request, redirects to request page  
    Parameters: `title`, `category`, `priority`, `description`
    
    #### Tenant Cancel Request
    
    ```http
    POST /tickets/tenant/request/{ticketId}/cancel
    ```
    
    Use Case: Tenant cancels their own request, redirects to request page
    
    ---
    
    ### Fixtures — `FixtureUiController`
    
    #### View All Fixtures
    
    ```http
    GET /fixtures
    ```
    
    Use Case: Landlord views all fixtures on their property  
    Template: `fixture/all.ftlh`
    
    #### View Fixture Detail
    
    ```http
    GET /fixtures/{fixtureId}
    ```
    
    Use Case: View fixture with linked help guides, unit assignment dropdown, and guide assignment dropdown  
    Template: `fixture/view.ftlh`
    
    #### New Fixture Form
    
    ```http
    GET /fixtures/new
    ```
    
    Use Case: Display form to create a new fixture  
    Template: `fixture/form.ftlh`
    
    #### Create Fixture
    
    ```http
    POST /fixtures/new
    ```
    
    Use Case: Landlord creates a fixture, redirects to fixtures list  
    Parameters: `title`, `description`
    
    #### Delete Fixture
    
    ```http
    POST /fixtures/{fixtureId}/delete
    ```
    
    Use Case: Landlord deletes fixture (unlinks all help guides first), redirects to fixtures list
    
    #### Assign Fixture to Unit
    
    ```http
    POST /fixtures/{fixtureId}/assign
    ```
    
    Use Case: Assign this fixture to a unit, redirects to fixture detail  
    Parameters: `unitId`
    
    #### Add Guide to Fixture
    
    ```http
    POST /fixtures/{fixtureId}/guides/add
    ```
    
    Use Case: Link a help guide to this fixture, redirects to fixture detail  
    Parameters: `helpGuideId`
    
    #### Remove Guide from Fixture
    
    ```http
    POST /fixtures/{fixtureId}/guides/{helpGuideId}/remove
    ```
    
    Use Case: Unlink a help guide from this fixture, redirects to fixture detail
    
    ---
    
    ### HelpGuides — `HelpGuideUiController`
    
    #### View All Help Guides
    
    ```http
    GET /helpGuides
    ```
    
    Use Case: Role-based view  
    - **Landlord**: all guides on their property  
    - **Tenant**: guides organized by fixture from their unit  
    Template: `helpGuide/all.ftlh`
    #### View Help Guide Detail
    
    ```http
    GET /helpGuides/{helpGuideId}
    ```
    
    Use Case: View a single help guide with file preview  
    Template: `helpGuide/view.ftlh`
    
    #### New Help Guide Form
    
    ```http
    GET /helpGuides/new
    ```
    
    Use Case: Display form to create a help guide (with fixture dropdown and file upload)  
    Template: `helpGuide/form.ftlh`
    
    #### Create Help Guide
    
    ```http
    POST /helpGuides/new
    ```
    
    Use Case: Landlord creates a help guide with optional file upload, redirects to guides list  
    Parameters: `title`, `description`, `category` (optional), `fixtureId` (optional), `file` (optional, multipart)
    
    #### Edit Help Guide Form
    
    ```http
    GET /helpGuides/{helpGuideId}/edit
    ```
    
    Use Case: Display form to edit an existing help guide  
    Template: `helpGuide/form.ftlh`
    
    #### Update Help Guide
    
    ```http
    POST /helpGuides/{helpGuideId}/edit
    ```
    
    Use Case: Landlord updates help guide, redirects to guide detail  
    Parameters: `title`, `description`, `category` (optional), `fixtureId` (optional), `file` (optional, multipart)
    
    #### Delete Help Guide
    
    ```http
    POST /helpGuides/{helpGuideId}/delete
    ```
    
    Use Case: Landlord deletes a help guide, redirects to guides list
    
    ---
    
    ### Chat / Messages — `MessageUiController`
    
    #### View Chat
    
    ```http
    GET /tickets/{ticketId}/chat
    ```
    
    Use Case: View the message thread for a ticket. Access restricted by `validateTicketUsage()`:  
    - **Tenant**: must be the submitter  
    - **Maintenance**: must be assigned to the ticket  
    - **Landlord**: must own the property  
    Template: `ticket/chat.ftlh`
    #### Send Message
    
    ```http
    POST /tickets/{ticketId}/chat
    Content-Type: multipart/form-data
    ```
    
    Use Case: Send a message with optional file attachment, redirects back to chat  
    Parameters: `content` (required unless file attached), `file` (optional, multipart)
    
    ---
    
    ### Error — `ErrorUiController`
    
    #### Error Page
    
    ```http
    GET /error
    ```
    Use Case: Generic error page; shows back link to dashboard if user is properly set up, or to login otherwise  
    Template: `error.ftlh`

    ---
    ## API EndPoints
    All API endpoints are prefixed with /api and return JSON. 
    Authentication is passed via userId query parameter.

    ### Users — `/api/users`
 
    #### Create User
    
    ```http
    POST /api/users
    Content-Type: application/json
    
    {
    "email": "user@example.com",
    "passwordHash": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "LANDLORD",
    "status": "ACTIVE"
    }
    ```
    
    Use Case: Create a new user account  
    Response: 201 Created
    
    #### Update User
    
    ```http
    PUT /api/users/1
    Content-Type: application/json
    
    {
    "email": "updated@example.com",
    "firstName": "John",
    "lastName": "Smith"
    }
    ```
    
    Use Case: Update user profile information  
    ```
    Response: 200 OK
    ```
    #### Get User by ID
    
    ```http
    GET /api/users/1
    ```
    
    Use Case: Retrieve a specific user's profile  
    ```
    Response: 200 OK
    ```
    #### Get All Users
    
    ```http
    GET /api/users
    ```
    
    Use Case: Retrieve all users  
    ```
    Response: 200 OK
    ```

    #### Delete User
    
    ```http
    DELETE /api/users/1
    ```
    
    Use Case: Delete a user account  
    ```
    Response: 204 No Content
    ```

    ---
    
    ### Properties — `/api/properties`
    
    #### Create Property
    
    ```http
    POST /api/properties?userId=1
    Content-Type: application/json
    
    {
    "name": "Sunset Apartments",
    "address": "123 Main St"
    }
    ```
    
    Use Case: Landlord creates a new property  
    ```
    Response: 201 Created
    ```

    #### Update Property
    
    ```http
    PUT /api/properties/1?userId=2
    Content-Type: application/json
    
    {
    "name": "Sunset Apartments Updated",
    "address": "123 Main St"
    }
    ```
    
    Use Case: Landlord updates property details  
    ```
    Response: 200 OK
    ```

    #### Transfer Property Ownership
    
    ```http
    PUT /api/properties/1/transfer?userId=2&newLandlordId=5
    ```
    
    Use Case: Landlord transfers property to another landlord  
    ```
    Response: 200 OK
    ```

    #### Add Staff to Property
    
    ```http
    PUT /api/properties/1/staff/3?userId=2
    ```
    
    Use Case: Landlord assigns a maintenance staff member to the property  
    ```
    Response: 200 OK
    ```

    #### Remove Staff from Property
    
    ```http
    DELETE /api/properties/1/staff/3?userId=2
    ```
    
    Use Case: Landlord removes a staff member from the property  
    ```
    Response: 200 OK
    ```

    #### Get All Properties
    
    ```http
    GET /api/properties
    ```
    
    Use Case: Retrieve all properties  
    ```
    Response: 200 OK
    ```

    #### Get Property by ID
    
    ```http
    GET /api/properties/1
    ```
    
    Use Case: Retrieve a specific property  
    ```
    Response: 200 OK
    ```

    #### Get Property by Landlord
    
    ```http
    GET /api/properties/landlord?userId=2
    ```
    
    Use Case: Retrieve the property owned by a specific landlord  
    ```
    Response: 200 OK
    ```

    #### Delete Property
    
    ```http
    DELETE /api/properties/1?userId=2
    ```
    
    Use Case: Landlord deletes a property  
    ```
    Response: 204 No Content
    ```

    ---
    
    ### 1.3 Units — `/api/units`
    
    #### Create Unit
    
    ```http
    POST /api/units?userId=2&propertyId=1
    Content-Type: application/json
    
    {
    "unitAddress": "123 Main St",
    "unitNumber": "101"
    }
    ```
    
    Use Case: Landlord creates a new unit under a property  
    ```
    Response: 201 Created
    ```

    #### Update Unit
    
    ```http
    PUT /api/units/1?userId=2
    Content-Type: application/json
    
    {
    "unitAddress": "123 Main St Updated",
    "unitNumber": "101A"
    }
    ```
    
    Use Case: Landlord updates unit details  
    ```
    Response: 200 OK
    ```

    #### Add Tenant to Unit
    
    ```http
    PUT /api/units/1/tenant/4?userId=2
    ```
    
    Use Case: Landlord assigns a tenant to a unit  
    ```
    Response: 200 OK
    ```

    #### Remove Tenant from Unit
    
    ```http
    DELETE /api/units/1/tenant?userId=2
    ```
    
    Use Case: Landlord removes the tenant from a unit  
    ```
    Response: 200 OK
    ```

    #### Add Fixture to Unit
    
    ```http
    PUT /api/units/1/fixtures/3?userId=2
    ```
    
    Use Case: Landlord assigns a fixture to a unit  
    ```
    Response: 200 OK
    ```

    #### Remove Fixture from Unit
    
    ```http
    DELETE /api/units/1/fixtures/3?userId=2
    ```
    
    Use Case: Landlord removes a fixture from a unit  
    ```
    Response: 200 OK
    ```

    #### Set Unit Under Maintenance
    
    ```http
    PUT /api/units/1/maintenance?userId=2
    ```
    
    Use Case: Landlord sets a unit's status to MAINTENANCE  
    ```
    Response: 200 OK
    ```

    #### Get Unit by ID
    
    ```http
    GET /api/units/1
    ```
    
    Use Case: Retrieve a specific unit  
    ```
    Response: 200 OK
    ```

    #### Get All Units
    
    ```http
    GET /api/units
    ```
    
    Use Case: Retrieve all units  
    ```
    Response: 200 OK
    ```

    #### Get Units by Property
    
    ```http
    GET /api/units/property/1?userId=2
    ```
    
    Use Case: Landlord retrieves all units in a property  
    ```
    Response: 200 OK
    ```

    #### Get Unit by Tenant
    
    ```http
    GET /api/units/tenant?userId=4
    ```
    
    Use Case: Tenant retrieves their assigned unit  
    ```
    Response: 200 OK
    ```

    #### Get Units by Status
    
    ```http
    GET /api/units/status?status=VACANT
    ```
    
    Use Case: Retrieve units filtered by status (VACANT, LEASED, MAINTENANCE)  
    ```
    Response: 200 OK
    ```

    #### Delete Unit
    
    ```http
    DELETE /api/units/1?userId=2
    ```
    
    Use Case: Landlord deletes a unit  
    ```
    Response: 204 No Content
    ```

    ---
    
    ### 1.4 Fixtures — `/api/fixtures`
    
    #### Create Fixture
    
    ```http
    POST /api/fixtures?userId=2
    Content-Type: application/json
    
    {
    "title": "Dishwasher",
    "description": "Bosch 500 Series"
    }
    ```
    
    Use Case: Landlord creates a new fixture  
    ```
    Response: 201 Created
    ```

    #### Update Fixture
    
    ```http
    PUT /api/fixtures/1?userId=2
    Content-Type: application/json
    
    {
    "title": "Dishwasher Updated",
    "description": "Bosch 800 Series"
    }
    ```
    
    Use Case: Landlord updates fixture details  
    ```
    Response: 200 OK
    ```

    #### Get All Fixtures
    
    ```http
    GET /api/fixtures
    ```
    
    Use Case: Retrieve all fixtures  
    ```
    Response: 200 OK
    ```

    #### Get Fixture by ID
    
    ```http
    GET /api/fixtures/1
    ```
    
    Use Case: Retrieve a specific fixture  
    ```
    Response: 200 OK
    ```

    #### Get Fixtures by Unit
    
    ```http
    GET /api/fixtures/unit/1?userId=2
    ```
    
    Use Case: Retrieve all fixtures assigned to a unit  
    ```
    Response: 200 OK
    ```

    #### Get Fixtures by Title
    
    ```http
    GET /api/fixtures/title/Dishwasher
    ```
    
    Use Case: Search fixtures by title  
    ```
    Response: 200 OK
    ```

    #### Delete Fixture
    
    ```http
    DELETE /api/fixtures/1?userId=2
    ```
    
    Use Case: Landlord deletes a fixture  
    ```
    Response: 204 No Content
    ```

    ---
    
    ### Tickets — `/api/tickets`
    
    #### Create Ticket
    
    ```http
    POST /api/tickets?unitId=1&userId=4&fixtureId=2
    Content-Type: application/json
    
    {
    "title": "Leaking faucet",
    "description": "Kitchen faucet dripping constantly",
    "category": "PLUMBING"
    }
    ```
    
    Use Case: Landlord or tenant creates a maintenance ticket (fixtureId is optional)  
    ```
    Response: 201 Created
    ```

    #### Update Ticket
    
    ```http
    PUT /api/tickets/1?userId=4
    Content-Type: application/json
    
    {
    "title": "Leaking faucet - urgent",
    "description": "Kitchen faucet now spraying",
    "category": "PLUMBING"
    }
    ```
    
    Use Case: Landlord or tenant updates ticket details  
    ```
    Response: 200 OK
    ```

    #### Assign Ticket
    
    ```http
    PUT /api/tickets/1/assign/3?userId=2
    ```
    
    Use Case: Landlord assigns ticket to maintenance staff  
    ```
    Response: 200 OK
    ```

    #### Set Ticket Priority
    
    ```http
    PUT /api/tickets/1/priority?priority=HIGH&userId=2
    ```
    
    Use Case: Set or change ticket priority (LOW, MEDIUM, HIGH, EMERGENCY)  
    ```
    Response: 200 OK
    ```

    #### Advance Ticket Status
    
    ```http
    PUT /api/tickets/1/status?userId=3
    ```
    
    Use Case: Maintenance or landlord advances ticket status (OPEN → IN_PROGRESS → COMPLETED)  
    ```
    Response: 200 OK
    ```

    #### Complete Ticket
    
    ```http
    PUT /api/tickets/1/complete?userId=4
    ```
    
    Use Case: Tenant or landlord confirms repair and closes ticket (COMPLETED → CLOSED)  
    ```
    Response: 200 OK
    ```

    #### Cancel Ticket
    
    ```http
    PUT /api/tickets/1/cancel?userId=4
    ```
    
    Use Case: Tenant or landlord cancels a ticket  
    ```
    Response: 200 OK
    ```

    #### Get Ticket by ID
    
    ```http
    GET /api/tickets/1
    ```
    
    Use Case: Retrieve a specific ticket  
    ```
    Response: 200 OK
    ```

    #### Get All Tickets
    
    ```http
    GET /api/tickets
    ```
    
    Use Case: Retrieve all tickets  
    ```
    Response: 200 OK
    ```

    #### Get Tickets by Tenant
    
    ```http
    GET /api/tickets/tenant?userId=4
    ```
    
    Use Case: Tenant retrieves their submitted tickets  
    ```
    Response: 200 OK
    ```

    #### Get Tickets by Staff
    
    ```http
    GET /api/tickets/staff?userId=3
    ```
    
    Use Case: Maintenance staff retrieves their assigned tickets  
    ```
    Response: 200 OK
    ```

    #### Get Tickets by Property
    
    ```http
    GET /api/tickets/property/1?userId=2
    ```
    
    Use Case: Landlord retrieves all tickets on their property  
    ```
    Response: 200 OK
    ```

    #### Get Tickets by Unit
    
    ```http
    GET /api/tickets/unit/1?userId=2
    ```
    
    Use Case: View ticket history for a specific unit  
    ```
    Response: 200 OK
    ```

    #### Get Tickets by Fixture
    
    ```http
    GET /api/tickets/fixture/1
    ```
    
    Use Case: View ticket history for a specific fixture  
    ```
    Response: 200 OK
    ```

    #### Delete Ticket
    
    ```http
    DELETE /api/tickets/1?userId=2
    ```
    
    Use Case: Delete a ticket  
    ```
    Response: 204 No Content
    ```

    ---
    
    ### Help Guides — `/api/helpGuides`
    
    #### Create Help Guide
    
    ```http
    POST /api/helpGuides?userId=2&fixtureId=1
    Content-Type: application/json
    
    {
    "title": "Dishwasher Troubleshooting",
    "description": "Steps to resolve common dishwasher issues",
    "category": "Appliances"
    }
    ```
    
    Use Case: Landlord creates a help guide (fixtureId is optional — null means general guide)  
    ```
    Response: 201 Created
    ```

    #### Update Help Guide
    
    ```http
    PUT /api/helpGuides/1?userId=2
    Content-Type: application/json
    
    {
    "title": "Dishwasher Troubleshooting v2",
    "description": "Updated steps",
    "category": "Appliances"
    }
    ```
    
    Use Case: Landlord updates help guide content  
    ```
    Response: 200 OK
    ```

    #### Change Help Guide Fixture
    
    ```http
    PUT /api/helpGuides/1/transfer_fixture?fixtureId=3&userId=2
    ```
    
    Use Case: Landlord reassigns a help guide to a different fixture (omit fixtureId to unlink)  
    ```
    Response: 200 OK
    ```

    #### Get All Help Guides
    
    ```http
    GET /api/helpGuides
    ```
    
    Use Case: Retrieve all help guides  
    ```
    Response: 200 OK
    ```

    #### Get Help Guide by ID
    
    ```http
    GET /api/helpGuides/1
    ```
    
    Use Case: Retrieve a specific help guide  
    ```
    Response: 200 OK
    ```

    #### Get Help Guides for Tenant
    
    ```http
    GET /api/helpGuides/tenantView?userId=4
    ```
    
    Use Case: Tenant retrieves help guides for their unit's fixtures  
    ```
    Response: 200 OK
    ```

    #### Get Help Guides by Fixture
    
    ```http
    GET /api/helpGuides/fixture/1
    ```
    
    Use Case: Retrieve all help guides linked to a fixture  
    ```
    Response: 200 OK
    ```

    #### Get Help Guides by Category
    
    ```http
    GET /api/helpGuides/category?category=Plumbing
    ```
    
    Use Case: Search help guides by category  
    ```
    Response: 200 OK
    ```

    #### Delete Help Guide
    
    ```http
    DELETE /api/helpGuides/1?userId=2
    ```
    
    Use Case: Landlord deletes a help guide  
    ```
    Response: 204 No Content
    ```