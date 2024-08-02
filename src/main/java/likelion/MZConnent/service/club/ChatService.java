package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.chat.Chat;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.SendChatRequest;
import likelion.MZConnent.dto.club.response.MyClubChatResponse;
import likelion.MZConnent.dto.club.response.SendChatResponse;
import likelion.MZConnent.repository.chat.ChatRepository;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepository chatRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public MyClubChatResponse getClubChats(String email, Long clubId) {
        Member member = getMemberByEmail(email);
        Club club = getClubById(clubId);
        validateClubMember(club, member);

        List<Chat> chats = chatRepository.findByClubOrderByCreatedDateDesc(club);
        ClubMember clubLeader = getClubLeader(club);

        return MyClubChatResponse.builder()
                .clubId(clubId)
                .memberId(member.getId())
                .title(club.getTitle())
                .meetingDate(club.getMeetingDate())
                .content(club.getContent())
                .cultureName(club.getCulture().getName())
                .leaderId(clubLeader.getMember().getId())
                .chats(chats.stream().map(this::convertToChatDto).collect(Collectors.toList()))
                .build();
    }

    public SendChatResponse sendChat(String email, Long clubId, SendChatRequest request) {
        Member member = getMemberByEmail(email);
        Club club = getClubById(clubId);
        validateClubMember(club, member);

        Chat chat = Chat.builder()
                .createdDate(LocalDateTime.now())
                .content(request.getContent())
                .club(club)
                .member(member)
                .build();

        chatRepository.save(chat);

        return SendChatResponse.builder()
                .chatId(chat.getChatId())
                .createdDate(chat.getCreatedDate())
                .content(chat.getContent())
                .memberId(chat.getMember().getId())
                .clubId(chat.getClub().getClubId())
                .build();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
    }

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 동아리가 존재하지 않습니다."));
    }

    private void validateClubMember(Club club, Member member) {
        boolean isMemberOfClub = club.getClubMembers().stream()
                .anyMatch(cm -> cm.getMember().getId().equals(member.getId()));
        if (!isMemberOfClub) {
            throw new IllegalArgumentException("해당 멤버는 해당 클럽의 멤버가 아닙니다.");
        }
    }

    private ClubMember getClubLeader(Club club) {
        return club.getClubMembers().stream()
                .filter(m -> m.getClubRole().equals(ClubRole.LEADER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("동아리장이 존재하지 않습니다."));
    }

    private MyClubChatResponse.ChatDto convertToChatDto(Chat chat) {
        ClubMember chatMember = getChatMember(chat);
        return MyClubChatResponse.ChatDto.builder()
                .chatId(chat.getChatId())
                .createdDate(chat.getCreatedDate())
                .content(chat.getContent())
                .memberId(chat.getMember().getId())
                .userName(chat.getMember().getUsername())
                .memberProfileUrl(chat.getMember().getProfileImageUrl())
                .role(chatMember.getClubRole())
                .build();
    }

    private ClubMember getChatMember(Chat chat) {
        ClubMember chatMember = chat.getClub().getClubMembers().stream()
                .filter(cm -> cm.getMember().getId().equals(chat.getMember().getId()))
                .findFirst()
                .orElse(null);

        return chatMember;
    }
}
