CREATE TABLE operadoras (
    id SERIAL PRIMARY KEY,
    registro_ans VARCHAR(50),
    nome_fantasia VARCHAR(255),
    razao_social VARCHAR(255),
    modalidade VARCHAR(100),
    uf CHAR(2),
    municipio VARCHAR(100),
    data_registro DATE
);

CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    registro_ans VARCHAR(50),
    ano INT,
    trimestre INT,
    receita_total DECIMAL(18,2),
    despesas_eventos DECIMAL(18,2)
);



COPY operadoras(registro_ans, nome_fantasia, razao_social, modalidade, uf, municipio, data_registro)
FROM '/downloadsDados/Operadoras_Ativas_Na_ANS.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

COPY demonstracoes_contabeis(registro_ans, ano, trimestre, receita_total, despesas_eventos)
FROM '/downloadsDados/Demonstracoes_Contabeis_2023.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

COPY demonstracoes_contabeis(registro_ans, ano, trimestre, receita_total, despesas_eventos)
FROM '/downloadsDados/Demonstracoes_Contabeis_2024.csv' 
WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');



SELECT registro_ans, SUM(despesas_eventos) AS total_despesas
FROM demonstracoes_contabeis
WHERE ano = EXTRACT(YEAR FROM CURRENT_DATE) AND trimestre = (
    CASE 
        WHEN EXTRACT(MONTH FROM CURRENT_DATE) IN (1, 2, 3) THEN 1
        WHEN EXTRACT(MONTH FROM CURRENT_DATE) IN (4, 5, 6) THEN 2
        WHEN EXTRACT(MONTH FROM CURRENT_DATE) IN (7, 8, 9) THEN 3
        ELSE 4
    END
)
GROUP BY registro_ans
ORDER BY total_despesas DESC
LIMIT 10;


SELECT registro_ans, SUM(despesas_eventos) AS total_despesas
FROM demonstracoes_contabeis
WHERE ano = EXTRACT(YEAR FROM CURRENT_DATE) - 1
GROUP BY registro_ans
ORDER BY total_despesas DESC
LIMIT 10;

