print("Executing mongo-init.js script...");

db.auth('root', 'rootpass')

db = db.getSiblingDB('ies')

db.createCollection('dummy');


db.createUser(
        {
            user: "spring",
            pwd: "springpass",
            roles: [
                {
                    role: "readWrite",
                    db: "ies"
                }
            ]
        }
);

print("Script execution completed.");
