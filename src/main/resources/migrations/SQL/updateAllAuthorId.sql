UPDATE "MP3"
SET author_id = i.id FROM (SELECT id, name FROM author) i
WHERE i.name = "MP3".author