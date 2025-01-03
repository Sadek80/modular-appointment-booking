package sadek.doctorAppointments.doctorAvailability.internal.data.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class DoctorAvailabilityFlywayConfig {
    @Value(DoctorAvailabilityConfig.FLYWAY_ENABLED)
    private boolean enabled;
    @Value(DoctorAvailabilityConfig.FLYWAY_BASELINE)
    private boolean baselineOnMigrate;
    @Value(DoctorAvailabilityConfig.FLYWAY_OUT_OF_ORDER)
    private boolean outOfOrder;
    @Value(DoctorAvailabilityConfig.FLYWAY_LOCATIONS)
    private String[] locations;


    @Bean(DoctorAvailabilityConfig.FLYWAY)
    @DependsOn(DoctorAvailabilityConfig.DATA_SOURCE)
    public Flyway flyway(@Qualifier(DoctorAvailabilityConfig.DATA_SOURCE) DataSource dataSource) {
        if (!enabled) {
            return null;
        }

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .outOfOrder(outOfOrder)
                .baselineOnMigrate(baselineOnMigrate)
                .locations(locations)
                .schemas(DoctorAvailabilityConfig.SCHEMA)
                .load();

        flyway.migrate();
        return flyway;
    }
}
