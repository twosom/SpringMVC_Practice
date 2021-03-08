package hello.servlet.domain.memer;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * 동시성 문제가 고려되어 있지 않기에, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {


    private static MailSender mailSender;

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;


    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        if (mailSender == null) {
            JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
            mailSenderImpl.setUsername("USER_NAME");
            mailSenderImpl.setPassword("PASSWORD");
            mailSenderImpl.setPort(PORT_NUMBER);
            mailSenderImpl.setHost("SMTP_HOST");

            mailSender = mailSenderImpl;
        }
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ADMIN_EMAIL_ADDRESS");
        mailMessage.setTo("MEMBER_EMAIL_ADDRESS");
        mailMessage.setSubject("가입 완료");
        mailMessage.setText(member.getUsername() + "님의 가입을 축하드립니다.\n" + "id = " + member.getId());

        mailSender.send(mailMessage);
        return member;
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
