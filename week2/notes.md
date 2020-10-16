## Databases

A Database is an application that provides long term storage and functionality to efficiently access the stored data.  Almost always, end users do not communicate with your database.  Instead, programs that we write communicate with our database, using it to save information long term.  An application (written in Scala) that runs on a server might only stay alive for an hour, day, week, month, .. possibly more but rare.  When that application goes down, all the values it has stored in memory disappear.  The database provides storage the persists through multiple application lifetimes.

Why not write to file?  The first reason is efficient access.  Data stored in a file is just stored sequentially, without any useful additional structure.  Any DB we use is going to give us the tools to quickly access/query the data stored within.  There are other benefits as well.  Your DB is an application that can communicate with other applications over a network, so multiple applications can use a single DB for their long term storage.

We'll talk a bit more about what SQL vs. NoSQL databases like Mongo get you -- what advantages they have.  For now, let's get started with Mongo:

## MongoDB basics

We're running MongoDB locally in *standalone* mode.  Right now, it should be running on port 27017 on your local machine.  This is just the default port, we could change it if we like.  We're just going to communicate with our MongoDB application from our local machine, but we're going to use the setup/tools that we would use communicating over a network.  The address of our MongoDB application is localhost:27017.  We'll use a connection string to connect from Scala : "mongodb://localhost:27017"

Our MongoDB application can contain multiple *databases*.  Each databsae in MongoDB can contain multiple *collections*.  Each collection in MongoDB contains multiple *documents*.  Documents are the level that contains actual data, like usernames, addresses, colors, fruits, ...
Collections are kind of like tables/relations in other DBs, with an important difference!  Out of the box, there's no restriction on what different types of documents can be placed in the same collection.  In a single collection we could put veery different types of documents.  We do have the option to place restrictions, if we want.  In our applications where we use case classes, all the structure is provided by Scala rather than by mongo.  Who/when/where enforces structure in your data model is an important to be thinking about with respect to DBs and in general data processing.

Documents are BSON, which is Binary JSON (JavaScript Object Notation), a data format that is like JSON but provides more types.  What this means for us right now is that your document is just some arbitrary object with fields.

We still want to store related objects in the same collection and have different collections for unrelated objects, it's just the MongoDB doesn't make us specify exactly the format of the documents stored in a collection.

Every single document has an ObjectId that uniquely identifies it.  The ObjectId includes a number that is an arbitrary unique identifier.  The ObjectId is contained within a special _id field that all Documents have.

## JSON: JavaScript Object Notation

An object with properties (key-value pairs) that demonstrate JSON data types:
{
    "string": "myString",
    "number": 123,
    "object": {},
    "array": [],
    "boolean": true,
    "null": null
}

valid JSON:
[{}, {}]

JSON is text.  It's useful because it contains a little bit of structure, but not too much (it's not like an XML document).

For adding to MongoDB collection:
{
    "_id": {
        "$oid": "5f85c72ca1756421a027327f"
    },
    "title": "neat comic"
}
{
    "_id": {
        "$oid": "5f85d1b2a1756421a0273281"
    },
    "title": "example title"
}


## MongoDB basics (continued)

We've done some querying on MongoDB, using the Mongo-Scala driver.  You can also interact with MongoDB using the Mongo Shell.  The Mongo SHell runs JavaScript and most of the keywords are the same as the ones we've used.

Thus far, we've just dealt with case classes in Scala that correspond to documents in a collection in Mongo.  In MongoDB, it's common (and often preferred) to have related documents just be nested, we call them embedded documents.  This afternoon we'll see an example with Writers.  In some other databases, we would store related objects in different places and have them reference each other.  While this is possible in Mongo, it is less common.

Having done a bit of [reading](https://www.mongodb.com/blog/post/6-rules-of-thumb-for-mongodb-schema-design-part-1), there are broadly two ways to represent related objects/documents in Mongo.  You can have embedded documents, where one document contains another.  You can also have references, where one document contains the Id of another.  In each case we might have arrays of embedded objects or arrays of references.  We call these relationships between objects multiplicity relationships and we categorize them broadly as 1-to-1, 1-to-N, and N-to-N.
Examples: 
- 1-to-1 : persons and birth certificates.  Each person has one birth certificate, each birth certificate corresponds to one person
- 1-to-N : directors and movies.  Each movie has one director, but a director can have multiple movies.
- N-to-N : songs and playlists.  Each song can appear many playlists and each playlsit can contain many songs.
It's these relationships that we represent using embedding or references in Mongo.  In Mongo, it's useful to consider the *cardinality* of the N-side of these relationships when deciding how to model them.  Cardinality is just the size of a set.  Something to be wary of is slightly different definitions of cardinality/multiplicity floating around.

## Indexes

In Mongo, and other databases, efficient use of the database requires us to create indexes.  An index, like an index in a book, it lets us easily find documents based on some condition on one of more of their fields.  Checking the index in the back of a book is much faster than flipping through page by page, similarly using the index to find documents that match a filter is much faster than checking the filter on every document.  We get one index for free on the _id field.  This means finding documents based on their _id is fast, a property that is useful for the references mentioned above.  We can create indexes on fields, or on combinations of fields.  Every index we create means that retrieving documents based on that field will be faster.  Your index is always going to improve retrieval.  The tradeoff is, for each index we have, it increases the time it takes to add/modify documents to the collection.  This is because each new/edited document needs to be adjusted in the index as well.  Under the hood each index is a B-tree, which is like a search tree but it contains chunks of data at each node.

## CRUD

Create, Retrieve/Read, Update, Delete.  We talk about CRUD operations with respect to databases, they are jsut the basic operations that let us save new data, modify data, read data, and remove data.  For mongo scala: find(), deleteMany(), replaceOne(), insertMany(), ...

## Mongo as distributed database

One of the major selling points of Mongo is that it is easy to use as a distributed database.  A few definitions:
- cluster : multiple computers that are networked together
- distributed application : an application that runs on more than one machine at the same time, with network communication between the machines.  For Mongo this will mean we have the *same* database split out across multiple machines.
- router : a part of the cluster the directs network traffic, often traffic originating outside the cluster.
- High Availability (HA) : a property of a system that means the system is almost always available/functional
- Fault Tolerance : a property of a system that means the system can experience faults and continue functioning (potentially with degraded functionality)
- failover : failover behaviour is used to achieve HA and fault tolerance.  When you implement failover you have multiple copies of a running application, and when one fails the other can step in to take its place.  Often but not always the backup system performs some functionality in addition to acting as a backup
- heartbeat : a periodic pinging between different machines in a cluster, letting each other know their status.  Used to determine when one of the members of a replica set (or other pieces of distributed app) have failed.  There will always be heartbeats bouncing around in a distributed application, though it's not necessary that every component communicates with every other component.
- scalability : a property of a system that means the system can expand to deal with increased demands.  We might talk about scalability with respect to network traffic, the amount of data stored, the amount of processing required, ...
- sharding : sharding is a way to scale a database.  In Mongo, we can shard collections.  A sharded collection will be split into multiple pieces called shards, and each shard will be responsible for only a subset of the data in the collection.  

In MongoDB, we achieve HA using a *replica set*.  A replica set is at least 3 instances of our database running in a distributed manner.  One of the replica will be primary, and one of the secondary replicas will take over if the primary replica fails.
In MongoDB, we achieve scalability using *sharding* --  we build *sharded clusters*.  Example:
We have a DB that contains books and authors.  We're receiving a ton of traffic that is attempting to edit the authors in our database, and the replica set we're running on can't handle it.  We can *shard* our database to handle the increased demand but splitting our collection of authors into authors with names A-M and authors with names N-Z.  Each subset of our documents (split based on author name) gets its own replica set.  Each replica set now only have half as many write to contend with, and it able to have the demand.
In the above example, author name is used as the *shard key*.  In Mongo, we can only create sharded clusters using replica sets.  Each shard in the cluster is in fact multiple machines running replicas.  Once you have a sharded cluster, connections to that cluster should go through *mongos*, which routes your connection/traffic to the appropriate shard.

Something to note is that most of problems that can appear with a distributed database have to do with editing/adding/deleting data; reading data is rarely a problem.  If our increased demand was just in the form of reads, we could increase the size of the replica set to handle it.

## CAP Theorem

CAP is Consistency, Availability, and Partition Tolerance.  The CAP Theorem says that we can only ever have 2 of the 3 guaranteed in a distrbuted data store.
- Consistency : every read receives the most recent write or an error.
- Availability : every request receives a non-error response.  It may not contain the most recent write.
- Partition Tolerance : the system continues to operate despite some network failure.
Every distributed data store, when there are no network problems, gives us consistency and availability.  However, network problems can and do always occur.  Different distributed data stores make different tradeoffs when this happens.  Due to CAP theorem, when a network failure occurs we need Partition Tolerance, which means we give up either Consistency or Availability.  This is a decision we make when setting up the cluster (including by deciding what distributed data store to use).

## Transactions and atomicity

In general, a transaction is an interaction with the database.  What exactly a transaction provides differs based on the data store you are using and configuration options on that data store.  We often use the word "atomic" to refer to transactions or other operations on a data store.  Anything that is atomic is indivisible, which means it succeeds or fails as a single unit.  In MongoDB this is easy, operations on a single document are atomic, operations otherwise (with one exception) are not.  If I replaceOne for a document in a Mongo collection, either that document will be entirely replaced, or no change will occur.  We'll never encounter a half-replaced document.  If I update two different documents with an updateMany, it's entirely possible that one update will succeed and the other will fail.  This behaviour is something we need to consider when we're deciding on our data model.  Two documents that are modified at the same time frequently should probably be embedded.  Modifications to embedded documents are atomic, modifications to documents related by a reference are not.
In recent versions of Mongo they offer support for multi-document transactions, which can give us atomicity for operations across multiple documents.  This is slower in general than the default single-document atomic operations.

## RDBMS Intro

RDBMS is Relational DataBase Management System.  RDBMS is a SQL database, as opposed to a NoSQL database.  SQL databases have been around for a long time and are widely used.  IN general, an RDBMS requires a lot more structure than a NoSQL database.  In our RDBMS we strictly define tables contained in schema contained in databases, providing exactly the datatypes that necessary for each record.  RDBMSs use SQL (Structured Query Language) to achieve CRUD functionality.  Each RDBMS vendor has a slightly different version of SQL, but base SQL is widespread and used even outside of RDBMSs.

In an RDBMS, we have less flexibility with the structure of our records than in Mongo.  In addition, we have strict rules for our data model.  There rules are called *normalization* and are described in a series of *normal forms*.  We should know that RDBMSs are normalized but we don't need to know all the specifics.  We do need to know that normalization means there are no embedded records in an RDBMS, all multiplicity relationships are achieved using references (Foreign keys).

Interactions with an RDBMS take place in Transactions, and transaction in an RDBMS have ACID properties:

## ACID

- Atomicity : a transaction succeeds or fails and a single unit
- Consistency : a transaction takes the database from one valid state to another (NOT the same as CAP Consistency)
- Isolation : multiple transactions taking place at the same time don't interfere with each other
- Durability : transactions satisfy ACID even in the case of disaster (power shut off to DB)
It's useful to start thinking about this as a property of SQL databases, but really SQL and NoSQL databases both implement useful features of the other.  Mongo introduced multi-document transactions with atomicity in 4.4.  In PostgreSQL (an RDBMS) you can store and index nested JSON, similar to a mongoDB document without any of the typical RDBMS structure.  We can say ACID favors Consistency and Partition Tolerance in a distributed database, as opposed to...

## BASE

- Basically Available : reads and writes are available as much as possible, using multiple machines in a cluster
- Soft state : records stored in the database may be inconsistent across a cluster, some may be more up to date than others
- Eventual consistency : if the databases runs long enough, it will achieve consistent data across the cluster
BASE favors Availability and Partition Tolerance in a distributed database.

## Aside: Lazy vs Eager fetching/loading

Loading something *eagerly* means that it is retireved immediately.  You say "I want this content from the database/file/datasource/..." and the content is retrieved.  Loading something lazily means it is retrieved only when it is actually used.  You say "I want this content from the database/file/datasource/..." and a proxy for that data is retrieved.  You can use the proxy exactly the same as you would use the real data, but the real data isn't populated until you actually use the proxy for something.  Both are reasonable strategies, it depends on context.  
If the connection is closed in the meantime, it will cause you problems for lazily loaded data.

## Friday Morning Topics:

- What does "valid state" mean when we talk about Consistency in ACID?  In an RDBMS, we have a lot of structure and a lot of rules for what our data can look like.  Attempting to break those rules in a Transaction will cause the transaction to fail.  This might be using the wrong data types, failing checks on length/null, or it might mean specifying references that don't exist.
- *Primary Key* : A Primary key is a unique identifier for some object.  In Mongo, the _id field is that primary key.  In an RDBMS we create our own primary key fields.
- 

## SQL : Structured Query Language
SQL is a query language, which means its used to query for data.  It's declarative, in that we tell our database the data we want using SQL rather than specifying exactly what we want to DB to do.  (Almost) every DB has a query planner or similar that will show you the DB's actual plan for resolving your declared query.

The are many different "dialects" of SQL.  Each major RDBMS has its own implementation, so major RBDMSs differ on their execution of some SQL functionality.  There's "core SQL" or "ANSI SQL" that is very nearly the same across DBs, and then each DB provides their own additional functionality outside of this.  We often divide SQL into sublanguages, just for organization.  Those would be:
- Data Manipulation Language (DML) : used to manipulate data, adds/removes/edits records contained within tables
- Data Definition Language (DDL) : used to manipulate tables, adds/removes/modifies tables themselves
- Data Query Language (DQL) : used to retrieve data.  Sometimes lumped in with DML.
Two more that I'll list here for completeness but we don't need to know about:
- Transaction Control Language (TCL) : used to start/stop and delimit transactions.
- Data Control Language (DCL) : used to manage DB users, grant permissions, revoke permissions

In SQL / RDBMSs we have schemas that contain tables that contain records, analogous to mongo's databases that contain collections that contain documents.  In SQL we define columns for each table, and each record in that table has all of those columns.  You can have null values in SQL, but you can't have the column be missing.
Some other terms for the above : schema is sometimes called database, table is sometimes called relation, records are somtimes rows.

We identify the sublanguage based on the first keyword, so keywords for sublanguages:
- DML : INSERT, UPDATE, DELETE
- DDL : CREATE, ALTER, DROP
- DQL : SELECT

SELECT "clauses":
- SELECT specifies columns
- FROM specifies the table
- WHERE filters the records
- ORDER BY orders the results
- LIMIT limits the output to some number of records
- OFFSET skips some number of records at the beginning of the output