package com.study.books.cleancode.objectanddatastructure;

import com.study.books.cleancode.objectanddatastructure.obj.Ctxt;

public class ObjectAndDataStructure {

    /* 변수를 비공개(private)로 정의하는 이유가 있다.
        남들이 변수에 의존하지 않게 만들고 싶어서다.
        그렇다면 어째서 수 많은 프로그래머가 조회(get) 함수와 설정(set) 함수를 당연하게 공개(public) 해 비공개 변수를 외부에 노출할까?
        객체간에는 메세지만 주고받는다! */

    /* 자료구조 : 여러 변수들이 하나의 의미를 구성한다면 이들을 묶어 하나의 자료구조로 구성! */

    /* 자료 추상화
        한 클래스는 구현을 외부로 노출하고 다른 클래스는 구현을 완전히 숨긴다. */
    public class Point {
        /* 구현을 노출한다.
            변수를 private 으로 선언하더라도 각 값마다 조회 함수와 설정 함수를 제공한다면 구현을 외부로 노출하는 셈이다. */
        public double x;
        public double y;
    }

    public interface Point2 {
        /* 직교좌표계를 사용하는지 극 좌표계를 사용하는지 알 길이 없다.
            둘다 아닐지도 모른다!
            그럼에도 불구하고 인터페이스는 자료구조를 명백하게 표현한다.
            클래스 메서드가 접근 정책을 강제한다.
            좌표를 읽을 때는 각 값을 개별적으로 읽어야 한다.
            하지만 좌표를 설정할 떄는 두 값을 한꺼번에 설정해야 한다. */
        double getX();
        double getY();
        void setCartesian(double x, double y);
        double getR();
        double getTheta();
        void setPolar(double r, double theta);
    }
    /* 구현을 감추려면 추상화가 필요하다!
        그저 조회 함수와 설정 함수로 변수를 다룬다고 클래스가 되지는 않는다.
        그보다는 추상 인터페이스를 제공해 사용자가 구현을 모른 채 자료의 핵심을 조작할 수 있어야 진정한 의미의 클래스다. */

    public interface Vehicle {
        double getFuelTankCapacityInGallons();
        double getGallonsOfGasoline();
    }

    public interface Vehicle2 {
        double getPercentFuelRemaining();
    }
    /* 점차 밑으로 작성된 인터페이스가 더 좋은 방식.
        자료를 세세하게 공개하기보다는 추상적인 개념으로 표현하는 편이 좋다.
        개발자는 객체가 포함하는 자료를 표현할 가장 좋은 방법을 심각하게 고민해야 한다.
        아무 생각 없이 조회/설정 함수를 추가하는 방법이 가장 나쁘다. */

    /* 자료/객체 비대칭
        객체는 추상화 뒤로 자료를 숨긴 채 자료를 다루는 함수만 공개한다.
        자료구조는 자료를 그대로 공개하며 별다른 함수는 제공하지 않는다. */
    public class Square {
        public Point topLeft;
        public double side;
    }

    public class Rectangle {
        public Point topLeft;
        public double height;
        public double width;
    }

    public class Circle {
        public Point center;
        public double radius;
    }

    public class Geometry {
        public final double PI = 3.141592653589793;

        public double area(Object shape) {
            if (shape instanceof Square) {
                Square s = (Square) shape;
                return s.side * s.side;
            } else if (shape instanceof Rectangle) {
                Rectangle r = (Rectangle) shape;
                return r.height * r.width;
            } else if (shape instanceof Circle) {
                Circle c = (Circle) shape;
                return c.radius * c.radius;
            } else {
                return 0;
            }
        }
    }
    /* 각 도형 클래스는 간단한 자료 구조다.
        즉, 아무 메서드도 제공하지 않는다.
        도형이 작동하는 방식은 Geometry 클래스에서 구현한다.

        절차적인 구현방식!
        만약 Geometry 클래스에 둘레 길이를 구하는 함수를 추가하고 싶다면?
        도형 클래스는 아무 영향도 받지 않는다!
        도형 클래스에 의존하는 다른 클래스들도 마찬가지다.
        반대로 새 도형을 추가하고 싶다면? Geometry 클래스에 속한 함수를 모두 고쳐야 한다. */

    public interface Shape {
        double area();
    }

    public class Square2 implements Shape {
        private Point topLeft;
        private double side;

        public double area() {
            return side * side;
        }
    }

    public class Rectangle2 implements Shape {
        private Point topLeft;
        private double height;
        public double width;

        public double area() {
            return height * width;
        }
    }

    public class Circle2 implements Shape {
        private Point center;
        private double radius;
        public final double PI = 3.141592653589793;

        public double area() {
            return PI * radius * radius;
        }
    }
    /* (자료 구조를 사용히는) 절차적인 코드는 기존 자료 구조를 변경하지 않으면서 새 함수를 추가하기 쉼다.
        반면, 객체 지향 코드는 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.
        절차적인 코드는 새로운 자료 구조를 추가하기 어렵다. 그러려면 모든 함수를 고쳐야 한다.
        객체 지향 코드는 새로운 함수를 추가하기 어렵다. 그러려면 모든 클래스를 고쳐야 한다. */

    /* 객체 지향 코드에서 어려운 변경은 절차적인 코드에서 쉬우며, 절차적인 코드에서 어려운 변경은 객체 지향 코드에서 쉽다!
        새로운 함수가 아니라 새로운 자료 타입이 필요한 경우가 생긴다.
        이때는 클래스와 객체 지향 기법이 가장 적합하다.
        반면, 새로운 자료 타입이 아니라 새로운 함수가 필요한 경우도 생긴다.
        이때는 절차적인 코드와 자료 구조가 좀 더 적합하다. */

    /* 디미터 법칙
        디미터 법칙은 잘 알려진 휴리스틱으로, 모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙이다.
        객체는 조회 함수로 내부 구조를 공개하면 안 된다는 의미다.
        그러면 내부 구조를 (숨기지 않고) 노출하는 셈이니까
        "클래스 C의 메서드 f는 다음과 같은 객체의 메서드만 호출해야 한다"
        - 클래스 C
        - f가 생성한 객체
        - f 인수로 넘어온 객체
        - C 인스턴스 변수에 저장된 객체 */

    Ctxt ctxt = new Ctxt();
    final String outputDir = ctxt.getOptions().getScratchDir().getAbsolutePath();
    /* 기차 충돌
        흔히 위와같은 코드를 기차 충돌(train wreck) 이라 부른다.
        일반적으로는 조잡하다 여겨지는 방식이므로 피하는 편이 좋다. */

    /* 자료 구조는 무조건 함수 없이 공개 변수만 포함하고 객체는 비공개 변수와 공개 함수를 포함한다면, 문제는 훨씬 간단하리라 */

    /* 잡종 구조
        때때로 절반은 객체, 절반은 자료 구조인 잡종 구조가 나온다.
        잡종 구조는 중요한 기능을 수행하는 함수도 있고, 공개 변수나 공개 조회/설정 함수도 있다.
        잡종 구조는 되도록 피하는 편이 좋다. */

    /* 자료 전달 객체
        자료 구조체의 전형적인 형태는 공개 변수만 있고 함수가 없는 클래스다.
        이런 자료 구조체를 때로는 자료 전달 객체(Data Transfer Object : DTO)라 한다.
        데이터베이스에 저장된 가공되지 않은 정보를 애플리케이션 코드에서 사용할 객체로 변환하는 일련의 단계에서 가장 처음으로 사용하는 구조체로
        일반적인 형태는 '빈(bean)'구조이다.
        빈은 비공개 변수를 조회/설정 함수로 조작한다. */

    /* 활성 레코드
        활성 레코드는 DTO 의 특수한 형태이다.
        공개 변수가 있거나 비공개 변수에 조회/설정 함수가 있는 자료 구조지만,
        대개 save 나 find 와 같은 탐색 함수도 제공한다.
        이런 자료 구조를 객체로 취급하는 개발자가 흔하다. 하지만 이는 바람직하지 않다.
        활성 레코드는 자료 구조로 취급한다! */

}
