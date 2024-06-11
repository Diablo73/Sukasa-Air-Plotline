package sukasa.air.plotline.template;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APIProcessTemplateImpl {

	@SneakyThrows
	public static <RESPONSE> RESPONSE execute(String apiName, APIProcessTemplate<RESPONSE> processCallback) {

		log.info("API : " + apiName);

		RESPONSE response = null;

		try {
			processCallback.validate();

			response = processCallback.invoke();

		} catch (Exception e) {
			log.info("ERROR : " + e.getMessage());
			response = processCallback.composeFailResultInfo();
		} finally {

		}
		return response;
	}
}
