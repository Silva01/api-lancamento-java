package silva.daniel.project.app.web.account.annotations;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import silva.daniel.project.app.web.AccountController;
import silva.daniel.project.app.web.account.ActivateAccountTestPrepare;
import silva.daniel.project.app.web.account.DeactivateAccountTestPrepare;
import silva.daniel.project.app.web.account.EditAgencyOfAccountPrepare;
import silva.daniel.project.app.web.account.GetAccountListTestPrepare;
import silva.daniel.project.app.web.account.GetInformationAccountTestPrepare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(AccountController.class)
@Import({
        EditAgencyOfAccountPrepare.class,
        GetInformationAccountTestPrepare.class,
        GetAccountListTestPrepare.class,
        ActivateAccountTestPrepare.class,
        DeactivateAccountTestPrepare.class
})
public @interface EnableAccountPrepare {
}
