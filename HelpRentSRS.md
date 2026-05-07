
# Requirements

**Project Name:** HelpRent \
**Team:** Christian Batista, Thomas Lambert \
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-02-13
**Purpose:** This SRS document defines the scope and functional requirements of HelpRent. It outline user stories and system behavior for tenants, management, and landlords.

---

## 1. Overview
**Vision.** HelpRent: A web-based app that facilitates maintenance manegement for landlords, and allows easy requests and provaides maintenance guides for renters who need any repairs. It also provides seemless integration for any standardized units, helping landlords with easier setups for the properties they manage.

**Glossary**
- **Tenant/Renter:** person that is renting a unit from the landlord.
- **Help Guide:** Management/landlord provided guides for any maintenance that the renter can do themselves for any minor issues that don't require any maintenance staff on-site
- **Ticket:** maintenance request with details relevant to the unit and fixtures 
- **Fixtures:** Appliances and Furniture provided by the landlord to units
- **Property:** A landlord's managed property containing units, fixtures, staff, and help guides.

**Primary Users / Roles.**
- **Tenant** — Log in to a landlord-created account; submit and cancel maintenance requests; view help guides for their unit's fixtures; chat with assigned maintenance staff on tickets.
- **Landlord** — Register an account; create and manage properties, units, fixtures, and help guides; create tenant and maintenance staff accounts; assign staff to properties; assign tickets to staff; view all tickets across their property; chat on any ticket thread.
- **Maintenance Staff** — Log in to a landlord-created account; view assigned tickets and all tickets on their property; advance ticket status; chat with tenants on assigned tickets.

**Scope (this semester).**
- Role-based authentication and login/logout (Spring Security with CSRF protection).
- Property, unit, fixture, and help guide CRUD for landlords.
- Tenant and maintenance staff account creation by landlords.
- Ticket submission, cancellation, status progression, and assignment.
- In-app per-ticket messaging with file/image attachments.
- Help guide viewing for tenants organized by fixture.
- Role-based dashboards showing relevant ticket and property information.

**Out of scope (deferred).**
- Online rent payment processing.
- Lease agreements and legal document management.
- SMS or email notification delivery system.
- Availability/scheduling system for maintenance visits.
- Ticket search and filtering and statistics.
---

## 2. Functional Requirements (User Stories)

### 2.1 Tenant Stories
- **US‑TENA‑001 — Set Maintenance Priority**  
  _Story:_ As a tenant, I want to indicate urgency of a maintenance request, so that critical issues are addressed quickly.
  _Acceptance:_
  ```gherkin
  Scenario: Tenant selects request priority
    Given the tenant is creating a maintenace request
    When  the tenant selects a priority level 
      And submits the request
    Then  the system records the selected priority 
      And displays it with the request details
  ```

- **US‑TENA‑002 — Receive Status Notifications**  
  _Story:_ As a tenant, I want to receive notifications when my manitenance request status changes, so that I stay informed without constantly checking the system.
  _Acceptance:_
  ```gherkin
  Scenario: Tenant receives status update notification
    Given the tenant has submitted a maintenance request
    When  the status of the request changes
    Then  then the system notifies the tenant
  ```

- **US‑TENA‑003 — Cancel or Modify Request**  
  _Story:_ As a tenant, I want to cancel or update a maintenance request, so that I can correct mistakes or report resolved issues.  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant updates maintenance request
    Given the tenant has submitted a maintenance request
    When  the tenant edits or cancels the request
    Then  the system updates the request information 
      And reflects new status
  ```

- **US‑TENA‑004 — Confirm Completion of Repair**  
  _Story:_ As a tenant, I want to confirm that a repair has been completed, so that maintenance requests are properly closed.  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant confirms repair completion
    Given a maintenance request is marked as completed
    When  the tenant confirms the repair is satisfactory
    Then  the system marks the request as closed
  ```

- **US‑TENA‑005 — Emergency Request Option**  
  _Story:_ As a tenant, I want to flag emergency maintenance issues, so that urgent problems are notified.  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant flags emergency issue
    Given the tenant is creating a maintenance request
    When  the tenant marks the issue as emergency
    Then  the system records the request as high urgency 
      And highlights it appropriately
  ```

- **US‑TENA-006 — Search Maintenance Guides**  
  _Story:_ As a tenant, I want to search maintenance guides, so that I can quickly find relevant information. 
  _Acceptance:_
  ```gherkin
  Scenario: Tenant searches for maintenance guides
    Given the tenant is viewing maintenance guides
    When  the tenant enters a search term
    Then  the system displays matching guides
  ```

- **US‑TENA-007 — View Frequently Asked Questions**  
  _Story:_ As a tenant, I want to view frequently asked maintenace questions, so that I can resolve minor issues independently.
  _Acceptance:_
  ```gherkin
  Scenario: Tentant views FAQ section
    Given the tenant is authenticated
    When  the tenant accesses the FAQ section
    Then  the system displays maintenance-related questions and answers
  ```
- **US‑TENA-008 — Log Into Account**  
  _Story:_ As a tenant, I want to log into my account, so that I can access maintenance features.  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant logs in successfully
    Given the tenant has a registered accont
    When  the tenant enters valid credentials
    Then  the system grants access to the tenant dashboard
  ```
- **US‑TENA-009 — View Dashboard**  
  _Story:_ As a tenant, I want to view my dashboard, so that I can see my maintenance activity in one place.  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant view dashboard
    Given the tenant is logged into the system
    When  the tenant accesses the dashboard
    Then  the system displays the tenant's maintenance information
  ```

### 2.2 Provider (Landlord) Stories
- **US‑PROV‑001 — Create and Manage Property**
  _Story:_ As a landlord, I want to create a property and manage its units, so that I can organize my rental portfolio.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord creates and manages a property
    Given the landlord is logged in
    When  the landlord creates a new property with a name and address
    Then  the system saves the property linked to the landlord
      And the landlord can add units to the property
  ```

- **US‑PROV‑002 — Manage Units and Assign Fixtures**
  _Story:_ As a landlord, I want to add units to my property and assign fixtures to those units, so tenants see what appliances are in their unit.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord assigns fixtures to a unit
    Given the landlord has a property with units and fixtures
    When  the landlord selects a fixture from the dropdown on the unit detail page
      And clicks Add Fixture
    Then  the fixture is linked to the unit
      And any tenant in that unit can see the fixture and its help guides
  ``
- **US‑PROV‑003 — Create and Manage Fixtures**
  _Story:_ As a landlord, I want to create fixtures and link help guides to them, so tenants have self-service repair information.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord creates a fixture and links a help guide
    Given the landlord is on the fixtures page
    When  the landlord creates a new fixture with title and description
      And links a help guide from the fixture detail page dropdown
    Then  the fixture is saved to the property
      And the help guide is associated with the fixture
      And tenants whose units have this fixture can see the guide
  ```

- **US‑PROV‑004 — Upload Help Guides**
  _Story:_ As a landlord, I want to upload help guides with file attachments and optionally link them to fixtures.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord uploads a help guide
    Given the landlord is on the help guide creation page
    When  the landlord fills in title, description, category
      And optionally selects a fixture and uploads a file (PDF or image)
      And submits the form
    Then  the system saves the help guide with the file stored in /uploads/helpGuides/
      And the guide appears in the landlord's help guide list
  ```

- **US‑PROV‑005 — Renter Profile Management**  
  _Story:_ As a landlord, I want to create and edit a tenant's renter profile to assign them their units and set up their account
  _Acceptance:_
  ```gherkin
  Scenario: Renter Profile creation and editing
    Given A new tenant has moved into a unit
    When  I create anew renter profile for the tenant
    Then  the unit they reside in is linked in their profile
  ```
- **US‑PROV‑005 — Tenant Account Management**
  _Story:_ As a landlord, I want to create tenant accounts and assign them to units, so tenants can log in and submit requests.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord creates a tenant account
    Given the landlord is viewing a unit detail page
    When  the landlord clicks Assign Tenant and fills in the tenant's name, email, and password
      And submits the form
    Then  the system creates a TENANT user account
      And assigns the tenant to the unit
      And the tenant can log in and access their dashboard
  ```

- **US‑PROV‑006 — Maintenance Staff Management**
  _Story:_ As a landlord, I want to create maintenance staff accounts and assign them to my property, so they can receive and work on tickets.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord creates and assigns a staff member
    Given the landlord is viewing their property detail page
    When  the landlord clicks Add New Staff Member and fills in the staff details
      And submits the form
    Then  the system creates a MAINTENANCE user account
      And adds the staff member to the property's staff list
  ```

- **US‑PROV‑007 — Assign Tickets to Staff**
  _Story:_ As a landlord, I want to assign open tickets to maintenance staff, so the right person handles each repair.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord assigns a ticket
    Given an unassigned ticket exists on the landlord's property
    When  the landlord selects a staff member from the dropdown on the ticket detail page
      And clicks Assign
    Then  the system links the staff member to the ticket
      And the ticket appears in that staff member's assigned tickets list
  ```


- **US‑PROV‑008 — View All Property Tickets**
  _Story:_ As a landlord, I want to view all tickets across my property, so I can monitor maintenance activity.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord views ticket list
    Given the landlord is logged in
    When  the landlord navigates to the tickets page
    Then  the system displays all tickets from units on the landlord's property
  ```

- **US‑PROV‑009 — Access Ticket Chat**
  _Story:_ As a landlord, I want to view and participate in ticket chat threads, so I can stay informed and communicate with tenants and staff.
  _Acceptance:_
  ```gherkin
  Scenario: Landlord views ticket chat
    Given a ticket exists on the landlord's property
    When  the landlord opens the chat for that ticket
    Then  the system displays the message history
      And the landlord can send messages
  ```
### 2.3 SysAdmin Stories
- **US‑MAINT‑001 — View Assigned Tickets**
  _Story:_ As maintenance staff, I want to view tickets assigned to me, so I know what repairs need attention.
  _Acceptance:_
  ```gherkin
  Scenario: Staff views assigned tickets
    Given the staff member is logged in
    When  the staff member views the dashboard or tickets page
    Then  the system displays all tickets assigned to them
      And each ticket shows title, status, priority, unit, and tenant info
  ```

- **US‑MAINT‑002 — Advance Ticket Status**
  _Story:_ As maintenance staff, I want to update a ticket's status, so the tenant and landlord can track repair progress.
  _Acceptance:_
  ```gherkin
  Scenario: Staff advances ticket status
    Given the staff member has an assigned ticket with status OPEN
    When  the staff member clicks the advance status button
    Then  the system changes status from OPEN to IN_PROGRESS
      And clicking again changes IN_PROGRESS to COMPLETED
      And the tenant can then confirm and close the ticket
  ```

- **US‑MAINT‑003 — Chat with Tenant on Assigned Ticket**
  _Story:_ As maintenance staff, I want to message the tenant on my assigned ticket, so I can coordinate the repair.
  _Acceptance:_
  ```gherkin
  Scenario: Staff sends a message on their assigned ticket
    Given the staff member is assigned to a ticket
    When  the staff member opens the ticket chat and sends a message
    Then  the message appears in the chat thread
      And the tenant can see and respond to it
  ```

- **US‑MAINT‑004 — View Property Tickets**
  _Story:_ As maintenance staff, I want to view all tickets on my assigned property, so I can see the broader maintenance picture.
  _Acceptance:_
  ```gherkin
  Scenario: Staff views all property tickets
    Given the staff member is assigned to a property
    When  the staff member navigates to the tickets page
    Then  the system displays all tickets on that property
  ```

## 3. Non‑Functional Requirements
- **Performance:** Pages load within standard server-side rendering times (for Spring FreeMarker); file uploads handled via MultipartFile with files stored on disk.
- **Availability/Reliability:** 95% uptime; maintenance should still allow for tenants to access help guides
- **Security/Privacy:** Spring Security with role-based access control; CSRF protection on all POST forms; passwords hashed via BCrypt (PasswordConfig); tenants cannot access other tenants' data; role-based route restrictions in SecurityConfig.
- **Usability:** new users can complete profile and verify info in <5 minutes

---

## 4. Assumptions, Constraints, and Policies
- Modern Browsers; android and ios web browser support
- Course Timeline constraints
- Single property per landlord user
- One tenant per unit
- Landlord handles creation and secure distribution of tenant and staff user accounts

---

## 5. Milestones (course‑aligned)
- **M2 Requirements** — this file + stories opened as issues. 
- **M3 High‑fidelity prototype** — core customer/provider flows fully interactive. 
- **M4 Design** — architecture, schema, API outline. 
- **M5 Backend API** — key endpoints + tests. 
- **M6 Increment** — ≥2 use cases end‑to‑end. 
- **M7 Final** — complete system & documentation. 

---

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.  
- This SRS has been updated with major changes
