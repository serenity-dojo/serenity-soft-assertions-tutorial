package cucumber.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import kotlin.jvm.functions.Function1;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.PerformableExpectation;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import java.util.Collection;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;

public class AssertionsStepDefinitions {

    @Before("@multistep")
    public void enableSoftAsserts() {
        Ensure.enableSoftAssertions();
    }

    @After("@multistep")
    public void reportSoftAsserts() {
        Ensure.reportSoftAssertions();
    }

    List<String> actualColors;

    @Given("the scenario has started")
    public void scenarioHasStarted() {
    }

    @Given("{actor} has the following colours:")
    public void sophieHasTheFollowingColours(Actor actor, List<String> colors) {
        actualColors = colors;
    }

    @When("she checks the colors")
    public void sheChecksTheColors() {
    }

    @Then("{actor} should see the following colors:")
    public void theFollowingColorsShouldBePresent(Actor actor, List<String> expectedColors) {
        Ensure.enableSoftAssertions();
        expectedColors.forEach(
                color -> actor.attemptsTo(Ensure.that(actualColors).contains(color))
        );
        Ensure.reportSoftAssertions();
    }

    @Then("{actor} should see all the following colors:")
    public void theFollowingColorsShouldBeVisible(Actor actor, List<String> expectedColors) {
        expectedColors.forEach(
                color -> actor.attemptsTo(Ensure.that(actualColors).contains(color))
        );
    }

    @Then("{actor} should see the following colors in nested checks:")
    public void theFollowingColorsShouldBePresentInDepth(Actor actor, List<String> expectedColors) {
        Ensure.enableSoftAssertions();
        actor.attemptsTo(
                checkColors(actualColors, expectedColors)
        );
        Ensure.reportSoftAssertions();
    }

    private Performable checkColors(List<String> actualColors, List<String> expectedColors) {
        return Task.where("{0} inspects the colors",
                actor -> expectedColors.forEach(
                        color -> theActorInTheSpotlight().attemptsTo(
                                Ensure.that(actualColors).contains(color)
                        )
                )
        );
    }


    private Performable checkTheColor(String color, List<String> actualColors) {
        return Task.where("{0} inspects the color",
                Ensure.that(actualColors).contains(color)
        );
    }

    SoftAssertions softly;

    @Then("{actor} ensures the following colors are present:")
    public void ensureTheFollowingColorsArePresent(Actor actor, List<String> expectedColors) {
        Performable[] ensureThatEachColorIsIncluded = expectedColors.stream()
                .map(color -> Ensure.that(actualColors).contains(color))
                .toList()
                .toArray(new Performable[]{});

        actor.attemptsTo(ensureThatEachColorIsIncluded);
    }

    @Then("the following colors are present:")
    public void theFollowingColorsArePresent(List<String> expectedColors) {
        softly = new SoftAssertions();
        expectedColors.forEach(
                color -> theActorInTheSpotlight().attemptsTo(
                        checkColor(color, actualColors)
                )
        );
        softly.assertAll();
    }

    private Performable checkColor(String color, List<String> colors) {
        return Task.where("{0} checks that the color " + color + " is present in '" + colors + "'",
                actor -> softly.assertThat(colors).contains(color)
        );
    }

    @And("{actor} should proceed to the next step if the color is found")
    public void shouldProceedToTheNextStepIfTheColorIsFound(Actor actor) {
        actor.attemptsTo(
                Ensure.that(true).isTrue()
        );
    }

    @Then("{actor} should see the color {}")
    public void sheShouldSeeTheColor(Actor actor, String color) {
        Assertions.assertThat(actualColors).contains(color);
    }

    @Then("{actor} should see the nested color {}")
    public void sheShouldSeeTheNestedColor(Actor actor, String color) {
        actor.attemptsTo(checkTheColor(color, actualColors));
    }

    @Then("{actor} should see the step color {}")
    public void sheShouldSeeTheStepColor(Actor actor, String color) {
        colorChecks.checkColor(color, actualColors);
    }

    @Then("{actor} should see that the nested color is {}")
    public void sheShouldSeeTheNestedColorIs(Actor actor, String color) {
        colorChecks.checkColor(color, actualColors);
    }

    public static class ColorChecks {
        @Step
        public void checkColor(String color, List<String> actualColors) {
            Assertions.assertThat(actualColors).contains(color);
        }
    }

    @Steps
    ColorChecks colorChecks;

}
