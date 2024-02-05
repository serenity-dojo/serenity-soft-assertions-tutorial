package junit5;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Soft Assertions with AssertJ")
public class SoftAssertionsWithAssertJTest {

    /**
     * Soft assertions will fail and report each assertion failure.
     * But beware: if there is an error (e.g. a null pointer), the test will fail immediately
     */
    @Test
    @DisplayName("Normal AssertJ soft assertions will show all the errors at the end")
    void basicAssertJSoftAsserts() {
        List<String> colors = Arrays.asList("red","green","blue");

        SoftAssertions softly = new SoftAssertions();

        // This line will cause the test to fail immediately
        // softly.assertThat(colors).contains(null);
        softly.assertThat(colors).contains("red");
        softly.assertThat(colors).contains("yellow");
        softly.assertThat(colors).contains("blue");
        softly.assertThat(colors).contains("orange");

        softly.assertAll();
    }

    @Steps
    CheckColours checkColours;

    /**
     * You can use AssertJ soft assertions inside Serenity steps.
     * This makes the assertions more visible in the reports.
     * But the errors will only be reported on the last step
     * (the last one you ran before calling assertAll())
     */
    @DisplayName("AssertJ soft assertions in steps will run all the steps and show the errors at the end")
    @Test
    void softAssertsInSteps() {
        List<String> colors = Arrays.asList("red","green","blue");
        SoftAssertions softly = new SoftAssertions();

        checkColours.forColors(colors);
        checkColours.softlyAssert(softly);

        checkColours.colorsShouldContain("red");
        checkColours.colorsShouldContain("yellow");
        checkColours.colorsShouldContain("blue");
        checkColours.colorsShouldContain("orange");

        softly.assertAll();
    }
}
