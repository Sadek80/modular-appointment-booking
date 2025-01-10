CREATE TABLE appointments (
    appointment_id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    patient_name VARCHAR(255) NOT NULL,
    doctor_id UUID NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    reserved_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP NULL,
    canceled_at TIMESTAMP NULL,
    status VARCHAR(50) NOT NULL
);