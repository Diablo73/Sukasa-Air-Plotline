package sukasa.air.plotline.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.diablo73.common.utils.ValidatorUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.User;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Route("login")
@PageTitle("Login")
public class LoginView extends BaseVaadinView {

    private static User user;
    private Notification notification;
    private List<String> statusCodesForRedirection = Arrays.asList("03", "04");

    public LoginView(@Autowired LoginService loginService) {

        add(new H1("Login"));

        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Welcome to Sukasa Air...");
        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.getForm().setTitle("Login");
        loginI18n.getForm().setUsername("Email");
        loginOverlay.setI18n(loginI18n);
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.setOpened(true);
        loginOverlay.addLoginListener(e -> {
            LoginResponse loginResponse = login(loginService, e);
            if (statusCodesForRedirection.contains(loginResponse.getStatusCode())) {
                UI.getCurrent().navigate("vaa/fetchAll");
                loginOverlay.setOpened(false);
            }
        });
        add(loginOverlay);
    }

    private LoginResponse login(LoginService loginService, AbstractLogin.LoginEvent loginEvent) {
        String email = loginEvent.getUsername();
        String password = loginEvent.getPassword();
        LoginResponse loginResponse = new LoginResponse(StatusEnum.INVALID_EMAIL, email);
        if (ValidatorUtil.isValidEmail(email)) {
            loginResponse = loginService.initiateRegisterAndLogin(new LoginRequest(email, password));
            user = new User(loginResponse.getEmail(), loginResponse.getToken());
        }
        if (Objects.nonNull(notification)) {
            notification.close();
        }
        notification = Notification.show(loginResponse.getMessage() + " : " + email);
        notification.setPosition(Notification.Position.MIDDLE);
        return loginResponse;
    }
}
