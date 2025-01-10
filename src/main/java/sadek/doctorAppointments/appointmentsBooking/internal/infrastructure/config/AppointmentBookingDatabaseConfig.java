package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration("AppointmentBookingDataConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {AppointmentBookingConfig.ENTITIES_PACKAGE},
        entityManagerFactoryRef = AppointmentBookingConfig.ENTITY_MANAGER,
        transactionManagerRef = AppointmentBookingConfig.TRANSACTION_MANAGER
)
public class AppointmentBookingDatabaseConfig {

    @Bean(name = AppointmentBookingConfig.DATA_SOURCE)
    @ConfigurationProperties(prefix = AppointmentBookingConfig.DATABASE_PROPERTIES_PREFIX)
    public DataSource appointmentBookingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = AppointmentBookingConfig.ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean appointmentBookingEntityManager(
            @Qualifier(AppointmentBookingConfig.DATA_SOURCE) DataSource dataSource,
            final EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(AppointmentBookingConfig.ENTITIES_PACKAGE)
                .persistenceUnit(AppointmentBookingConfig.PERSISTENT_UNIT)
                .build();
    }

    @Bean(name = AppointmentBookingConfig.TRANSACTION_MANAGER)
    public PlatformTransactionManager appointmentBookingTransactionManager(
            @Qualifier(AppointmentBookingConfig.ENTITY_MANAGER) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
