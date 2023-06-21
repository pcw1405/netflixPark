package com.netf.netflix.Controller;

import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Constant.Role;
import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Dto.VideoFormDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    private LocalDate LocalDate;

    @GetMapping(value="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "/register";
    }

    @PostMapping(value = "/new")
    @ResponseBody
    public ResponseEntity<?> newMember(@RequestBody MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        Map<String, Object> response = new HashMap<>();
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
            response.put("message", "회원가입이 성공하였습니다. 로그인 해주세요");
            response.put("code", 200);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping(value ="/login")
    public String loginMember(){
        return "/login";
    }
    @GetMapping(value="/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호 확인해주세요.");
        return "/login";
    }

    @ResponseBody
    @PostMapping(value="/login")
    public ResponseEntity<?> loginMember(@RequestBody MemberFormDto memberFormDto, Model model, HttpSession session) {

        String email = memberFormDto.getEmail();
        String password = memberFormDto.getPassword();
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            // 인증 성공 시
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", email);
            response.put("message","로그인에 성공했습니다.");
            response.put("code", 200);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            response.put("message","아이디와 비밀번호를 확인해주세요");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping(value = "/find-id")
    public String findIdMain(){
        return "/find-id";
    }

    @PostMapping(value = "/find-email")
    public String findIdPost(Model model, @RequestParam("name") String name,
                             @RequestParam("phonenum") String phonenum){
        Member member = memberRepository.findByNameAndPhoneNumber(name, phonenum);
        if(member == null || member.getEmail() == null){
            model.addAttribute("message", "찾으시는 이메일이 없습니다. 이름과 전화번호를 확인해주세요.");
        } else {
            model.addAttribute("message", "찾으시는 아이디는: " + member.getEmail());
        }
        return "/find-id";
    }

    @GetMapping(value = "/find-pw")
    public String findPwMain(){
        return "/find-pw";
    }

    @PostMapping(value = "/find-pw")
    public String findPwPost(HttpSession session, @RequestParam("email") String email,
                             @RequestParam("phonenum") String phonenum, Model model) {
        Member member = memberRepository.findByEmailAndPhoneNumber(email, phonenum);
        if(member != null) {
            session.setAttribute("member", member);
            return "/set-pw";
        }else{
            model.addAttribute("Message", "회원정보 조회에 실패했습니다.");
            return "/find-pw";
        }
    }

    @PostMapping(value = "/set-pw")
    public String setPwPost(@RequestParam("password") String password, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        String encodedPassword = passwordEncoder.encode(password);
        member.setPassword(encodedPassword);

        memberService.changePassword(member, encodedPassword);

        return "/login";
    }

    @GetMapping(value = "/memberList")
    public String memberList(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // 페이지당 아이템 개수

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Member> memberPage = memberRepository.findPaginated(pageable);
        List<Member> memberList = memberPage.getContent();
        int totalPages = memberPage.getTotalPages();

        model.addAttribute("memberList", memberList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);



        return "member/memberList";
    }

    @ResponseBody
    @PostMapping(value = "/memberEdit")
    public ResponseEntity<?> videoCreateFrom(@RequestBody MemberFormDto memberFormDto) throws Exception {
        Map<String, Object> response = new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate newExpirationDate = currentDate.plusMonths(1);
        Long memberId = memberFormDto.getId();
        String memberRole = memberFormDto.getRole();
        String membershipRoles = memberFormDto.getMembershipRole();
        try {
            Member member = memberRepository.findById(memberId).orElse(null);

            if (memberRole.equals("USER")) {
                member.setRole(Role.USER);
            } else if (memberRole.equals("ADMIN")) {
                member.setRole(Role.ADMIN);
            }
            if (membershipRoles.equals("BASIC")) {
                member.setMembershipRole(MembershipRole.BASIC);
                member.setMembershipExpirationDate(newExpirationDate);
            } else if (membershipRoles.equals("STANDARD")) {
                member.setMembershipRole(MembershipRole.STANDARD);
                member.setMembershipExpirationDate(newExpirationDate);
            } else if (membershipRoles.equals("PREMIUM")) {
                member.setMembershipRole(MembershipRole.PREMIUM);
                member.setMembershipExpirationDate(newExpirationDate);
            } else if (membershipRoles.equals("NONE")) {
                member.setMembershipRole(MembershipRole.NONE);
                member.setMembershipExpirationDate(newExpirationDate);
            }
            memberRepository.save(member);
        } catch (IllegalArgumentException e) {
            response.put("message", "수정중 에러가 발생하엿습니다 확인후 다시 시도해주세요");
            return ResponseEntity.ok(response);
        }
        response.put("message", "수정이 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(response);
    }
}
