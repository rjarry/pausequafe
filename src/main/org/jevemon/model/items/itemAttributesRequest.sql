SELECT 
	c.categoryName, 
	t.attributeName, 
	IFNULL(a.valueInt, a.valueFloat) AS value, 
	u.displayName AS unit 
FROM 
	dgmTypeAttributes a, 
	dgmAttributeTypes t, 
	dgmAttributeCategories c, 
	eveUnits u 
WHERE a.typeID = ? 
AND a.attributeID = t.attributeID 
AND t.categoryID = c.categoryID 
AND c.categoryName != 'NULL' 
AND t.unitID = u.unitID 

ORDER BY c.categoryName 