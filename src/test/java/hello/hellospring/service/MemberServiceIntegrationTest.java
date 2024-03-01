package hello.hellospring.service;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 스프링 통합 테스트 : 스프링 컨테이너와 DB까지 연결한 통합 테스트를 진행해보자
 *
 * @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
 * @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
 * 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
 *
 * MemberServiceTest : 단위테스트
 * MemberServiceIntegrationTest : 통합테스트
 *
 * 단위테스트 : 순수한 Java 코드로 최소한의 단위로 하는 테스트
 * 통합테스트 : 스프링컨테이너와 DB연동까지 하는 테스트
 *
 * 순수한 단위테스트가 훨씬 좋은 테스트일 확률이 높다.
 * 단위별로 쪼개서 테스트를 잘 할 수 있도록 하고 스프링 컨테이터 없이 테스트 할 수 있도록 훈련해야함
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //Given
        Member member = new Member();
        member.setName("spring");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,() -> memberService.join(member2)); //예외가 발생
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //에러메세지 확인
    }
}