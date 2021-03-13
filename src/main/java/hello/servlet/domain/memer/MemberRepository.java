package hello.servlet.domain.memer;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.*;


/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

    /**
     * mailSender 포함 모든 멤버들 static 으로 선언
     */
    private static MailSender mailSender;
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;


    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        if (mailSender == null) {
            JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
            mailSenderImpl.setUsername("if0rever");
            mailSenderImpl.setPassword("5W8F2V8QWY87");
            mailSenderImpl.setPort(587);
            mailSenderImpl.setHost("smtp.naver.com");

            mailSender = mailSenderImpl;
        }
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        sendMail(member);
        return member;
    }

    private void sendMail(Member member) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("if0rever@naver.com");
        mailMessage.setTo("two_somang@icloud.com");
        mailMessage.setSubject("가입 완료");
        mailMessage.setText(member.getUsername() + "님의 가입을 축하드립니다.\n" + "id = " + member.getId());

        mailSender.send(mailMessage);
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
