package study.board2.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Tag;
import study.board2.repository.tag.TagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class TagServiceTest {


    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;


    @DisplayName("태그를 등록한다.")
    @Test
    void saveTag() {
        // given
        Tag tag = Tag.of("spring");

        // when
        tagService.saveTag(tag);
        List<Tag> tags = tagRepository.findAll();

        // then
        assertThat(tags).hasSize(1)
                .extracting("name")
                .containsExactlyInAnyOrder("spring");
    }

}