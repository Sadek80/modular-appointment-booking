package sadek.doctorAppointments;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

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

}
