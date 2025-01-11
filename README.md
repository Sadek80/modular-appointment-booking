# Architecture Overview
It is a doctor appointment booking application following the **Modular Monolith Architecture**.

![Figure 1: Architecture Overview](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Architecture_Overview.png)

*Figure 1: Architecture Overview*

The system consists of 4 modules:

* **Doctor Availability**: which manages the doctor available slots. *(Layered Architecture)*
    
* **Appointment Booking**: allows the patient to book an appointment in a specific slot. (*Clean Architecture)*
    
* **Doctor Appointment Management**: provides a view for the doctor to see the appointment and manage them. *(Hexagonal Architecture)*
    
* **Appointment Confirmation**: a thin module to send confirmation to doctor and patient when interesting things happened. *(Simplest Architecture, Just a Public interface)*
    

As you can see from the *Figure 1,* communication between modules is one of the following:

* **Dotted Arrow**: represents Asynchronous Communication.
    
* **Filled Arrow**: represents Direct Call.
    

---

# Design Process

Once I had the requirement, I started to list the system functionalities and events in their timelines:

![Events Timeline](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Event_TImelines.png)

*Figure 2: Events Timeline*

This helped me to think more about the modules responsibilities and how to figure which module fit in which timeline and even exploring the boundaries of each module:

* **Slot Timeline**: doctor availability manages the slots, so it may fit in the slot timeline and also it can be the source of truth of the slots and the doctor info.
    
* **Patient Booking Timeline**: appointment booking allows the patient to book a slot, so it may fit in the patient timeline, also it can be the source of truth of the patient info.
    
* **Confirmation Sending**: appointment confirmation module definitely can fit here.
    
* **Doctor Appointment Timeline**: doctor appointment allows the doctor to manage his appointments, so it may fit in this timeline, but I felt in this stage it can be just a view of appointments with just two actions (cancel and complete).  
    

After that, I started to think more about, which module should be the source of truth of the appointments, for me it wasn’t clear at first, but then I’ve chosen the Appointment Booking Module as it is the starting point of the appointment, and this decision will affect a lot of the communication decision made later.

## Module Boundaries

In this stage I tried to figure out the module boundaries and a draft of the domain models they have:

![Module Boundaries](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Module_Boundaries.png)

*Figure 3: Module Boundaries*

* **Doctor Availability**: It isolates the slot management functionality from other modules and it is the source of the Slots and Doctor info and updates.
    
    * If allowed update happened to the slot it should be communicated with other modules.
        
* **Appointment Booking**: It isolates the patient and the booking functionality from other modules, and it is the source of the Appointments and Patient info and updates.
    
    * It reads the slots info from the Doctor Availability Module replicating doctor basic info.
        
    * also it should communicate with other modules when is appointment created.
        
* **Doctor Appointment Management**: It provides just a view of the doctor appointments:
    
    * Appointment Info synced from the Appointment Booking Module.
        
    * Canceling and Completing appointment should be synced back to the Appointment Booking Module.
        

## Database Design

Here I decided to use a shared database, but each module has its own schema.

### Doctor Availability

![db](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Doctor_Availability_db.png)

### Appointment Booking

![db](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Appointment_Booking_db.png)

### Doctor Appointment Management

![db](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Doctor_Appointment_Management_db.png)

## Features Design

Then, I started to design the main features flow and added some extra logic for the cycle.

I also added some general rules to the whole system to add some extra logic to test the complication of integration styles between modules:

* Doctor can create slot with time range, start time and end time ensuring that a single slot time range duration must be 2 hours.
    

* Doctor can only update slot (time and cost only) if and only if the current time is before the slot with at least 2 hours, less than that the update will be refused.
    
* slot time range should be in the same date of the original slot, if it is changing the date and the slot is already reserved. we can say please cancel the related appointment.
    
* as general rule we try to restrict updates to at least 2 hours from the starting time.
    

### Update Slot

![slot updated](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Slot_Updated_Sequence_Diagram.png)

*Figure 4: Slot Updated Sequence Diagram*

Here, I’ve introduced Slot Updating functionality:

* Doctor can update slot time and cost
    
* if the slot is reserved so the Doctor Availability Module should raise an “SlotUpdatedEvent”.
    
* the “SlotUpdatedEvent” gonna be handled by the Appointment Booking Module Asynchronously to update appointment related to the slot updated. and then raise “AppointmentUpdatedEvent”.
    
* the “AppointmentUpdatedEvent” gonna be handled by the Doctor Appointment Management to replicate the appointment updates.
    

**Note**: As we took the decision of making the Appointment Booking Module the source of truth of the appointment, so I make it the one which notifying the Doctor Appointment Management with the new update not the Doctor Availability Module.

### Create Appointment

![appointment created](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Appointment_Created_Sequence_Diagram.png)

*Figure 5: Appointment Created Sequence Diagram*

* After Patient choose the slot id to book, the Appointment Booking Module will talk to Doctor Availability Module asking for the slot info.
    
* After making the appointment creation logic it will ask the Doctor Availability Module to reserve the slot, so make it not available for other patients.
    
* If all is fine the Appointment Booking Module will raise “AppointmentCreatedEvent“.
    
    * handled internally and make a direct call to the Appointment Confirmation Module to notify Doctor and Patient
        
    
    * handled by the Doctor Appointment Module to replicate the appointment.
        

**Note**: Here we can have consistency issues, I decide to ignore them as I do not to want to complicate the implementation by either implement Saga Pattern or Distributed Transactions. I just want to try multiple integration styles.

### Cancel Appointment

![cancel](https://github.com/Sadek80/modular-appointment-booking/blob/1308fe514312e3d09653aaa6944a68ff7d644f7f/diagrams/Cancel_Appointment_Sequence_Diagram.png)

*Figure 6: Cancel Appointment Sequence Diagram*

* When a doctor cancels the appointment, it should be 2 hours at least before the appointment start time.
    
* Doctor Appointment Management Module then raise “AppointmentCanceledEvent”.
    
* The “AppointmentCanceledEvent“ will be handled by the Appointment Booking Module asking the Doctor Availability Module via direct call to release the slot, to make it available for booking.
    
* The Appointment Booking Module then raise another event “AppointmentCanceledEvent“ handled internally by communicating with Appointment Confirmation Module to notify the Patient that doctor has canceled the appointment.
    

**Note**: Here we can have consistency issues, I decided to ignore it too :D

## Project Setup

I’ve started to setup the project thinking of Cross-Cutting Concerns.

### Cross-Cutting Concerns (Shared Module)

I decided that I will build Shared Module that is open for all other modules, containing:

* Base Abstractions for Domain and Application Layers.
    
* Base Implementation for shared concepts like:
    
    * Logging
        
    * Clock
        
    * Event Bus
        
* Base Setup for presentation concerns like:
    
    * Base Controller
        
    * Global Exception Handling
        

### Modules Folder Structure

I decided that each module will follow the base folder structure as:

* **Internal**: contains the internal architecture folder structure, and isolated from other modules.
    
* **PublicAPI**: contains the public interfaces for other modules for communication.
    

# Running the Project

### Prerequisites

* **JDK 17** ([Amazon Cor](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)[retto).](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
    
* [PostgreSQL 10+.](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
    

1. If you have PgAdmin and Postgres running locally, so you can:
    

* change the password in the application.properties
    
* create new Db in your Postgres instance with the name “doctorAppointments“.
    

otherwise, you can run the docker-compose for the db setup:

```dockerfile
docker-compose up
```

2. Run the Project via Intellij IDEA
    
3. Test via Postman: [Posman Collection](https://github.com/Sadek80/modular-appointment-booking/blob/00958f576e9a76fa46bbc5fde3e2c14b4a9ab0b7/Doctor_Appointments.postman_collection.json)
