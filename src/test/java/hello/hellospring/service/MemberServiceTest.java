package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * class MemberService 에서 [ctrl+shift+T]로 create test로 생성
 */
class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /**
     * @BeforeEach : 각 테스트 실행 전에 호출된다.
     * 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다.
     */
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

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

        //When 1 : try-catch 사용
        /*
        memberService.join(member1);
        try{
            memberService.join(member2);
        }catch(IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //When
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,() -> memberService.join(member2)); //예외가 발생
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //에러메세지 확인
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}