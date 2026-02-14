
# Requirements – Starter Template

**Project Name:** HelpRent \
**Team:** Christian Batista, Thomas Lambert \
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-02-13
**Purpose:** This SRS document defines the scope and functional requirements of HelpRent

---

## 1. Overview
**Vision.** HelpRent is a web-based app that facilitates maintenance manegement for landlords, and allows easy requests and provaides maintenance guides for renters who need any repairs. It also provides seemless integration for any standardized units, helping landlords with easier setups for the properties they manage.

**Glossary**
- **Tenant/Renter:** person that is renting a unit from the landlord.
- **Help Guide:** Management/landlord provided guides for any maintenance that the renter can do themselves for any minor issues that don't require any maintenance staff on-site
- **Ticket:** maintenance request with details relevant to the unit and fixtures 
- **Fixtures:** Appliances and Furniture provided by the landlord to units

**Primary Users / Roles.**
- **Renter (e.g., Student/Patient/Pet Owner/etc. )** — Edit renter profiles; create  tikets; view relevant help guides; message assigned maintenance personel.
- **Landlord/Management (e.g., Teacher/Doctor/Pet Sitter/etc. )** — Create/Modify renter and maintenance profiles; set up and manage units; upload Help Guides; communicate with renters.
- **Maintenance** — access and manage  tickets; communitcate with renters; schedule maintenance visits.

**Scope (this semester).**
- Tenant, landlord, and maintenance account login and logout.
- Submit view, edit, and cancel maintenance requests.
- In-app messaging.
- Basic user account management.

**Out of scope (deferred).**
- Online rent payment processing.
- Lease agreement and legal document management.
- SMS or email notification delivery system.



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
- **US‑PROV‑001 — Create and Update Managed Units**  
  _Story:_ As a landlord, I want to edit a recently renovated set of units within my managed units on HelpRent   
  _Acceptance:_
  ```gherkin
  Scenario: Add and manage Units
    Given I would like to edit a unit 
    When  I change and update unit details
    Then  The tenant sees the updated set of fixtures, as well as any updated Help Guides
  ```

- **US‑PROV‑002 — Uploading Help Guides**  
  _Story:_ As a landlord, I want to upload a help guide for a common set of dishwashers in a set of units  
  _Acceptance:_
  ```gherkin
  Scenario: Upload Help Guides
    Given I have a pdf or image or text file of some kind with the information that makes up a help guide
    When  I upload the file and link it to existing units
    Then  any tenant residing in the linked units will be able to view the Help Guide
  ```
- **US‑PROV‑003 — Maintenance Statistics**  
  _Story:_ As a landlord, I want to view past tickets so that I can gage the state of a unit's fixtures 
  _Acceptance:_
  ```gherkin
  Scenario: View Maintenance Statistics
    Given there are tickets (open or closed)
    When  I access the maintenance records
    Then  I can view individual unit's history, if a fixutre is standard then i can view it's history too
  ```
- **US‑PROV‑004 — Access Messages**  
  _Story:_ As a landlord, I want to send a message to a renter who has opened a ticket 
  _Acceptance:_
  ```gherkin
  Scenario: View and Send Messages
    Given A tenant has opened a ticket
    Then  I can send a message to the renter informing them of any updates, or give them any needed information
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
### 2.3 SysAdmin Stories
- **US‑ADMIN‑001 — Managing Tickets**  
  _Story:_ As maintenance personel, I want acess tickets so that I can update them, view the unit assigned to the ticket, the unit's ticket or the fixture's ticket history, and view the tenant profile to send and recieve messages from the tenant   
  _Acceptance:_
  ```gherkin
  Scenario: New ticket
    Given I recieved a notification that a ticket has been assigned to me
    When  I view my assigned tickets
    Then  I can view all the details of the ticket, and close it once it has beed resolved
  ```

- **US‑ADMIN‑002 — Maintenance Scheduling**  
  _Story:_ As maintenance personel, I want to communicate with the tenant so that I can schedule a maintenance visit  
  _Acceptance:_
  ```gherkin
  Scenario: Tenant requires on-site assistance 
    Given a tenant needs help with a fixture in their unit
    When  I access the open ticket
    Then  I can offer the tenant time windows where I am available to go to their unit, or view time windows that the tenant themselves has provided
  ```
- **US‑ADMIN‑003 — Accessing Maintenance Statistics**  
  _Story:_ As maintenance personel, I want view maintenance history so that I can view common issues with particular fixtures or units  
  _Acceptance:_
  ```gherkin
  Scenario: 
    Given a ticket history with the unit or fixture
    When  I view ticket history
    Then  I can find any relevant past tickets
  ```
  - **US‑ADMIN‑004 — Access Messages**  
  _Story:_ As maintenance personel, I want send messages to a tenant so that they know I am on my way to their unit  
  _Acceptance:_
  ```gherkin
  Scenario: on-site maintenance required
    Given a time window for on-site maintenace was agreed upon
    When  I view the ticket
    Then  I can send messages directly to the tenant
  ```
---

## 3. Non‑Functional Requirements
- **Performance:** messages delivered in <5 sec; help guides load <2 sec 
- **Availability/Reliability:** 95% uptime; maintenance should still allow for tenants to access help guides
- **Security/Privacy:** secured sign in info for all roles and users;role based access checks;tenants cannot view other tenant info
- **Usability:** new users can complete profile and verify info in <5 minutes

---

## 4. Assumptions, Constraints, and Policies
- Modern Browsers; android and ios web browser support
- Course Timeline constraints

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
- Major changes should update this SRS.