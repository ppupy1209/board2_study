package study.board2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.board2.domain.Member;
import study.board2.dto.member.MemberPatchDto;
import study.board2.dto.member.MemberPostDto;
import study.board2.dto.member.MemberResponseDto;
import study.board2.service.MemberService;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @DisplayName("회원을 등록한다.")
    @Test
    void postMember() throws Exception {
        // given
        MemberPostDto request = MemberPostDto.of("kim");

        // when & then
        mockMvc.perform(post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void patchMember() throws Exception {

        // given
        MemberPatchDto request = MemberPatchDto.of("kim");

        // when & then
        mockMvc.perform(patch("/members/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원 이름은 필수값이다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameIsBlank_ReturnBadRequest(String input) throws Exception {

        // given
        MemberPostDto request = MemberPostDto.of(input);


        // when & then
        mockMvc.perform(post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("BAD REQUEST"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(jsonPath("$.fieldErrors[0].reason").value("이름은 필수 항목입니다."));

    }

    @DisplayName("회원 1명을 조회한다.")
    @Test
    void getMember() throws Exception {
        // given
        when(memberService.findMember(1L)).thenReturn(MemberResponseDto.of(Member.of(1L, "kim")));


        // when & then
        mockMvc.perform(get("/members/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("kim"));

    }

    @DisplayName("멤버 리스트를 조회한다.")
    @Test
    void getMembers() throws Exception {

        // given
        int page = 1;
        int size = 2;
        when(memberService.findMembers(page - 1, size)).thenReturn(Page.empty());

        // when & then
        mockMvc.perform(get("/members")
                        .queryParam("page", "1")
                        .queryParam("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());

    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteMember() throws Exception {

        // when & then
        mockMvc.perform(delete("/members/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        
    }
}