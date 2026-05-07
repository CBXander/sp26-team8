# HelpRent
**Project Name:** HelpRent
**Version:** 1.0
**Date:** 2026-05-07
**Purpose:** This document outlines comprehensive test scenarios for the functional requirements in the HelpRent system.

## Actors
- Provider P: landlord
- Customer C: tenant
- Staff S: maintenance
- Ticket T: maintenance ticket
- Unit U: unit to be rented out

## Use Cases
### 1. Provider: 
#### US-PROV-001 -- Register & Manage Property Profile, US-PROV-002 -- Create Unit & Manage Unit
> 1. P1 registers a landlord account and logs in for the first time.
> 2. P1 sets up property details.
> 3. P1 creates units U1 and U2.
> 4. P1 creates C1 and assigns U1 to C1.
> 5. P1 views and edits unit information.
> 6. P1 creates S1.
> 6. P1 exits the application

### 2. Customer:
US-CUST-001 - Login & View Dashboard
> 1. Tenant C1 logs in for the first time.
> 2. C1 is redirected to the tenant dashboard.
> 3. C1 views assigned information for U1.
> 4. C1 exits the application

### 3. Customer:
US-CUST-002 - Submit Maintenance Request
> 1. Tenant C1 logs in.
> 2. C1 navigates to the maintenance request page.
> 3. C1 creates maintenance ticket T1 with:
    - title
    - category
    - priority
    - description
> 4. T1 is saved with status "OPEN"/
> 5. C1 views T1 in the Active Request tab.
> 6. C1 exits the application

### 4. Customer & Staff:
US-PROV-003 - Assign Maintenance Staff

US-STAFF-001 — Respond to Maintenance Requests
> 1. Tenant C1 logs into the system.
> 2. C1 opens ticket T1 chat.
> 3. C1 sends a text message and uploads an image attachment.
> 4. Maintenance staff S1 logs in and opens ticket T1.
> 5. S1 views the image attachment and replies to C1.
> 6. C1 refreshes chat and sees the response.
> 7. Both users exit the application.

### 5. Provider & Staff:
US-PROV-003 — Assign Maintenance Staff

US-STAFF-002 — Update Ticket Status
> 1. Landlord P1 logs in.
> 2. P1 views unassigned maintenance ticket T1.
> 3. P1 assigns maintenance staff S1 to T1.
> 4. S1 logs in and changes ticket T1 status from:
    - OPEN → IN_PROGRESS
> 5. S1 later changes status:
    - IN_PROGRESS → COMPLETED
> 6. Tenant C1 views T1 under the Completed tab.
> 7. All users exit the application.

### 6. Customer:
US-CUST-004 — Cancel Maintenance Request
> 1. Tenant C1 logs in.
> 2. C1 opens active ticket T2.
> 3. C1 cancels ticket T2.
> 4. T2 status changes to “CANCELLED”.
> 5. T2 appears in the Cancelled tab.
> 6. C1 exits the application.

### 7. Provider:
US-PROV-004 — Create & Manage Help Guides
> 1. Landlord P1 logs in.
> 2. P1 creates help guide G1 with:
    - title
    - description
    - category
    - fixture association
> 3. P1 uploads an image attachment to G1. (optional)
> 4. P1 saves the guide.
> 5. Tenant C1 logs in and views G1 under the Guides section.
> 6. Both users exit the application.

### 8. Customer:
US-CUST-005 — View Fixtures & Help Guides
> 1. Tenant C1 logs in.
> 2. C1 opens the dashboard.
> 3. C1 views fixtures assigned to unit U1.
> 4. C1 opens fixture details.
> 5. C1 views associated help guides and attachments.
> 6. C1 exits the application.