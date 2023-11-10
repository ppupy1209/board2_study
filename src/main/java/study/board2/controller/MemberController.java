package study.board2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.board2.dto.member.MemberPatchDto;
import study.board2.dto.member.MemberPostDto;
import study.board2.dto.member.MemberResponseDto;
import study.board2.dto.response.MultiResponseDto;
import study.board2.dto.response.SingleResponseDto;
import study.board2.service.MemberService;
import study.board2.utils.UriCreator;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final static String MEMBER_DEFAULT_URL = "/members";
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto request) {
        Long memberId = memberService.saveMember(request.toEntity());

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, memberId);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") Long memberId,
            @Valid @RequestBody MemberPatchDto request) {
        memberService.updateMember(request.toEntity(), memberId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId) {
        MemberResponseDto response = memberService.findMember(memberId);


        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam int page,
                                     @RequestParam int size) {
        Page<MemberResponseDto> pagedMembers = memberService.findMembers(page - 1, size);
        List<MemberResponseDto> members = pagedMembers.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(members,pagedMembers),HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
