SET REFERENTIAL_INTEGRITY FALSE;

delete from `activity_log`;
delete from `comment`;
delete from `comment_aud`;
delete from `revision`;
delete from `user`;
delete from `user_aud`;

SET REFERENTIAL_INTEGRITY TRUE;