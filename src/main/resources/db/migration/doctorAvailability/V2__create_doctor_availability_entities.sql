CREATE TABLE doctors (
    doctor_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE slots (
    slot_id UUID PRIMARY KEY,
    doctor_id UUID NOT NULL REFERENCES doctors(doctor_id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    cost DECIMAL NOT NULL CHECK (cost > 0),
    is_reserved BOOLEAN NOT NULL DEFAULT FALSE
);