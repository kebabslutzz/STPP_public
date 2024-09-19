--liquibase formatted sql

--changeset kebabslutzz:add_discussion_id_to_comments
ALTER TABLE discussion_comments ADD COLUMN discussion_id BIGINT;

--changeset kebabslutzz:add_foreign_key_to_comments
ALTER TABLE discussion_comments ADD CONSTRAINT fk_discussion_comments_discussion_id FOREIGN KEY (discussion_id) REFERENCES movie_discussions(id) ON DELETE CASCADE;

--rollback ALTER TABLE discussion_comments DROP CONSTRAINT fk_discussion_comments_discussion_id;
--rollback ALTER TABLE discussion_comments DROP COLUMN discussion_id;
