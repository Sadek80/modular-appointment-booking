package sadek.doctorAppointments;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SpringBootTest
class DoctorAppointmentsApplicationTests {

	@Test
	void contextLoads() {
		var modules = ApplicationModules.of(DoctorAppointmentsApplication.class);

		modules.forEach(System.out::println);
	}

	@Test
	void verifyModularity() {
		var modules = ApplicationModules.of(DoctorAppointmentsApplication.class);

		modules.forEach(System.out::println);

		ApplicationModules.of(DoctorAppointmentsApplication.class).verify();

	}

	@Test
	void validateCleanArchitectureDependencies() {
		JavaClasses importedClasses = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("sadek.doctorAppointments.appointmentsBooking");

		layeredArchitecture()
				.consideringOnlyDependenciesInLayers()
				// Define layers
				.layer("API").definedBy("sadek.doctorAppointments.appointmentsBooking.internal.api..")
				.layer("Application").definedBy("sadek.doctorAppointments.appointmentsBooking.internal.application..")
				.layer("Domain").definedBy("sadek.doctorAppointments.appointmentsBooking.internal.domain..")
				.layer("Infrastructure").definedBy("sadek.doctorAppointments.appointmentsBooking.internal.infrastructure..")

				// Define layer dependencies
				.whereLayer("Domain").mayNotAccessAnyLayer()
				.whereLayer("Application").mayOnlyAccessLayers( "Domain")
				.whereLayer("Infrastructure").mayOnlyAccessLayers("API", "Application", "Domain")
				.whereLayer("API").mayOnlyAccessLayers("Infrastructure", "Application", "Domain")

				.check(importedClasses);
	}

	@Test
	void validateHexagonalArchitectureDependencies() {
		JavaClasses importedClasses = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("sadek.doctorAppointments.doctorAppointmentManagement.internal");

		layeredArchitecture()
				.consideringOnlyDependenciesInLayers()
				// Define layers
				.layer("Core").definedBy("sadek.doctorAppointments.doctorAppointmentManagement.internal.core..")
				.layer("Shell").definedBy("sadek.doctorAppointments.doctorAppointmentManagement.internal.shell..")

				// Define layer dependencies
				.whereLayer("Shell").mayOnlyAccessLayers("Core")
				.whereLayer("Core").mayNotAccessAnyLayer()

				.check(importedClasses);
	}

	@Test
	void validateLayeredArchitectureDependencies() {
		JavaClasses importedClasses = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("sadek.doctorAppointments.doctorAvailability.internal");

		layeredArchitecture()
				.consideringOnlyDependenciesInLayers()
				// Define layers
				.layer("API").definedBy("sadek.doctorAppointments.doctorAvailability.internal.api..")
				.layer("Business").definedBy("sadek.doctorAppointments.doctorAvailability.internal.business..")
				.layer("Data").definedBy("sadek.doctorAppointments.doctorAvailability.internal.data..")

				// Define layer dependencies
				.whereLayer("API").mayOnlyAccessLayers("Business", "Data")
				.whereLayer("Business").mayOnlyAccessLayers("Data")
				.whereLayer("Data").mayNotAccessAnyLayer()

				.check(importedClasses);
	}

}
