
package junit5;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
public class SoftAssertsWithScreenplayAndBeforeEachTest {

    @BeforeEach
    void setupSoftAssertions() {
        Ensure.enableSoftAssertions();
    }

    @AfterEach
    void reportSoftAssertions() {
        Ensure.reportSoftAssertions();
    }
    /**
     * With Screenplay, we can (optionally) use the Ensure class
     * to perform assertions in the same style as AssertJ
     */
    @Test
    // WORKS AS EXPECTED
    void screenplayAssertsUsingEnsureAndBeforeAfter() {
        List<String> colors = Arrays.asList("red","green","blue");

        Actor sophie = Actor.named("Sophie");

        sophie.attemptsTo(
                Ensure.that(colors).contains("red"),
                Ensure.that(colors).contains("yellow"),
                Ensure.that(colors).contains("blue"),
                Ensure.that(colors).contains("orange")
        );
    }

}
