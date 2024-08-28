package com.springboot.member.entity;

import com.springboot.todos.entity.Todos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity  // 이 클래스가 JPA 엔티티임을 나타냅니다.
@Getter  // Lombok을 사용하여 자동으로 getter 메서드를 생성합니다.
@Setter  // Lombok을 사용하여 자동으로 setter 메서드를 생성합니다.
@NoArgsConstructor  // Lombok을 사용하여 기본 생성자를 자동으로 생성합니다.
public class Member {

    @Id  // 이 필드가 엔티티의 기본 키임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키를 자동 생성하고, 데이터베이스의 기본 키 생성 전략을 따릅니다.
    private long memberId;

    @Column(nullable = false, length = 8)  // 이 필드가 데이터베이스의 열(column)로 매핑되며, 필수 값이고 최대 길이가 8자입니다.
    private String name;

    @Column(nullable = false, updatable = false, unique = true)  // 필수 값이며, 한 번 설정되면 수정할 수 없고, 유일한 값이어야 합니다.
    private String email;

    @Column(nullable = false, length = 100)  // 필수 값이며, 최대 길이가 100자입니다.
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)  // 기본 컬렉션 타입의 필드를 엔티티에 포함시키며, EAGER 로딩 전략을 사용합니다.
    private List<String> roles = new ArrayList<>();  // 사용자의 권한(roles)을 저장하는 리스트입니다.

    @OneToMany(mappedBy = "member")  // 일대다 관계를 나타내며, 'member' 필드를 통해 매핑됩니다.
    private List<Todos> todos = new ArrayList<>();  // 회원이 가진 할일(Todos) 목록을 저장하는 리스트입니다.
}