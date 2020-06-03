# 10장. 람다를 이용한 도메인 전용 언어(DSL)

### 10.4.2 큐컴버 (https://cucumber.io/)

Cucumber 는 GherKin 이라는 테스트 전용 도메인 언어를 사용한다.
참고 : https://cucumber.io/docs/gherkin/reference/

Gherkin 은 기획자가 시스템을 설계할 때 사용 시나리오를 작성하는 방법을 제공한다.

```Gherkin

Feature: 창고에 물건을 입고 시키면 입고 상태에 따라 재고 수량이 반영된다.

  Scenario: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다.

  Given '수원' 물류창고에 제품 'A4509'가 10개 있다.
  When 제품 'A4509' 10개를 입고시켰다.
  And 이 제품들은 모두 정상 이었다.
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 20개가 된다.

  Scenario: (불량) 창고에 물건이 입고되었는데, 불량이 있으면 입고수에서 불량수를 차감한 만큼 재고에 반영된다.

  Given '수원' 물류창고에 제품 'A4509'가 10개 있다.
  When 제품 'A4509' 10개를 입고시켰다.
  And 이 제품들 중 3개가 불량이었다.
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 17개 불량품 3개가 된다.


```

```Gherkin (ko)
language: ko

기능: 창고에 물건을 입고 시키면 입고 상태에 따라 재고 수량이 반영된다.

  시나리오: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다.

    조건 '수원' 물류창고에 제품 'A4509'가 10개 있다.
    만약 제품 'A4509' 10개를 입고시켰다.
    그리고 이 제품들은 모두 정상 이었다.
    그러면 '수원' 물류창고의 제품 'A4509'의 재고수량은 20개가 된다.

  시나리오: (불량) 창고에 물건이 입고되었는데, 불량이 있으면 입고수에서 불량수를 차감한 만큼 재고에 반영된다.

    조건 '수원' 물류창고에 제품 'A4509'가 10개 있다.
    만약 제품 'A4509' 10개를 입고시켰다.
    그리고 이 제품들 중 3개가 불량이었다.
    그러면 '수원' 물류창고의 제품 'A4509'의 재고수량은 17개 불량품 3개가 된다.

```

### 키워드 랭귀지 매핑

영어의 키워드에 대한 한글 키워드 매핑은 다음과 같다.

| 키워드          | 한국어           |
| --------------- | ---------------- |
| Feature         | 기능             |
| background      | 배경             |
| scenario        | 시나리오         |
| scenarioOutline | 시나리오 개요    |
| examples        | 예               |
| given           | \* / 조건 / 먼저 |
| when            | \* / 만일 / 만약 |
| then            | \* / 그러면      |
| and             | \* / 그리고      |
| but             | \* / 하지만 / 단 |

### Senario Outlet 활용하기

```
Feature: 창고에 물건을 입고 시키면 입고 상태에 따라 재고 수량이 반영된다.

  Scenario Outline: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다. (#<index>)

    Given '<창고이름>' 물류창고에 제품 '<제품코드>'이 <초기수량>개 있다.
    When 제품 '<제품코드>' <입고수량>개를 입고시켰다.
    And 이 제품들은 모두 정상 이었다.
    Then '<창고이름>' 물류창고의 제품 '<제품코드>'의 재고수량은 <유효수량>개가 된다.

    Examples:

    | index | 창고이름 | 제품코드 | 초기수량 | 입고수량 | 불량개수 | 유효수량 | 불량수량 |
    | 1     | 수원    | A4509   | 10    | 10     | 0      | 20    | 0     |
    | 2     | 수원    | A4509   | 11    | 10     | 0      | 21    | 0     |
    | 3     | 수원    | A4509   | 10    | 20     | 0      | 30    | 0     |
    | 4     | 수원    | A4509   | 10    | 35     | 0      | 45    | 0     |
```

실행 모습

```
Scenario Outline: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다. (#1) # features/입고.feature:14
  Given '수원' 물류창고에 제품 'A4509'이 10개 있다.                           # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:23)
  When 제품 'A4509' 10개를 입고시켰다.                                    # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:30)
  And 이 제품들은 모두 정상 이었다.                                          # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:33)
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 20개가 된다.                     # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:41)

Scenario Outline: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다. (#2) # features/입고.feature:15
  Given '수원' 물류창고에 제품 'A4509'이 11개 있다.                           # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:23)
  When 제품 'A4509' 10개를 입고시켰다.                                    # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:30)
  And 이 제품들은 모두 정상 이었다.                                          # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:33)
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 21개가 된다.                     # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:41)

Scenario Outline: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다. (#3) # features/입고.feature:16
  Given '수원' 물류창고에 제품 'A4509'이 10개 있다.                           # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:23)
  When 제품 'A4509' 20개를 입고시켰다.                                    # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:30)
  And 이 제품들은 모두 정상 이었다.                                          # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:33)
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 30개가 된다.                     # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:41)

Scenario Outline: (정상) 창고에 물건을 입고 시키면 재고에 입고된 물건의 수량이 반영된다. (#4) # features/입고.feature:17
  Given '수원' 물류창고에 제품 'A4509'이 10개 있다.                           # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:23)
  When 제품 'A4509' 35개를 입고시켰다.                                    # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:30)
  And 이 제품들은 모두 정상 이었다.                                          # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:33)
  Then '수원' 물류창고의 제품 'A4509'의 재고수량은 45개가 된다.                     # com.sp.warehouse.InWarehouseStepDefinition.<init>(InWarehouseStepDefinition.java:41)
```
