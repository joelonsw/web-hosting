package webhosting.webhosting.member.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import webhosting.webhosting.member.service.MemberService;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage.html";
    }
}
