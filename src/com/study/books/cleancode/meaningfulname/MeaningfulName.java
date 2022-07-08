package com.study.books.cleancode.meaningfulname;

import com.study.books.cleancode.meaningfulname.objs.Account;

import java.util.List;

import static sun.misc.Version.print;

public class MeaningfulName {

    /* 의도를 분명히 밝혀라
        - 변수(혹은 함수나 클래스)의 존재 이유는?
        - 수행 기능은?
        - 사용방법은?
        따로 주석이 필요하다면 의도를 분명히 드러내지 못했다는 말!
        코드의 함축성이 중요하다. */

    /* 그릇된 정보를 피하라
        hp, aix, sco 등은 변수 이름으로 적합하지 않다. */
    List<Account> accountList;  // 부적합한 방식. List 라는 단어는 프로그래머에게 특수한 의미이기 때문
    List<Account> accounts;     // 좀더 나은 표현

    /* 의미 있게 구분하라
        연속된 숫자를 붙히거나 불용어를 추가하는 것은 적절하지 못하다.
        이름이 달라야 한다면 의미도 달라져야 한다!
        ex) Product ProductInfo ProductData > 의미 구분이 가능한가? */

    /* 발음하기 쉬운 이름을 사용하라 */

    /* 검색하기 쉬운 이름을 사용하라
        검색의 관점에서 긴 이름이 짧은 이름보다 좋고, 검색하기 쉬운 이름이 상수보다 좋다!
        이름의 길이는 범위 크기게 비례해야 한다. */
    public void realDaysPerIdeaDay() {
        int realDaysPerIdeaDay = 4;
        for (int i = 0; i < realDaysPerIdeaDay; i++) {}
        for (int i = 0; i < 4; i++) {}  // 단지 4롤 사용하면 검색하기가 힘들다! '4'로 검색하면 뭐가 나올지 알고?
    }

    /* 인코딩을 피하라
        유형이나 범위 정보까지 인코딩에 넣으면 그만큼 이름을 해독하기 어려워진다. */

    /* 자신의 기억력을 자랑하지 마라
        변수 이름을 자신이 아는 이름으로 변환해야 한다면 그 변수 이름은 바람직하지 못하다.
        다른 개발자가 실제 개념으로 변환해야 하기 때문! */

    /* 클래스 이름
        - 명사나 명사구가 적합하며 동사는 사용하지 않는다. */

    /* 메서드 이름
        - 동사나 동사구가 적합하며 javabean 표준에 맞춰 get set is 로 사용한다.
        - 생성자를 overload 할 때는 인수를 설명하는 이름을 담은 메서드를 통해 정적 팩토리 메서드를 사용한다. */
    Account account1 = Account.GeneratorAccountName("name");    // Good!
    Account account2 = new Account("name");               // Bad!

    /* 기발한 이름은 피하라
        구어체나 속어를 이름으로 사용하지 말자! */

    /* 한 개념에 한 단어를 사용하라
        똑같은 메서드를 클래스마다 fetch, retrieve, get 으로 제각각 부르면 혼란스럽다.
        따라서 메서드 이름은 독자적이고 일관적이어야 한다. */

    /*  말장난을 하지 마라
        한 단어를 두 가지 목적으로 사용하지 마라.
        다른 개념에 같은 단어를 사용한다면 그것은 말장난에 불과! */

    /* 해법 영역에서 가져온 이름을 사용하라
        같은 개념을 다른 이름으로 이해하던 동료들이 매번 고객에게 의미를 물어야하기 때문이다.
        기술 개념에는 기술 이름이 가장 적합한 선택! */

    /* 문제 영역에서 가져온 이름을 사용하라
        적절한 '프로그래머 용어' 가 없다면 문제 영역에서 이름을 가져온다. */

    /* 의미 있는 맥락을 추가하라 */
    public void printGuessStatistics(char candidate, int count) {
        String number;
        String verb;
        String pluralModifier;
        if (count == 0) {
            number = "no";
            verb = "are";
            pluralModifier = "s";
        } else if (count == 1) {
            number = "1";
            verb = "is";
            pluralModifier = "";
        } else {
            number = Integer.toString(count);
            verb = "are";
            pluralModifier = "s";
        }
        String guessMessage = String.format(
                "There %s %s %s%s", verb, number, candidate, pluralModifier
        );
        System.out.println(guessMessage);
        // 읽는 사람이 맥락을 유푸해야만 한다.
        // 또한 함수가 좀 길고, 세 변수를 함수 전반에 사용한다.
    }

    public class GuessStatisticsMessage {
        private String number;
        private String verb;
        private String pluralModifier;

        public String make(char candidate, int count) {
            createPluralDependentMessageParts(count);
            return String.format(
                    "There %s %s %s%s",
                    verb, number, candidate, pluralModifier
            );
        }

        private void createPluralDependentMessageParts(int count) {
            if (count == 0) {
                thereAreNoLetters();
            } else if (count == 1) {
                thereIsOneLetter();
            } else {
                thereAreManyLetters(count);
            }
        }

        private void thereAreManyLetters(int count) {
            number = Integer.toString(count);
            verb = "are";
            pluralModifier = "s";
        }

        private void thereIsOneLetter() {
            number = "1";
            verb = "is";
            pluralModifier = "";
        }

        private void thereAreNoLetters() {
            number = "no";
            verb = "are";
            pluralModifier = "s";
        }
        // 클래스를 만들어 변수를 생성하였기 때문에 맥락이 분명해진다.
        // 함수를 쪼개기 쉬워져 알고리즘도 좀 더 명확해진다.
    }

    /* 불필요한 맥락을 없애라
        일반적으로는 짧은 이름이 긴 이름보다 좋다. 단, 의미가 분명한 경우에 한해서다.
        이름에 불필요한 맥락을 추가하지 않도록 주의한다. */



}
