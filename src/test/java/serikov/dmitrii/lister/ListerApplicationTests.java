package serikov.dmitrii.lister;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest
//@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
class ListerApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
