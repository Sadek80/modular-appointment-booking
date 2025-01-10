package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class AppointmentBookingFlywayConfig {
    @Value(AppointmentBookingConfig.FLYWAY_ENABLED)
    private boolean enabled;
    @Value(AppointmentBookingConfig.FLYWAY_BASELINE)
    private boolean baselineOnMigrate;
    @Value(AppointmentBookingConfig.FLYWAY_OUT_OF_ORDER)
    private boolean outOfOrder;
    @Value(AppointmentBookingConfig.FLYWAY_LOCATIONS)
    private String[] locations;


    @Bean(AppointmentBookingConfig.FLYWAY)
    @DependsOn(AppointmentBookingConfig.DATA_SOURCE)
    public Flyway flyway(@Qualifier(AppointmentBookingConfig.DATA_SOURCE) DataSource dataSource) {
        if (!enabled) {
            return null;
        }

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .outOfOrder(outOfOrder)
                .baselineOnMigrate(baselineOnMigrate)
                .locations(locations)
                .schemas(AppointmentBookingConfig.SCHEMA)
                .load();

        flyway.migrate();
        return flyway;
    }
}
