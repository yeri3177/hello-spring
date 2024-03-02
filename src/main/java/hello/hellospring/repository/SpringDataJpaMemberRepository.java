package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 스프링 데이터 JPA가 SpringDataJpaMemberRepository 를 스프링 빈으로 자동 등록해준다.
 *
 * 인터페이스를 통한 기본적인 CRUD
 * findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공
 * 페이징 기능 자동 제공
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // JPQL : select m from Member m where m.name = ?
    Optional<Member> findByName(String name);
}
