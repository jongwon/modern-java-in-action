
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrategyMain {

  interface ValidationStrategy {
    boolean execute(String s);
  }

  static private class IsAllLowerCase implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
      return s.matches("[a-z]+");
    }
  }

  static private class IsNumeric implements ValidationStrategy {
    @Override
    public boolean execute(String s) {
      return s.matches("\\d+");
    }
  }

  static private class Validator {
    private final ValidationStrategy strategy;

    public Validator(ValidationStrategy v) {
      strategy = v;
    }

    public boolean validate(String s) {
      return strategy.execute(s);
    }
  }

  @DisplayName("예전 방식으로 테스트 하기 ")
  @Test
  public void test_old_way() {
    Validator numericValidator = new Validator((new IsNumeric()));
    Validator lowerCaseValidator = new Validator(new IsAllLowerCase());

    assertFalse(numericValidator.validate("aaa"));
    assertTrue(lowerCaseValidator.validate("bbb"));
  }

  @DisplayName("람다 방식으로 테스트 하기")
  @Test
  public void test_lambda_method() {
    Validator numericValidator = new Validator(s -> s.matches("\\d+"));
    Validator lowerCaseValidator = new Validator(s -> s.matches("[a-z]+"));

    assertFalse(numericValidator.validate("aaa"));
    assertTrue(lowerCaseValidator.validate("bbb"));
  }

}
