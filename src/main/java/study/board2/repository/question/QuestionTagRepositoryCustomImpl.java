package study.board2.repository.question;


import com.querydsl.jpa.impl.JPAQueryFactory;
import study.board2.domain.QQuestion;
import study.board2.domain.QQuestionTag;
import study.board2.domain.QTag;
import study.board2.domain.QuestionTag;

import javax.persistence.EntityManager;
import java.util.List;

import static study.board2.domain.QQuestion.*;
import static study.board2.domain.QQuestionTag.*;
import static study.board2.domain.QTag.*;

public class QuestionTagRepositoryCustomImpl implements QuestionTagRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionTagRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<QuestionTag> findByQuestionId(Long questionId) {
        return queryFactory.selectFrom(questionTag)
                .join(questionTag.question, question)
                .fetchJoin()
                .join(questionTag.tag, tag)
                .fetchJoin()
                .where(question.id.eq(questionId))
                .fetch();
    }
}
