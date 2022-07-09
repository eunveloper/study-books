package com.study.books.cleancode.function;

import com.study.books.cleancode.function.obj.*;

public class Function {

    /* 의도를 분명히 표현하는 함수를 어떻게 구현할 수 있을까?
        함수에 어떤 속성을 부여해야 처음 일는 사람이 프로그램 내부를 직관적으로 파악할 수 있을까? */

    /* 함수를 어떻게 짜죠?
        처음에는 길고 복잡하다.
        들여쓰기 단계도 많고 중복된 루프도 많다. 인수목록도 아주 길다.
        이름은 즉흥적이고 코드는 중복된다.
        하지만 코드를 다듬고, 함수를 만들고, 이름을 바꾸고, 중복을 제거한다!
        메서드를 줄이고 순서를 바꾼다! */

    public class SetupTeardownIncluder {
        private PageData pageData;
        private boolean isSuite;
        private WikiPage testPage;
        private StringBuffer newPageContent;
        private PageCrawler pageCrawler;

        /* 함수 당 추장화 수준은 하나로!
            함수가 확실히 '한 가지' 작업만 하려면 함수 내 모든 문장의 추상화 수준이 동일해야 한다.
            - getHtml() : 추상화 수준이 아주 높음
            - String pagePathName = PathParser.render(pagePath) : 추상화 수준이 중간
            - .append("\n") : 추상화 수준이 아주 낮음 */

        /* 위에서 아래로 코드 읽기 : 내려가기 규칙
            코드는 위에서 아래로 이야기처럼 읽혀야 좋다.
            즉, 위에서 아래로 프로그램을 읽으면 함수 추상화 수준이 한 번에 한 단계씩 낮아진다.
            각 함수는 다음 함수를 소개한다. 또한 각 함수는 일정한 추상화 수준을 유지한다. */

        /* 부수 효과를 일으키지 마라!
            시간적인 결합은 혼란을 일으킨다.
            특히 부수 효과로 숨겨진 경우에는 더더욱 혼락이 커진다. */

        /* 구조적 프로그래밍
            함수는 return 문이 하나여야 한다.
            루프 안에서 break 나 continue 를 사용해서는 안 되며 goto 는 절대로 안 된다. */

        public String render(PageData pageData) {
            return render(pageData, false);
        }

        private String render(PageData pageData, boolean isSuite) {
            return new SetupTeardownIncluder(pageData).render(isSuite);
        }

        private SetupTeardownIncluder(PageData pageData) {
            this.pageData = pageData;
            testPage = pageData.getWikiPage();
            pageCrawler = testPage.getPageCrawler();
            newPageContent = new StringBuffer();
        }

        private String render(boolean isSuite) {
            /* 작게 만들어라!
                각 함수를 명백하고 단 하나의 이야기만 포함하게 구성하라. */
            this.isSuite = isSuite;
            if (isTestPage()) {
                /* if 문 else 문 while 문 등에 들어가는 블록은 한 줄이어야 한다는 의미로
                    대게 안에서 함수를 호출한다.
                    호출하는 함수 이름을 적절히 짓는다면 코드를 이해하기도 쉬워진다.
                    함수에서 들여쓰기 수준은 1단이나 2단을 넘어서면 안 된다. */
                includeSetupAndTeardownPages();
            }
            return pageData.getHtml();
        }

        private boolean isTestPage() {
            /* 한가지만 해라!
                함수는 한 가지를 해야 한다. 그 한 가지를 잘 해야 한다. 그 한 가지만을 해야한다.
                지정된 함수 이름 아래에서 추상화 수준이 하나인 단계만 수행한다면 그 함수는 한 가지 작업만 한다.
                의미 있는 이름으로 다른 함수를 추출할 수 있다면 그 함수는 여러 작업을 하는 셈! */
            return pageData.hasAttribute("Test");
        }

        private void includeSetupAndTeardownPages() {
            /* 반복하지 마라!
                같은 내용을 반복하면 코드 길이가 늘어날 뿐 아니라 알고리즘이 변하면 네 곳이나 손봐야 한다.
                아래와 같이 include 방법으로 중복을 없앤다. */
            includeSetupPages();
            includePageCount();
            includeTeardownPages();
            updatePageContent();
        }

        private void includeSetupPages() {
            /* 서술적인 이름을 사용하라!
                함수가 하는 일을 좀 더 잘 표현하는 이름이 훨씬 좋은 이름이다.
                함수 이름을 정할 때는 여러 단어가 쉽게 읽히는 명명법을 사용하고 여러 단어를 사용해 함수 기능을 잘 표현하는 이름을 선택한다.
                이름을 붙일 때는 일관성이 있어야 한다.
                모듈 내에서 함수 이름은 같은 문구, 명사, 동사를 사용한다. */
            if (isSuite) {
                includeSuiteSetupPage();
            }
            includeSetupPage();
        }

        private void includeSuiteSetupPage() {
            include(SuiteResponder.SUITE_SETUP_NAME, "-setup");
        }

        private void includeSetupPage() {
            include("SetUp", "=setup");
        }

        private void includePageCount() {
            include("SetUp", "-setup");
        }

        private void includeTeardownPages() {
            includeTeardownPage();
            /* 명령과 조회를 분리하라!
                함수는 뭔가를 수행하거나 뭔가에 답하거나 둘 중에 하나만 해야 한다.
                조회하여 분기문에 넣고,
                분기 결과에 따라서 무언가의 행위를 한다. */

            /* 오류 코드보다 예외를 사용하라!
                명령 함수에서 오류 코드를 반환하는 방식은 명령, 조회 분리 규칙을 미묘하게 위반한다.
                오류 코드를 반환하면 호출자는 오류 코드를 곧바로 처리해야 한다는 문제에 부딪힌다. */

            /* 오류 처리도 한 가지 작업이다. */
            if (isSuite) {
                includeSuiteTeardownPage();
            }
        }

        private void includeTeardownPage() {
            include("TearDown", "-teardown");
        }

        private void includeSuiteTeardownPage() {
            include(SuiteResponder.SUITE_TEARDOWN_NAME, "-teardown");
        }

        private void updatePageContent() {
            pageData.setContent(newPageContent.toString());
        }

        private void include(String pageNames, String arg) {
            /* 함수 인수
                함수에서 이상적인 인수 개수는 0개(무항)다. 다음은 1개(단항)고, 다음은 2개(이항)디.
                인스턴스 변수로 선언하는 대신 함수 인수로 넘기는 방법은 코드를 읽는 사람이 의미를 해석해야 한다!
                최선은 입력 인수가 없는 경우이며, 차선은 입력 인수가 1개뿐인 경우다. */

            /* 많이 쓰는 단항 형식
                함수에 인수 1개를 넘기는 이유로 가장 흔한 경우는 두 가지다.
                - 인수에 질문을 던지는 경우 : boolean fileExists("MyFile");
                - 인수를 뭔가로 변환해 결과를 반환하는 경우 : InputStream fileOpen("MyFile"); */

            /* 이벤트 함수는 입력 인수만 있고 출력 인수는 없다.
                프로그램은 함수 호출을 이벤트로 해석해 입력 인수로 시스템 상대를 바꾼다.
                이벤트라는 사실이 코드에 명확히 드러나야 한다.

              변환 함수에서 출력 인수를 사용하면 혼란을 일으킨다.
                입력 인수를 변환하는 함수라면 변환 결과를 반환값으로 돌려준다. */

            /* 플래그 인수
                함수로 부울 값을 넘기는 관례는 정말로 끔찍하다.
                함수가 한꺼번에 여러 가지를 처리한다고 대놓고 공표하는 셈이니까! */

            /* 이항 함수
                이항 함수가 적절한 경우도 있다.
                Point p = new Point(0, 0); 직교 좌표계 점은 일반적으로 인수 2개를 취한다.
                assertEquals(expected, actual) expected 인수에 actual 값을 집어 넣는 실수를 발생 */

            /* 인수 객체
                인수가 2-3개 필요하다면 일부를 독자적인 클래스 변수로 선언할 가능성을 짚어본다.
                Circle makeCircle(double x, double y, double radius);
                Circle makeCircle(Point center, double radius);
                객체를 생성해 인수를 줄이는 방법이 눈속임이라 여겨질지 모르지만 그렇지 않다! */

            /* 동사와 키워드
                함수의 의도나 인수의 순서와 의도를 제대로 표현하려면 좋은 함수 이름이 필수다.
                - 단항 함수는 함수와 인수가 동사/명사 쌍을 이뤄야 한다. writeField(name);
                - 함수 이름에 키워드를 추가하는 형식, 함수 이름에 인수 이름을 넣는다. */
            WikiPage inheritedPage = findInheritedPage(pageNames);
            if (inheritedPage != null) {
                String pagePathName = getPathNameForPage(inheritedPage);
                buildIncludeDirective(pagePathName, arg);
            }
        }

        private WikiPage findInheritedPage(String pageName) {
            return PageCrawlerImpl.getInheritedPage(pageName, testPage);
        }

        private String getPathNameForPage(WikiPage page) {
            /* 출력 인수
                일반적으로 인수를 함수 입력으로 해석한다.
                함수 선언부를 찾아보고 나서야 행위를 알게 되는 것은 바람직하지 않다.
                함수에서 상태를 변경해야 한다면 함수가 속한 객체 상태를 변경하는 방식을 택한다. */
            WikiPagePath pagePath = pageCrawler.getFullPath(page);
            return PathParser.render(pagePath);
        }

        private void buildIncludeDirective(String pagePathName, String arg) {
            newPageContent
                    .append("\n!include ")
                    .append(arg)
                    .append(" .")
                    .append(pagePathName)
                    .append("\n");
        }

    }

    public Money calculatePay(Employee e) {
        /* Switch 문
            switch 문은 작게 만들기 어렵다. 또한 한 가지 작업만 하게 만드는 것도 어렵다.
            아래 switch 문의 문제점
            - 새 직원 유형을 추가하면 더 길어진다.
            - 한 가지 작업만 수행하지 않는다.
            - SRP(Single Responsibility Principle)을 위반한다.
            - OCP(Open Closed Principle)를 위반한다. */
        switch (e.type) {
            case COMMISSIONED:
                return calculateCommissionedPay(e);
            case HOURLY:
                return calculateHourlyPay(e);
            case SALARIED:
                return calculateSalariedPay(e);
            default:
                throw new RuntimeException("");
        }
    }

    private Money calculateCommissionedPay(Employee e) {
        return new Money();
    }

    private Money calculateHourlyPay(Employee e) {
        return new Money();
    }

    private Money calculateSalariedPay(Employee e) {
        return new Money();
    }

    public interface EmployeeAfterFactory {
        public Employee makeEmployee(EmployeeRecord r);
    }

    public class EmployeeAfterFactoryImpl implements EmployeeAfterFactory {
        @Override
        public Employee makeEmployee(EmployeeRecord r) {
            /* 각 switch 문을 저차원 클래스에 숨기고 절대로 반복하지 않는 방법은 있다.
                바로 다형성을 이용하는 것이다!
                switch 문을 추상 팩토리에 꽁꽁 숨긴다.
                그러면 다형성으로 인해 실제 파생클래스의 함수가 실핸된다. */
            switch (r.type) {
                case COMMISSIONED:
                    return new CommissionedEmployee(r);
                case HOURLY:
                    return new HourlyEmployee(r);
                case SALARIED:
                    return new SalariedEmployee(r);
                default:
                    throw new RuntimeException("");
            }
        }
    }

}
