package sukasa.air.plotline.template;

public interface APIProcessTemplate<RESPONSE> {

	void validate();

	RESPONSE invoke();

	RESPONSE composeFailResultInfo();

}
