package silva.daniel.project.app.web.account.annotations;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import silva.daniel.project.app.web.TransactionController;
import silva.daniel.project.app.web.account.transaction.RegisterTransactionTestPrepare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest(TransactionController.class)
@Import({
        RegisterTransactionTestPrepare.class
})
public @interface EnableTransactionPrepare {
}
