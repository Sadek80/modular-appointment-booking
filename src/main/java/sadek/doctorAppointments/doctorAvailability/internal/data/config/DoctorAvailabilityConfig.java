package sadek.doctorAppointments.doctorAvailability.internal.data.config;

public class DoctorAvailabilityConfig {
    public static final String DATA_SOURCE = "doctorAvailabilityDataSource";
    public static final String DATABASE_PROPERTIES_PREFIX = "sadek.doctor.appointments.doctor.availability.datasource";
    public static final String ENTITY_MANAGER = "doctorAvailabilityEntityManager";
    public static final String TRANSACTION_MANAGER = "doctorAvailabilityTransactionManager";
    public static final String ENTITIES_PACKAGE = "sadek.doctorAppointments.doctorAvailability.internal.data";
    public static final String PERSISTENT_UNIT = "doctorAvailability";
    public static final String SCHEMA = "doctor_availability";

    //Flyway
    public static final String FLYWAY = "doctorAvailabilityFlyway";
    public static final String FLYWAY_ENABLED = "${sadek.flyway.enabled:true}";
    public static final String FLYWAY_BASELINE = "${sadek.flyway.baseline-on-migrate:false}";
    public static final String FLYWAY_OUT_OF_ORDER = "${spring.flyway.out-of-order:false}";
    public static final String FLYWAY_LOCATIONS = "${sadek.doctor.appointments.doctor.availability.flyway.locations}";
}
