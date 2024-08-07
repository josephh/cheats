# Palantir (founded 2003) Foundry
Data-driven operations + decision-making 
----------------
Akshay Krishnaswamy

Palantir "Foundry" => O/S for modern enterprise.  The 'Producer-consumer' paradigm is too limiting. Foundry = an open architecture for closing the loop between "operations and analytics"
Palantir "Gotham" => O/S for intelligence and defence
Palantir "Apollo" => O/S for deploying and managing complex software

Architecture?
100+ microservices which provide 24+ OOTB user apps
1. Data Ontology /pipeline = how data lakes, data warehouses, machine learning [how they're all connected together]
2. "Digital twin & modelling" Data science etc - fuse data and models together...
3. Operational actions = 70% of Foundry users...

"Monocle" - one of the apps to view the ontology.  See "health" view of the ontology; "security" view of the ontology; data LINEAGE view...
PySpark code = Python API for Apache Spark.  Configures data sources...
Data is managed like code and code braches used to apply code to different "environments" which in fact are logically separated but all within the same cloud instances

Comes with native connectors, e.g. for SAP (Netweaver??) - that auto ingests SAP tables and metadata and can suggest example workflows that suit SAP integrations...

- "Ontology Management" app; for use by IT/ sysadmin team members... (1) object types (2) link types (3) action typea
- "Object Explorer" - view the data in various views, appropriate to the data type
- "Quiver" - sibling app of Object Explorer pitched at engineering users... timeline info/ charting data 

connect into Foundry via,
* REST
* jdbc
* direct file access 
* 

- 'Model Objectives' library. Create models then 'bind' them to the Ontology
-> inputs can be defined and outputs mapped back onto the Ontology ::
------> this leads to the final part of Palantir = provide optimal decision to an end user to apply in Operations
------> end users are notified through some messaging system, with info on what the alert concerns and suggested actions
------> 'actions' are part of the ontology at the core level

- Operational app "Workshop" another is "Sibling"
- "Vertex" map


We have done this already for a couple of brands - adding a checklist here to record progress.  

- [] Mercury
- [x] Venus
Harvester

Stonehouse  
Sizzling  
Toby Carvery  
Vintage Inns  
All Bar One  
Ember Inns  
Browns  
Orleans Smokehouse  
O'Neills  
Nicolsons  
Son of Steak  
High Street  
Castle  
Premium Country Pubs  
Miller & Carter
