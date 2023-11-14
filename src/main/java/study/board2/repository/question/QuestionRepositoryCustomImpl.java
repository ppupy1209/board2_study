package study.board2.repository.question;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.board2.domain.QMember;
import study.board2.domain.QQuestion;
import study.board2.domain.Question;

import javax.persistence.EntityManager;

import java.util.List;

import static study.board2.domain.QMember.*;
import static study.board2.domain.QQuestion.*;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Question> findQuestionsWithMember(Pageable pageable) {
        QueryResults<Question> results = queryFactory.selectFrom(question)
                .join(question.member, member)
                .fetchJoin()
                .orderBy(question.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<Question> questions = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(questions,pageable,total);
    }
}
