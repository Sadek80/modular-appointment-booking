package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.config;

public class AppointmentBookingConfig {
    public static final String DATA_SOURCE = "appointmentBookingDataSource";
    public static final String DATABASE_PROPERTIES_PREFIX = "sadek.doctor.appointments.appointment.booking.datasource";
    public static final String ENTITY_MANAGER = "appointmentBookingEntityManager";
    public static final String ENTITIES_PACKAGE = "sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db";
    public static final String PERSISTENT_UNIT = "appointmentBooking";
    public static final String SCHEMA = "appointment_booking";

    //Flyway
    public static final String FLYWAY = "appointmentBookingFlyway";
    public static final String FLYWAY_ENABLED = "${sadek.flyway.enabled:true}";
    public static final String FLYWAY_BASELINE = "${sadek.flyway.baseline-on-migrate:false}";
    public static final String FLYWAY_OUT_OF_ORDER = "${spring.flyway.out-of-order:false}";
    public static final String FLYWAY_LOCATIONS = "${sadek.doctor.appointments.appointment.booking.flyway.locations}";
}
