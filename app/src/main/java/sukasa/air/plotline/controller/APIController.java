package sukasa.air.plotline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.utils.MapperUtil;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/")
public class APIController {

	@Autowired
	private LoginService loginService;

	@GetMapping({APIPathConstants.BLANK})
	public ResponseEntity<String> getDefaultMessage() {
		Date date = new Date();
		return new ResponseEntity<String>(
				"Welcome to the SukasaAirPlotlineApplication!!!" +
						"<br>" +
						"Started Successfully at Time : " + date,
				HttpStatus.OK);
	}

	@PostMapping(value = APIPathConstants.LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> loginAndFetchTokenController(@RequestBody Map<String, Object> requestMap) {

		LoginRequest loginRequest = (LoginRequest) MapperUtil.convertMap2Object(requestMap, LoginRequest.class);
		LoginResponse loginResponse = loginService.initiateLogin(loginRequest);

		return MapperUtil.convertObject2Map(loginResponse);
	}

}
