package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class DoctorAppointmentManagementFlywayConfig {
    @Value(DoctorAppointmentManagementConfig.FLYWAY_ENABLED)
    private boolean enabled;
    @Value(DoctorAppointmentManagementConfig.FLYWAY_BASELINE)
    private boolean baselineOnMigrate;
    @Value(DoctorAppointmentManagementConfig.FLYWAY_OUT_OF_ORDER)
    private boolean outOfOrder;
    @Value(DoctorAppointmentManagementConfig.FLYWAY_LOCATIONS)
    private String[] locations;


    @Bean(DoctorAppointmentManagementConfig.FLYWAY)
    @DependsOn(DoctorAppointmentManagementConfig.DATA_SOURCE)
    public Flyway flyway(@Qualifier(DoctorAppointmentManagementConfig.DATA_SOURCE) DataSource dataSource) {
        if (!enabled) {
            return null;
        }

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .outOfOrder(outOfOrder)
                .baselineOnMigrate(baselineOnMigrate)
                .locations(locations)
                .schemas(DoctorAppointmentManagementConfig.SCHEMA)
                .load();

        flyway.migrate();
        return flyway;
    }
}
