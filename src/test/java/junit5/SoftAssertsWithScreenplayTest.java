package junit5;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
public class SoftAssertsWithScreenplayTest {

    /**
     * With Screenplay, we can (optionally) use the Ensure class
     * to perform assertions in the same style as AssertJ
     * Without soft assertions, the first Ensure will fail and the following ones will be skipped
     */
    @Test
    @DisplayName("Without soft asserts, Ensure statements after the first failing one will be skipped")
    void screenplayAssertsUsingEnsureAndSkippingAfterTheFirstFailure() {
        List<String> colors = Arrays.asList("red", "green", "blue");

        Actor sophie = Actor.named("Sophie");

        sophie.attemptsTo(
                Ensure.that(colors).contains("red"),
                Ensure.that(colors).contains("yellow"),
                Ensure.that(colors).contains("blue"),
                Ensure.that(colors).contains("orange"),
                Ensure.that(colors).contains("green")
        );
    }

    /**
     * We can use the Ensure.enableSoftAssertions() and
     * Ensure.reportSoftAssertions() to turn normal Ensure assertions
     * into soft asserts.
     * When you use soft asserts with Ensure, the failing assertions
     * will be reported in each assertion in the reports.
     * In the console, they are grouped together.
     */
    @Test
    @DisplayName("With soft asserts, each Ensure statements is a separate assertion that passes or fails")
    void screenplaySoftAssertsUsingEnsure() {
        List<String> colors = Arrays.asList("red", "green", "blue");

        Actor sophie = Actor.named("Sophie");

        Ensure.enableSoftAssertions();
        sophie.attemptsTo(
                Ensure.that(colors).contains("red"),
                Ensure.that(colors).contains("yellow"),
                Ensure.that(colors).contains("blue"),
                Ensure.that(colors).contains("orange"),
                Ensure.that(colors).contains("green")
        );
        Ensure.reportSoftAssertions();
    }

    /**
     * I like Ensure statements to be in the test itself, as it makes
     * expectations more visible. But for more complex tests, you may
     * want to include Ensure statements inside nested actions.
     */
    @Test
    @DisplayName("With soft asserts, each assertion step is a separate assertion that passes or fails")
    void screenplaySoftAssertsUsingNestedEnsures() {
        List<String> colors = Arrays.asList("red", "green", "blue");

        Actor sophie = Actor.named("Sophie");

        sophie.attemptsTo(
                ensureThatEachColorIsIncludedFrom(colors, "red", "green","blue", "yellow", "blue", "orange")
        );
    }

    private static Performable ensureThatEachColorIsIncludedFrom(List<String> colors, String... expectedColors) {
        return Task.where("{0} ensures that each color is included from " + Arrays.asList(expectedColors),
                actor -> {
                    Ensure.enableSoftAssertions();
                    for (String color : expectedColors) {
                        actor.attemptsTo(Ensure.that(colors).contains(color));
                    }
                    Ensure.reportSoftAssertions();
                }
        );

    }
}
