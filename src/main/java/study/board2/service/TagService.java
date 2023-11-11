package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Tag;
import study.board2.exception.BusinessLogicException;
import study.board2.exception.ExceptionCode;
import study.board2.repository.TagRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {

    private final TagRepository tagRepository;

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public Tag findVerifiedTag(String name) {
        Optional<Tag> optionalTag = tagRepository.findByName(name);

        Tag tag = optionalTag.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND));

        return tag;
    }


}
