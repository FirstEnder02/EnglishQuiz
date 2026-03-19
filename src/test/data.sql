-- ============================================================
-- English Quiz — Sample Data
-- ============================================================
-- Run these statements after the application has started once
-- (the schema is auto-created by Spring Boot on first run).
--
-- Covers two categories: Grammar and Vocabulary.
-- Levels 1, 1+, 2, 2+, 3.
-- Mix of Single-answer (S) and Multiple-choice (M) questions.
-- ============================================================

-- ── Categories ──────────────────────────────────────────────
INSERT INTO category (title) VALUES
  ('Grammar'),
  ('Vocabulary');

-- ── Levels ──────────────────────────────────────────────────
-- Grammar levels (category_id = 1)
INSERT INTO level (category_id, level) VALUES
  (1, '1'),
  (1, '1+'),
  (1, '2'),
  (1, '2+'),
  (1, '3');

-- Vocabulary levels (category_id = 2)
INSERT INTO level (category_id, level) VALUES
  (2, '1'),
  (2, '1+'),
  (2, '2');

-- ── Questions — Grammar Level 1 (level_id = 1) ──────────────
INSERT INTO question (level_id, title, explaination, type, media_url) VALUES
  (1,
   'Which sentence uses the correct form of "to be"?',
   'Use "is" with third-person singular subjects (he/she/it).',
   'S', NULL),
  (1,
   'Choose the correct article: "___ umbrella was left at the door."',
   '"An" is used before words that start with a vowel sound. "Umbrella" starts with a vowel sound.',
   'S', NULL),
  (1,
   'Which words are pronouns? (Select all that apply)',
   'Pronouns replace nouns. "He", "they", and "it" are all pronouns.',
   'M', NULL);

-- Answers — Q1 (id=1)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (1, 'A', 'She are happy.',       FALSE),
  (1, 'B', 'She is happy.',        TRUE),
  (1, 'C', 'She be happy.',        FALSE),
  (1, 'D', 'She am happy.',        FALSE);

-- Answers — Q2 (id=2)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (2, 'A', 'A umbrella',   FALSE),
  (2, 'B', 'An umbrella',  TRUE),
  (2, 'C', 'The umbrella', FALSE),
  (2, 'D', 'No article',   FALSE);

-- Answers — Q3 (id=3)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (3, 'A', 'He',      TRUE),
  (3, 'B', 'They',    TRUE),
  (3, 'C', 'Run',     FALSE),
  (3, 'D', 'It',      TRUE),
  (3, 'E', 'Blue',    FALSE);

-- ── Questions — Grammar Level 1+ (level_id = 2) ─────────────
INSERT INTO question (level_id, title, explaination, type, media_url) VALUES
  (2,
   'Which sentence is in the simple past tense?',
   'Simple past tense uses the past form of the verb. "Walked" is the past form of "walk".',
   'S', NULL),
  (2,
   'Choose the sentence with correct subject-verb agreement.',
   'Plural subjects take plural verbs. "The children play" is correct.',
   'S', NULL);

-- Answers — Q4 (id=4)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (4, 'A', 'She walks to school.',        FALSE),
  (4, 'B', 'She is walking to school.',   FALSE),
  (4, 'C', 'She walked to school.',       TRUE),
  (4, 'D', 'She will walk to school.',    FALSE);

-- Answers — Q5 (id=5)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (5, 'A', 'The children plays outside.',  FALSE),
  (5, 'B', 'The children play outside.',   TRUE),
  (5, 'C', 'The children playing outside.', FALSE),
  (5, 'D', 'The children played outside.',  FALSE);

-- ── Questions — Grammar Level 2 (level_id = 3) ──────────────
INSERT INTO question (level_id, title, explaination, type, media_url) VALUES
  (3,
   'Which sentence correctly uses the present perfect tense?',
   'Present perfect = has/have + past participle. "I have finished" is correct.',
   'S', NULL),
  (3,
   'Select all sentences that contain a conditional clause.',
   'Conditional clauses begin with "if" or "unless".',
   'M', NULL);

-- Answers — Q6 (id=6)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (6, 'A', 'I have finished my homework.',  TRUE),
  (6, 'B', 'I finished my homework.',       FALSE),
  (6, 'C', 'I finish my homework.',         FALSE),
  (6, 'D', 'I was finishing my homework.',  FALSE);

-- Answers — Q7 (id=7)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (7, 'A', 'If it rains, I will stay home.',          TRUE),
  (7, 'B', 'She goes to the market every Saturday.',  FALSE),
  (7, 'C', 'Unless you hurry, we will be late.',      TRUE),
  (7, 'D', 'He reads books every evening.',           FALSE);

-- ── Questions — Vocabulary Level 1 (level_id = 6) ───────────
INSERT INTO question (level_id, title, explaination, type, media_url) VALUES
  (6,
   'What does the word "enormous" mean?',
   '"Enormous" means very large in size.',
   'S', NULL),
  (6,
   'Which words are synonyms of "happy"? (Select all that apply)',
   'Synonyms of "happy" include joyful, content, and cheerful.',
   'M', NULL),
  (6,
   'What is the antonym of "ancient"?',
   '"Ancient" means very old, so its antonym is "modern" or "new".',
   'S', NULL);

-- Answers — Q8 (id=8)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (8, 'A', 'Very small',    FALSE),
  (8, 'B', 'Very large',    TRUE),
  (8, 'C', 'Very fast',     FALSE),
  (8, 'D', 'Very loud',     FALSE);

-- Answers — Q9 (id=9)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (9, 'A', 'Joyful',    TRUE),
  (9, 'B', 'Angry',     FALSE),
  (9, 'C', 'Content',   TRUE),
  (9, 'D', 'Gloomy',    FALSE),
  (9, 'E', 'Cheerful',  TRUE);

-- Answers — Q10 (id=10)
INSERT INTO answer (question_id, label, text, is_correct) VALUES
  (10, 'A', 'Old',       FALSE),
  (10, 'B', 'Historic',  FALSE),
  (10, 'C', 'Modern',    TRUE),
  (10, 'D', 'Broken',    FALSE);
