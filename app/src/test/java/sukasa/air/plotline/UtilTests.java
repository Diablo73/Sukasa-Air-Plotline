package sukasa.air.plotline;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import sukasa.air.plotline.utils.JwtTokenUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SukasaAirPlotlineApplication.class)
public class UtilTests {

	private static final String email = "asdfg.f5.si";
	private static final String jwtToken = JwtTokenUtil.generateToken(email);

	@Test
	public void jwtTokenUtilVerifyTokenTest() {
		Assert.assertTrue(JwtTokenUtil.verifyToken(jwtToken, email));
		Assert.assertFalse(JwtTokenUtil.verifyToken(jwtToken.concat("0"), email));
	}

	@Test
	public void jwtTokenUtilisTokenExpiredTest() {
		Assert.assertFalse(JwtTokenUtil.isTokenExpired(jwtToken));
		Assert.assertTrue(JwtTokenUtil.isTokenExpired(jwtToken.concat("0")));
	}

	@Test
	public void jwtTokenUtilGetSubjectFromTokenTest() {
		Assert.assertEquals(email, JwtTokenUtil.getSubjectFromToken(jwtToken));
	}
}
