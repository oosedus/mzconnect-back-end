package likelion.MZConnent.repository.chat;

import likelion.MZConnent.domain.chat.Chat;
import likelion.MZConnent.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByClubOrderByCreatedDateDesc(Club club);
}
