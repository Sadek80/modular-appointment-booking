CREATE TABLE patients (
    patient_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE appointments (
    appointment_id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    slot_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    doctor_name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    reserved_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP NULL,
    canceled_at TIMESTAMP NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients (patient_id) ON DELETE CASCADE
);

CREATE INDEX idx_appointments_patient_id ON appointments (patient_id);
CREATE INDEX idx_appointments_slot_id ON appointments (slot_id);
CREATE INDEX idx_appointments_doctor_id ON appointments (doctor_id);