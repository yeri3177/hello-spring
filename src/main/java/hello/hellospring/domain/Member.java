package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * DB의 컬럼명이 "username"이라고 하면 @Column을 통해 매핑
     * 현재는 DB컬럼명이 똑같이 name이므로 생략 가능
     */
    //@Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
