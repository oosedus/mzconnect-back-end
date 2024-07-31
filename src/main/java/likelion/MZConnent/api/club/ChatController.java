package likelion.MZConnent.api.club;

import likelion.MZConnent.dto.club.request.SendChatRequest;
import likelion.MZConnent.dto.club.response.MyClubChatResponse;
import likelion.MZConnent.dto.club.response.SendChatResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.club.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/api/club/{clubId}/chats")
    public ResponseEntity<MyClubChatResponse> getClubChats(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId) {
        MyClubChatResponse response = chatService.getClubChats(userPrinciple.getEmail(), clubId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/club/{clubId}/chats")
    public ResponseEntity<SendChatResponse> sendChat(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId, @RequestBody SendChatRequest request) {
        SendChatResponse response = chatService.sendChat(userPrinciple.getEmail(), clubId, request);
        return ResponseEntity.ok(response);
    }

}
