## Databases

A Database is an application that provides long term storage and functionality to efficiently access the stored data.  Almost always, end users do not communicate with your database.  Instead, programs that we write communicate with our database, using it to save information long term.  An application (written in Scala) that runs on a server might only stay alive for an hour, day, week, month, .. possibly more but rare.  When that application goes down, all the values it has stored in memory disappear.  The database provides storage the persists through multiple application lifetimes.

Why not write to file?  The first reason is efficient access.  Data stored in a file is just stored sequentially, without any useful additional structure.  Any DB we use is going to give us the tools to quickly access/query the data stored within.  There are other benefits as well.  Your DB is an application that can communicate with other applications over a network, so multiple applications can use a single DB for their long term storage.

We'll talk a bit more about what SQL vs. NoSQL databases like Mongo get you -- what advantages they have.  For now, let's get started with Mongo:

## MongoDB basics

We're running MongoDB locally in *standalone* mode.  Right now, it should be running on port 27017 on your local machine.  This is just the default port, we could change it if we like.  We're just going to communicate with our MongoDB application from our local machine, but we're going to use the setup/tools that we would use communicating over a network.  The address of our MongoDB application is localhost:27017.  We'll use a connection string to connect from Scala : "mongodb://localhost:27017"

Our MongoDB application can contain multiple *databases*.  Each databsae in MongoDB can contain multiple *collections*.  Each collection in MongoDB contains multiple *documents*.  Documents are the level that contains actual data, like usernames, addresses, colors, fruits, ...
Collections are kind of like tables/relations in other DBs, with an important difference!  Out of the box, there's no restriction on what different types of documents can be placed in the same collection.  In a single collection we could put veery different types of documents.  We do have the option to place restrictions, if we want.

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
