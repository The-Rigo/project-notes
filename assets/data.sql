CREATE TABLE "users" (
                         "userid" BIGSERIAL PRIMARY KEY,
                         "username" VARCHAR(255),
                         "email" VARCHAR(255),
                         "password" VARCHAR(255),
                         "created_at" TIMESTAMP,
                         "updated_at" TIMESTAMP,
                         "version" BIGINT
);

CREATE TABLE "note" (
                        "noteid" BIGSERIAL PRIMARY KEY,
                        "title" VARCHAR(255),
                        "content" VARCHAR(255),
                        "archived" BOOLEAN,
                        "created_at" TIMESTAMP,
                        "updated_at" TIMESTAMP,
                        "version" BIGINT
);

CREATE TABLE "tag" (
                       "tagid" BIGSERIAL PRIMARY KEY,
                       "name" VARCHAR(255),
                       "user_id" BIGINT NOT NULL,
                       "created_at" TIMESTAMP,
                       "updated_at" TIMESTAMP,
                       "version" BIGINT,
                       CONSTRAINT "fk_tag_user" FOREIGN KEY ("user_id") REFERENCES "users" ("userid")
);

CREATE TABLE "tag_note" (
                            "note_id" BIGINT NOT NULL,
                            "tag_id" BIGINT NOT NULL,
                            CONSTRAINT "fk_tag_note_note" FOREIGN KEY ("note_id") REFERENCES "note" ("noteid"),
                            CONSTRAINT "fk_tag_note_tag" FOREIGN KEY ("tag_id") REFERENCES "tag" ("tagid")
);