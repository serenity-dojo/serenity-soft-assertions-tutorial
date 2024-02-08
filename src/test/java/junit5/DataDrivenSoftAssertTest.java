package junit5;

import com.google.common.base.Splitter;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Soft Assertions with AssertJ and parameterized tests")
public class DataDrivenSoftAssertTest {

    SoftAssertions softly;

    @BeforeEach
    void setupSoftAssertions() {
        softly = new SoftAssertions();
    }

    @AfterEach
    void reportSoftAssertions() {
        softly.assertAll();
    }

    /**
     * JUnit 5 parameterised tests will always act like soft assertions.
     * If one row fails, the others will still get executed.
     */
    @DisplayName("Basic AssertJ assertions in a data-driven test will execute all tests regardless of failures")
    @ParameterizedTest
    @CsvSource(delimiter = '|',
            value = {
                    "red,green,blue | green",
                    "red,green,blue | orange",
                    "red,green,blue | blue",
                    "red,green,blue | purple",
                    "red,green,blue | red"
            }
    )
    void basicAssertJSoftAsserts(String colorList, String expectedColor) {
        List<String> colors = Splitter.on(",").splitToList(colorList);
        assertThat(colors).contains(expectedColor);
   }
}
