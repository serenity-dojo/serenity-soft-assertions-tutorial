Feature: Sample asserts

  Scenario: Screenplay asserts with AssertJ
  With AssertJ soft asserts, the assertion message appears at the end
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she ensures the following colors are present:
      | red    |
      | purple |
      | blue   |
      | yellow |
      | green  |

  Scenario: Soft asserts with AssertJ
  With AssertJ soft asserts, the assertion message appears at the end
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then the following colors are present:
      | red    |
      | purple |
      | blue   |
      | yellow |
      | green  |

  Scenario: Serenity soft asserts
    With Serenity soft asserts using the Ensure library, the assertion failures appear in each step
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see the following colors:
      | red    |
      | purple |
      | blue   |
      | yellow |
      | green  |
#
#
  Scenario: Nested Serenity soft asserts
  The Ensure checks can also be nested inside other performables
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see the following colors in nested checks:
      | red    |
      | purple |
      | blue   |
      | yellow |
      | green  |
    And she should proceed to the next step if the color is found

  @multistep
  Scenario: Serenity soft asserts across several steps

  You can make soft asserts work across multiple steps using @Before and @After hooks

    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see all the following colors:
      | red    |
      | purple |
      | blue   |
    And she should see all the following colors:
      | pink   |
      | yellow |
      | green  |

  @assertj
  Scenario: AssertJ soft asserts across several steps
  You can make soft asserts work across multiple steps using @Before and @After hooks with AssertJ
  In this case, the assertion errors will be reported the end of the test

    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see each of the following colors:
      | red    |
      | purple |
      | blue   |
    And she should see each of the following colors:
      | pink   |
      | yellow |
      | green  |

  Scenario Outline: Serenity asserts in a data-driven test
  Soft asserts are not required for data-driven tests
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see all the following colors:
      | <color> |
    And she should proceed to the next step if the color is found
    Examples:
      | color  |
      | red    |
      | purple |
      | blue   |
      | pink   |
      | yellow |
      | green  |

  Scenario Outline: Simple assertions in a data-driven test
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see the color <color>
    And she should proceed to the next step if the color is found
    Examples:
      | color  |
      | red    |
      | purple |
      | blue   |
      | pink   |
      | yellow |
      | green  |

  Scenario Outline: Simple nested AssertJ assertions in a data-driven test
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see that the nested color is <color>
    And she should proceed to the next step if the color is found
    Examples:
      | color  |
      | red    |
      | purple |
      | blue   |
      | pink   |
      | yellow |
      | green  |

  Scenario Outline: Nested screenplay assertions in a data-driven test
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see the nested color <color>
    And she should proceed to the next step if the color is found
    Examples:
      | color  |
      | red    |
      | purple |
      | blue   |
      | pink   |
      | yellow |
      | green  |

  @current
  Scenario Outline: Nested step assertions in a data-driven test
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see the step color <color>
    And she should proceed to the next step if the color is found
    Examples:
      | color  |
      | red    |
      | purple |
      | blue   |
      | pink   |
      | yellow |
      | green  |

  Scenario Outline: Passing Serenity asserts in a data-driven test
    Given Sophie has the following colours:
      | red   |
      | green |
      | blue  |
    When she checks the colors
    Then she should see all the following colors:
      | <color> |
    And she should proceed to the next step if the color is found
    Examples:
      | color |
      | red   |
      | blue  |
      | green |
