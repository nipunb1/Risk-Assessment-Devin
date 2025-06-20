
SELECT 
    COUNT(*) as total_records,
    COUNT(risk_impact) as non_null_records,
    COUNT(*) - COUNT(risk_impact) as null_records
FROM risk;

SELECT risk_id, risk_impact, risk_desc 
FROM risk 
WHERE risk_impact IS NULL OR risk_impact = '' OR risk_impact NOT IN ('LOW', 'MEDIUM', 'HIGH')
ORDER BY risk_id;

UPDATE risk 
SET risk_impact = 'MEDIUM' 
WHERE risk_impact IS NULL OR risk_impact = '';

UPDATE risk 
SET risk_impact = 'MEDIUM' 
WHERE risk_impact NOT IN ('LOW', 'MEDIUM', 'HIGH');

SELECT 
    risk_impact,
    COUNT(*) as count
FROM risk 
GROUP BY risk_impact
ORDER BY risk_impact;

SELECT risk_id, risk_impact, risk_desc 
FROM risk 
WHERE risk_impact IS NULL OR risk_impact = '' OR risk_impact NOT IN ('LOW', 'MEDIUM', 'HIGH')
ORDER BY risk_id;
