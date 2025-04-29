package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  /*
  * 회원 가입
  * */
  public Long join(Member member) {
    // 같은 이름이 있는 중복 회원 X
    long start = System.currentTimeMillis();

    try {
      validateDuplicateMember(member);
      memberRepository.save(member);
      return member.getId();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMs = finish - start;
      System.out.println("join = " + timeMs + "ms");
    }

  }

  private void validateDuplicateMember(Member member) {
    memberRepository.findByName(member.getName())
        .ifPresent(m -> {
          throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
  }

  /*
  * 전체 회원 조회
  * */
  public List<Member> findMembers() {
    long start = System.currentTimeMillis();
    try {
      return memberRepository.findAll();
    } finally {
      long finish = System.currentTimeMillis();
      long timeMs = finish - start;
      System.out.println("join = " + timeMs + "ms");
    }

  }

  public Optional<Member> findOne(Long memberId) {
    return memberRepository.findById(memberId);
  }
}
