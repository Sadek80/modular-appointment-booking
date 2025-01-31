package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config;

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
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.DoctorAppointmentManagementCoreConfig;

import javax.sql.DataSource;

@Configuration("DoctorAppointmentDataConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {DoctorAppointmentManagementConfig.ENTITIES_PACKAGE},
        entityManagerFactoryRef = DoctorAppointmentManagementConfig.ENTITY_MANAGER,
        transactionManagerRef = DoctorAppointmentManagementCoreConfig.TRANSACTION_MANAGER
)
public class DoctorAppointmentManagementDatabaseConfig {

    @Bean(name = DoctorAppointmentManagementConfig.DATA_SOURCE)
    @ConfigurationProperties(prefix = DoctorAppointmentManagementConfig.DATABASE_PROPERTIES_PREFIX)
    public DataSource appointmentBookingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DoctorAppointmentManagementConfig.ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean appointmentBookingEntityManager(
            @Qualifier(DoctorAppointmentManagementConfig.DATA_SOURCE) DataSource dataSource,
            final EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(DoctorAppointmentManagementConfig.ENTITIES_PACKAGE)
                .persistenceUnit(DoctorAppointmentManagementConfig.PERSISTENT_UNIT)
                .build();
    }

    @Bean(name = DoctorAppointmentManagementCoreConfig.TRANSACTION_MANAGER)
    public PlatformTransactionManager appointmentBookingTransactionManager(
            @Qualifier(DoctorAppointmentManagementConfig.ENTITY_MANAGER) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
