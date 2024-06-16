CREATE (a:Application { name:"REPIND" })
CREATE (t:Type { name:"FB_FAMILY" })

MATCH  (a:Application { name:"REPIND"})
MATCH  (t:Type { name:"FB_FAMILY"})
CREATE (a)-[cm:CAN_MANAGE]->(t)

CREATE (a:Application { name:"FBAPI" })
CREATE (a:Application { name:"HACHIKO" })
CREATE (t:Type { name:"GP" })

MATCH  (a:Application { name:"REPIND"})
MATCH  (t:Type { name:"GP"})
CREATE (a)-[cm:CAN_MANAGE]->(t)

MATCH  (a:Application { name:"FBAPI"})
MATCH  (t:Type { name:"FB_FAMILY"})
CREATE (a)-[cm:CAN_MANAGE]->(t)
RETURN a,cm,t

MATCH  (a:Application { name:"HACHIKO"})
MATCH  (t:Type { name:"FB_FAMILY"})
CREATE (a)-[cm:CAN_MANAGE]->(t)
RETURN a,cm,t

CREATE CONSTRAINT ON (t:Tribe) ASSERT t.id IS UNIQUE
CREATE CONSTRAINT ON (i:Individual) ASSERT i.gin IS UNIQUE

CREATE CONSTRAINT ON (a:Application) ASSERT a.name IS UNIQUE
CREATE CONSTRAINT ON (t:Type) ASSERT t.name IS UNIQUE
