package com.study.books.cleancode.errorhandling;

import com.study.books.cleancode.errorhandling.obj.MealExpenses;
import com.study.books.cleancode.errorhandling.obj.RecordedGrip;
import com.study.books.cleancode.function.obj.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* 오류 처리는 프로그램에 반드시 필요한 요소 중 하나일 뿐이다.*/
public class ErrorHandling {

    /* 오류 코드보다는 예외를 사용하라
        오류 코드를 정의하는 방식은 호출자의 코드가 복잡해진다.
        함수를 호출한 즉시 오류를 확인해야 하기 때문이다. 하지만 불행이도 이 단계는 잊어버리기 쉽다.
        그래서 오류가 발생하면 예외를 던지는편이 낮다! */

    /* Try-Catch-Finally 문부터 작성하라
        예외에서 프로그램 안에다 범위를 정의한다는 사실은 매우 흥미롭다.
        try 블록에서 무슨 일이 생기든지 catch 블록은 프로그램 상태를 일관성 있게 유지해야 한다.
        그러므로 에외가 발생할 코드를 짤 때는 try-catch-finally 문으로 시작하는 편이 낫다. */
    public List<RecordedGrip> retrieveSection(String sectionName) {
        try {
            FileInputStream stream = new FileInputStream(sectionName);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return new ArrayList<RecordedGrip>();
    }
    /* 먼저 강제로 예외를 일으키는 테스트 케이스를 작성한 후 테스트를 통과하게 코드를 작성하는 방법을 권장한다.
        그러면 자연스럽게 try 블록의 트랜잭션 범위부터 구현하게 되므로 범위 내에서 트랜잭션 본질을 유지하기 쉬워진다. */

    /* 미확인(unchecked) 예외를 사용하라
        확인된(checked) 예외는 OCP(Open Closed Principle)을 위반한다.
        메서드에서 확인된 예외를 던졌는데 catch 블록이 세 단계 위에 있다면 그 사이 메서드 모두가 선언부에 해당 예외를 정의해야 한다.
        즉, 하위 단계에서 코드를 변경하면 상위 단계의 메서드 선언부를 전부 고쳐야 한다는 말이다.
        최하위 함수를 수정하면 연쇄적인 수정이 일어난다!
        - 참고 :Checked Exception 과 Unchecked Exception 의 가장 명확한 구분 기준은 ‘꼭 처리를 해야 하느냐’이다.
            Checked Exception 이 발생할 가능성이 있는 메소드라면 반드시 로직을 try/catch 로 감싸거나 throw 로 던져서 처리해야 한다.
            반면에 Unchecked Exception 은 명시적인 예외처리를 하지 않아도 된다. */

    /* 예외에 의미를 제공하라
        예외를 던질 때는 전후 상황을 충분히 덧붙인다.
        오류 메세지에 정보를 담아 예외와 함께 던진다.
        실패한 연산 이름과 실패 유형도 언급한다. */

    /* 호출자를 고려해 예외 클래스를 정의하라
        애플리케이션에서 오류를 정의할 때 프로그래머에게 가장 중요한 관심사는 오류를 잡아내는 방법이 되어야 한다.
        우리가 오류를 처리하는 방식은 비교적 일정하다.
            1. 오류를 기록한다.
            2. 프로그램을 계속 수행해도 좋은지 확인한다. */
    public void portCheck() {
        LocalPort port = new LocalPort(12);
        try {
            port.open();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {

        }
    }

    /* LocalPort 클래스처럼 wrapper 클래스는 굉장히 유용하다.
        1. 외부 API 를 감싸면 외부 라이브러리와 프로그램 사이에서 의존성이 크게 줄어든다.
        2. 그래서 다른 라이브러리로 갈아타도 비용이 적다.
        3. 프로그램을 테스트하기도 쉬워진다.
        4. 특정 업체가 API 를 설계한 방식에 발목 잡히지 않는다. */
    public class LocalPort {
        public LocalPort(int port) {}

        public void open() {
            try {
                // wrapper 된 클래스의 실제 행위
            } catch (Exception e) {
                // open() 을 호출하는 부에서 하나로 축약해서 정의한 익셉션으로 throw
                throw new RuntimeException();
            } // 계속 하위 Exception 을 정의하면 축약 익셉션으로 throw
        }
    }

    /* 정상 흐름을 정의하라 */
    public void getTotal() {
        int m_total = 0;

        try {
            MealExpenses expenses = new MealExpenses();
            m_total += expenses.getTotal();
        } catch (RuntimeException e) {
            m_total += getMealPerDiem();
        }
        // 예외 상황을 만들어서 처리하는것 보다

        MealExpenses expenses = new MealExpenses();
        m_total += expenses.getTotal();
        // getTotal() 에서 구분해야하는 비지니스 로직을 구분해서 구현하여 정상 흐름으로 정의하자
    }
    private int getMealPerDiem() {
        return 0;
    }
    /* 이를 특수 사례 패턴이라 부른다.
        클래스를 만들거나 객체를 조작해 특수 사례를 처리하는 방식이다.
        그러면 클라이언트의 코드가 예외적인 상황을 처리할 필요가 없어진다. */

    /* null을 반환하지 마라
        null을 반환하는 습관은 좋지 못한 습관이다.
        null을 반환하는 코드는 일거리를 늘릴 뿐만 아니라 호출자에게 문제를 떠넘긴다.
        누구 하나라도 null 확인을 빼먹는다면 애플리케이션이 통제 불능에 빠질지도 모른다.
        메서드에서 null을 반환하고픈 유혹이 든다면 그 대신 예외를 던지거나 특수 사례 객체를 반환한다. */
    public void getEmployee() {
        int totalPay = 0;
        List<Employee> employees = getEmployees();
        if (employees != null) {    // getEmployees() 가 null을 반환할 가능성이 있다면 null 확인을 해야할 것이다.
            for (Employee e : employees) {
                totalPay += e.getPay();
            }
        }

        // 하지만 null을 반환할 가능성이 없다면 확인을 하지 않아도 된다!
        for (Employee e : employees) {
            totalPay += e.getPay();
        }

    }

    private List<Employee> getEmployees() {
        if (isEmptyEmployee()) {   // null을 반환하게 해야할 케이스에 다른 처리를 하자!
            return Collections.emptyList();
        }
        return new ArrayList<>();
    }

    private boolean isEmptyEmployee() {
        return true;
    }

    /* null을 전달하지 마라
        메서드에서 null을 반환하는 방식도 나쁘지만 메서드로 null을 전달하는 방식은 더 나쁘다.
        정상적인 인수로 null을 기대하는 API 가 아니라면 메서드로 null을 전달하는 코드는 최대한 피한다.
        대다수 프로그래밍 언어는 호출자가 실수로 넘기는 null을 적절히 처리하는 방법이 없다.
        그렇다면 애초에 null을 넘기지 못하도록 금지하는 정책이 합리적이다. */

}
