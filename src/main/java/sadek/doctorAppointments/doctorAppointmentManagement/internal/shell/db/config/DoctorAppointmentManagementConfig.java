package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config;

public class DoctorAppointmentManagementConfig {
    public static final String DATA_SOURCE = "doctorAppointmentManagementDataSource";
    public static final String DATABASE_PROPERTIES_PREFIX = "sadek.doctor.appointments.doctor.appointment.management.datasource";
    public static final String ENTITY_MANAGER = "doctorAppointmentManagementEntityManager";
    public static final String ENTITIES_PACKAGE = "sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db";
    public static final String PERSISTENT_UNIT = "doctorAppointmentManagement";
    public static final String SCHEMA = "doctor_appointment_management";

    //Flyway
    public static final String FLYWAY = "doctorAppointmentManagementFlyway";
    public static final String FLYWAY_ENABLED = "${sadek.flyway.enabled:true}";
    public static final String FLYWAY_BASELINE = "${sadek.flyway.baseline-on-migrate:false}";
    public static final String FLYWAY_OUT_OF_ORDER = "${spring.flyway.out-of-order:false}";
    public static final String FLYWAY_LOCATIONS = "${sadek.doctor.appointments.doctor.appointment.management.flyway.locations}";
}
