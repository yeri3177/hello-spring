package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <<자바 코드로 직접 스프링 빈 등록>>>
 * 회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired 애노테이션을 제거하고 SpringConfig로 설정
 * 본 강의에서 메모리 리포지토리를 다른 리포지토리로 변경할 예정이므로, 컴포넌트 스캔 방식 대신에 자바 코드로 스프링 빈을 설정함
 *
 * XML로 설정하는 방식도 있지만 최근에는 잘 사용하지 않으므로 생략한다.
 *
 * DI에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 있다.
 * 의존관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.
 *
 * 실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
 * 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.
 */
@Configuration
public class SpringConfig {
    // 스프링 데이터 JPA
    private final MemberRepository memberRepository;
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // JPA
//    //private final DataSource dataSource;
//    private final EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        //this.dataSource = dataSource;
//        this.em = em;
//    }

    /**
     * DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체다.
     * 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 DataSource를 생성하고 스프링 빈으로 만들어둔다.
     * 그래서 DI를 받을 수 있다.
     */
//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Bean
    public MemberService memberService() {
        //return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

    /**
     * MemberRepository 인터페이스를 통해 인터페이스의 구현체들을 만들어 변경 가능
     *
     * 개방-폐쇄 원칙(OCP, Open-Closed Principle) : 확장에는 열려있고 수정,변경에는 닫혀있다.
     *
     * 스프링의 DI (Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고,
     * 설정만으로 구현 클래스를 변경할 수 있다.
     */
/*
    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
*/

    /**
     * AOP 클래스 빈으로 등록
     */
//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
}
