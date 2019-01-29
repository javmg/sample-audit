package com.jmgits.sample.audit.rest;

import com.jmgits.sample.AbstractIT;
import com.jmgits.sample.audit.domain.ActivityLog;
import com.jmgits.sample.audit.domain.Comment;
import com.jmgits.sample.audit.repository.ActivityLogRepository;
import com.jmgits.sample.audit.repository.CommentRepository;
import com.jmgits.sample.audit.repository.CommentRevisionRepository;
import com.jmgits.sample.audit.support.TokenSupport;
import com.jmgits.sample.audit.util.JsonUtils;
import com.jmgits.sample.audit.view.CommentCreateOrUpdate;
import com.jmgits.sample.audit.view.Id;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.history.Revision;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@Sql("/sql/comment.sql")
public class CommentRestControllerIT extends AbstractIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRevisionRepository commentRevisionRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private TokenSupport tokenSupport;

    @Test
    public void testCreateKoForbidden() {

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments", POST, new HttpEntity<>(new CommentCreateOrUpdate()), String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(FORBIDDEN)));
    }

    @Test
    public void testCreateOk() {

        CommentCreateOrUpdate criteria = criteriaCreateOrUpdate();

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments", POST, tokenSupport.toRequest(criteria), String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(CREATED)));

        String body = responseEntity.getBody();

        assertThat(body, allOf(
                hasJsonPath("$.id"),
                hasJsonPath("$.creator.id", is(1)),
                hasJsonPath("$.description", is(criteria.getDescription())),
                hasJsonPath("$.editor", is(nullValue())),
                hasJsonPath("$.title", is(criteria.getTitle()))
        ));

        //
        // check crated in DB

        Long id = JsonUtils.read(body, Id.class).getId();

        boolean existsById = commentRepository.existsById(id);

        assertThat(existsById, is(true));

        // check functional auditing

        List<ActivityLog> activityLogs = activityLogRepository.findAll();

        assertThat(activityLogs, contains(allOf(
                hasProperty("event", is("Comment created.")),
                hasProperty("username", is("user1"))
        )));

        //
        // check technical auditing

        Revision<Long, Comment> revision = commentRevisionRepository.getLatestById(id);

        assertThat(revision.getEntity().getTitle(), is(criteria.getTitle()));
        assertThat(revision.getMetadata().getDelegate(), hasProperty("username", is("user1")));
    }

    @Test
    public void testSearchKoForbidden() {

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/paged?own=true&title=title", GET, null, String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(FORBIDDEN)));
    }

    @Test
    public void testSearchOk() {

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/paged?own=true&title=title", GET, tokenSupport.toRequest(null), String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(OK)));

        String body = responseEntity.getBody();

        assertThat(body, allOf(
                hasJsonPath("$.totalPages", is(1)),
                hasJsonPath("$.totalElements", is(2))
        ));
    }

    @Test
    public void testUpdateKoForbidden() {

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/1", PUT, new HttpEntity<>(new CommentCreateOrUpdate()), String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(FORBIDDEN)));
    }

    @Test
    public void testUpdateOk() {

        CommentCreateOrUpdate criteria = criteriaCreateOrUpdate();

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/1", PUT, tokenSupport.toRequest(criteria), String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(OK)));

        String body = responseEntity.getBody();

        assertThat(body, allOf(
                hasJsonPath("$.id", is(1)),
                hasJsonPath("$.creator.id", is(1)),
                hasJsonPath("$.description", is(criteria.getDescription())),
                hasJsonPath("$.editor.id", is(1)),
                hasJsonPath("$.title", is(criteria.getTitle()))
        ));

        //
        // check updated in DB

        Comment comment = commentRepository.getById(1L);

        assertThat(comment, hasProperty("editor", hasProperty("id", is(1L))));

        // check functional auditing

        List<ActivityLog> activityLogs = activityLogRepository.findAll();

        assertThat(activityLogs, contains(allOf(
                hasProperty("event", is("Comment '1' updated.")),
                hasProperty("username", is("user1"))
        )));

        //
        // check technical auditing

        Revision<Long, Comment> revision = commentRevisionRepository.getLatestById(1L);

        assertThat(revision.getEntity().getTitle(), is(criteria.getTitle()));
        assertThat(revision.getMetadata().getDelegate(), hasProperty("username", is("user1")));
    }

    @Test
    public void testDeleteKoForbidden() {

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/1", DELETE, null, String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(FORBIDDEN)));
    }

    @Test
    public void testDelete() {

        HttpEntity<Object> request = tokenSupport.toRequest(null);

        //
        // business

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/comments/1", DELETE, request, String.class);

        //
        // check response

        assertThat(responseEntity, hasProperty("statusCode", is(NO_CONTENT)));

        //
        // check deleted from DB

        boolean existsById = commentRepository.existsById(1L);

        assertThat(existsById, is(false));

        // check functional auditing

        List<ActivityLog> activityLogs = activityLogRepository.findAll();

        assertThat(activityLogs, contains(allOf(
                hasProperty("event", is("Comment '1' deleted.")),
                hasProperty("username", is("user1"))
        )));

        //
        // check technical auditing

        Revision<Long, Comment> revision = commentRevisionRepository.findRevisions(1L).getLatestRevision();

        assertThat(revision.getEntity().getTitle(), is(nullValue()));
        assertThat(revision.getMetadata().getDelegate(), hasProperty("username", is("user1")));
    }

    //
    // private

    private CommentCreateOrUpdate criteriaCreateOrUpdate() {

        CommentCreateOrUpdate criteria = new CommentCreateOrUpdate();

        criteria.setDescription("myDescription");
        criteria.setTitle("myTitle");

        return criteria;
    }
}
