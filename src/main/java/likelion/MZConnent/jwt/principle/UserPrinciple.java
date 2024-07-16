package likelion.MZConnent.jwt.principle;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * SecurityContext에 저장되는 사용자의 인증 정보를 저장하는 클래스
 */
@Getter
@ToString
public class UserPrinciple extends User {
    private static final String PASSWORD_ERASED_VALUE = "[PASSWORD_ERASED]";
    private final String email;

    public UserPrinciple(String email, String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, PASSWORD_ERASED_VALUE, authorities);
        this.email = email;
    }
}