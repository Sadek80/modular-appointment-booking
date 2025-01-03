package sadek.doctorAppointments.doctorAvailability.internal.data.config;

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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {DoctorAvailabilityConfig.ENTITIES_PACKAGE},
        entityManagerFactoryRef = DoctorAvailabilityConfig.ENTITY_MANAGER,
        transactionManagerRef = DoctorAvailabilityConfig.TRANSACTION_MANAGER
)
public class DoctorAvailabilityDatabaseConfig {

    @Bean(name = DoctorAvailabilityConfig.DATA_SOURCE)
    @ConfigurationProperties(prefix = DoctorAvailabilityConfig.DATABASE_PROPERTIES_PREFIX)
    public DataSource doctorAvailabilityDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DoctorAvailabilityConfig.ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean doctorAvailabilityEntityManager(
            @Qualifier(DoctorAvailabilityConfig.DATA_SOURCE) DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(DoctorAvailabilityConfig.ENTITIES_PACKAGE)
                .persistenceUnit(DoctorAvailabilityConfig.PERSISTENT_UNIT)
                .build();
    }

    @Bean(name = DoctorAvailabilityConfig.TRANSACTION_MANAGER)
    public PlatformTransactionManager doctorAvailabilityTransactionManager(
            @Qualifier(DoctorAvailabilityConfig.ENTITY_MANAGER) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
