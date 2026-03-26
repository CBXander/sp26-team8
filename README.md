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
1. Renter:
    1. Edit profile information
        - edit name and profile picture
    2. Create tickets for maintenance 
        - request maintenance
            - preset maintenance options
            - can write what the issue is
            - set availability for maintenance to schedule a visit
    3. View maintenance tutorials
        - view how-tos
            - photos, videos, and/or text guides
    4. Access to messages with maintenance        
        - send photos, videos, and text
        - set availability for maintenance to schedule a visit
2. Landlord (Provider):
    1. Create/Modify/Remove renter profile 
        - create renter profiles for new renters
        - assign unit/apt number to renter profile
    2. Create and upload maintenance guides 
        - upload text files or pdfs or images
    3. View maintenance statistics 
        - view recent tickets and their location (apt number or unit) 
        - view maintenace personel assigned to tickets
    4. Access renter messages
        - send messages to renters
        - send and view messages to maintenace personel
        - view and send messages to renters and maintenance through tickets
    5. Add and manage units to managed unit list
        - can add units, as well as any standard appliances and furniture in the unit
3. Maintenance Personel:
    1. Manage tickets 
        - Edit or mark tickets as completed
        - Delete tickets (sends a message to renter)
    2. Schedule maintenance
        - based on availability of renter, schedules a repair
        - can send a request for availability if renter doesnt set any
    3. View maintenace statistics
        - per unit, see history of common issues 
        - if appliance is common, history of appliance common issues
    4. Access to messages sent by renters
        - view messages related to assigned tickets
        - send messages to renter through assigned tickets

    ## API Documentation

    This system allows tenants, landlords, and maintenance staff to manage rental maintenance.

    ---

    ### Create User
    ```http
    POST /api/users

    Body:
    {
    "email": "tenant@example.com",
    "passwordHash": "123456",
    "firstName": "John",
    "lastName": "Doe",
    "status": "ACTIVE",
    "role": "TENANT"
    }
    ```
    Use Case: Create a new user  
    Response: 201 Created

    ---

    ### Get All Users
    ```http
    GET /api/users
    ```
    Use Case: View all users  
    Response: 200 OK

    ---

    ### Create Property
    ```http
    POST /api/properties?userId=2

    Body:
    {
    "name": "Test Property",
    "address": "123 Main St"
    }
    ```
    Use Case: Landlord creates a property  
    Response: 201 Created

    ---

    ### Create Unit
    ```http
    POST /api/units?userId=2&propertyId=1

    Body:
    {
    "unitAddress": "123 Main St",
    "unitNumber": "A1",
    "status": "VACANT"
    }
    ```
    Use Case: Landlord adds a unit  
    Response: 201 Created

    ---

    ### Assign Tenant to Unit
    ```http
    PUT /api/units/1/tenant/1?userId=2
    ```
    Use Case: Assign tenant to a unit  
    Response: 200 OK

    ---

    ### Create Ticket
    ```http
    POST /api/tickets?unitId=1&userId=1

    Body:
    {
    "title": "AC Not Cooling",
    "description": "Air conditioner is not working",
    "category": "HVAC",
    "priority": "HIGH"
    }
    ```
    Use Case: Tenant creates maintenance request  
    Response: 201 Created

    ---

    ### Get Tickets by Tenant
    ```http
    GET /api/tickets/tenant?userId=1

    Use Case: Tenant views their tickets  
    Response: 200 OK
    ```
    ---

    ### Assign Ticket
    ```http
    PUT /api/tickets/1/assign/3?userId=2
    ```
    Use Case: Landlord assigns ticket to maintenance staff  
    Response: 200 OK

    ---

    ### Update Ticket Status
    ```http
    PUT /api/tickets/1/status?userId=3
    ```
    Use Case: Maintenance updates ticket status  
    Response: 200 OK

    ---

    ### Delete Property
    ```http
    DELETE /api/properties/1?userId=2
    ```
    Use Case: Landlord deletes a property  
    Response: 204 No Content