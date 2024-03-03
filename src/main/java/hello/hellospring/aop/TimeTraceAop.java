package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
 * 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
 * 핵심 관심 사항을 깔끔하게 유지할 수 있다.
 * 변경이 필요하면 이 로직만 변경하면 된다.
 * 원하는 적용 대상을 선택할 수 있다.
 *
 * @Component 어노테이션 대신 SpringConfig에서 빈으로 등록해서 사용
 */
@Component
@Aspect
public class TimeTraceAop {
    /**
     * @Around("${pattern}"}
     * 지정된 패턴에 해당하는 메소드의 실행되기 전, 실행된 후 모두에서 동작한다.
     * 이 어노테이션이 붙은 메소드의 반환 값은 Object여야 한다.
     *
     * pointcut 표현식
     * "*" : 모든 것 / ".." : 0개 이상
     * execution([접근제어자] 반환타입 패키지.패키지.패키지.패키지.클래스.메소드(인자))
     */
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs + "ms");
        }
    }
}
