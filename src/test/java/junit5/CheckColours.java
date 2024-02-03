package junit5;

import net.serenitybdd.annotations.Step;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckColours {

    private List<String> colors;
    private SoftAssertions softly;

    @Step
    void colorsShouldContain(String expectedColor) {
       if (softly != null) {
           softly.assertThat(colors).contains(expectedColor);
       } else {
           assertThat(colors).contains(expectedColor);
       }
    }

    public void forColors(List<String> colors) {
        this.colors = colors;
    }

    public void softlyAssert(SoftAssertions softly) {
        this.softly = softly;
    }
}
