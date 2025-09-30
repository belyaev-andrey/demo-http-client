CREATE TABLE quote (
    id SERIAL PRIMARY KEY,
    text VARCHAR(1024) NOT NULL,
    author VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL
);

INSERT INTO quote(text, author, source) VALUES
('Life is like riding a bicycle. To keep your balance you must keep moving.', 'Albert Einstein', ''),
('Be yourself; everyone else is already taken.', 'Oscar Wilde', ''),
('You only live once, but if you do it right, once is enough.', 'Mae West', ''),
('The happiness of your life depends upon the quality of your thoughts.', 'Marcus Aurelius', 'Meditations'),
('In the midst of chaos, there is also opportunity.', 'Sun Tzu', 'The Art of War'),
('Do. Or do not. There is no try.', 'Yoda', 'The Empire Strikes Back'),
('It does not matter how slowly you go as long as you do not stop.', 'Confucius', ''),
('It always seems impossible until it''s done.', 'Nelson Mandela', ''),
('Stay hungry, stay foolish.', 'Steve Jobs', 'Stanford Commencement 2005'),
('The future belongs to those who believe in the beauty of their dreams.', 'Eleanor Roosevelt', '');
