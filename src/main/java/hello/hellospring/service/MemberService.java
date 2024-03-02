package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * SpringConfig에서 MemberService을 빈으로 등록함
 */
//@Service

/**
 * 회원가입할때 Transactional 필요 !
 * org.springframework.transaction.annotation.Transactional 를 사용하자.
 * 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커밋한다.
 * 만약 런타임 예외가 발생하면 롤백한다.
 * JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다
 */
@Transactional
public class MemberService {
    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    /**
     * @Autowired 를 통한 DI는 helloController, memberService 등과 같이 스프링이 관리하는 객체에서만 동작한다.
     * 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.
     */
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
//    public Long join(Member member) {
//        validateDuplicateMember(member); //중복 회원 검증
//        memberRepository.save(member);
//        return member.getId();
//    }

    /**
     * AOP가 필요한 상황 : 시간을 측정하는 로직 추가 (공통 관심 사항)
     */
    public Long join(Member member) {
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
    }

    /**
     * 같은 이름 있는 중복회원 검증
     * Boolean isPresent() : Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
     */
    private void validateDuplicateMember(Member member) {
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     */
//    public List<Member> findMembers() {
//        return memberRepository.findAll();
//    }

    /**
     * AOP가 필요한 상황 : 시간을 측정하는 로직 추가 (공통 관심 사항)
     */
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
