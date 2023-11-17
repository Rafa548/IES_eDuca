import sys
import mysql.connector

mydb = mysql.connector.connect(
    port=3306,
    user="spring",
    passwd="springpass",
)

print("Connection successful!")
print(mydb)

mycursor = mydb.cursor()

#show tables in database
databases = []
mycursor.execute("SHOW DATABASES")
for x in mycursor:
    databases.append(x[0])
print(databases[1])

#show tables in database
tables = []
if "ies" in databases:
    mycursor.execute("USE ies")
    mycursor.execute("SHOW TABLES")
    for x in mycursor:
        tables.append(x[0])
    print(tables)

    if "users" not in tables:
        mycursor.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255))")
        print("Table users created!")

    if "sys_admin" not in tables:
        mycursor.execute("CREATE TABLE sys_admin (id INT AUTO_INCREMENT PRIMARY KEY, FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE)")
        print("Table sys_admin created!")

    if "school_admin" not in tables:
        mycursor.execute("CREATE TABLE school_admin (id INT AUTO_INCREMENT PRIMARY KEY, FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE)")
        print("Table school_admin created!")

    if "escolas" not in tables:
        mycursor.execute("CREATE TABLE escolas (nome VARCHAR(255) PRIMARY KEY, admin INT, FOREIGN KEY (admin) REFERENCES school_admin(id) ON DELETE CASCADE)")
        print("Table escolas created!")

    if "turmas" not in tables:
        mycursor.execute("CREATE TABLE turmas (id INT PRIMARY KEY, nome VARCHAR(255), escola VARCHAR(255), FOREIGN KEY (escola) REFERENCES escolas(nome) ON DELETE CASCADE)")
        print("Table turmas created!")

    if "alunos" not in tables:
        mycursor.execute("CREATE TABLE alunos (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(255), turma INT, FOREIGN KEY (turma) REFERENCES turmas(id) ON DELETE CASCADE)")
        print("Table alunos created!")

    if "professores" not in tables:
        mycursor.execute("CREATE TABLE professores (id INT AUTO_INCREMENT PRIMARY KEY, escola VARCHAR(255), FOREIGN KEY (escola) REFERENCES escolas(nome) ON DELETE CASCADE)")
        print("Table professores created!")
    
    if "disciplinas" not in tables:
        mycursor.execute("CREATE TABLE disciplinas (nome VARCHAR(255) PRIMARY KEY)")
        print("Table disciplinas created!")
    
    if "disciplinas_professores" not in tables:
        mycursor.execute("CREATE TABLE disciplinas_professores (disciplina VARCHAR(255), professor_id INT, FOREIGN KEY (disciplina) REFERENCES disciplinas(nome) ON DELETE CASCADE, FOREIGN KEY (professor_id) REFERENCES professores(id) ON DELETE CASCADE)")
        print("Table disciplinas_professores created!")

    if "disciplinas_turmas" not in tables:
        mycursor.execute("CREATE TABLE disciplinas_turmas (disciplina VARCHAR(255), turma INT, FOREIGN KEY (disciplina) REFERENCES disciplinas(nome) ON DELETE CASCADE, FOREIGN KEY (turma) REFERENCES turmas(id) ON DELETE CASCADE)")
        print("Table disciplinas_turmas created!")


    # Inserts

    """ mycursor.execute("INSERT INTO users (name, email, password) VALUES (%s, %s, %s)", ("João", "joao@gmail", "123"))
    mycursor.execute("INSERT INTO users (name, email, password) VALUES (%s, %s, %s)", ("Maria", "maria@gmail", "123"))
    mycursor.execute("INSERT INTO users (name, email, password) VALUES (%s, %s, %s)", ("José", "jose@gmail", "123"))
    mycursor.execute("INSERT INTO users (name, email, password) VALUES (%s, %s, %s)", ("Ana", "ana@gmail", "123"))

    mycursor.execute("INSERT INTO sys_admin (id) VALUES (%s)", ([1]))

    mycursor.execute("INSERT INTO escolas (nome, admin) VALUES (%s, %s)", ("Anadia", 2))
    mycursor.execute("INSERT INTO escolas (nome, admin) VALUES (%s, %s)", ("Aveiro", 3))

    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (1, "1º ano", "Anadia"))
    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (2, "2º ano", "Anadia"))
    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (3, "3º ano", "Anadia"))
    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (4, "1º ano", "Aveiro"))
    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (5, "2º ano", "Aveiro"))
    mycursor.execute("INSERT INTO turmas (id, nome, escola) VALUES (%s, %s, %s)", (6, "3º ano", "Aveiro"))

    mycursor.execute("INSERT INTO alunos (nome, turma) VALUES (%s, %s)", ("João", 1))
    mycursor.execute("INSERT INTO alunos (nome, turma) VALUES (%s, %s)", ("Maria", 1))
    mycursor.execute("INSERT INTO alunos (nome, turma) VALUES (%s, %s)", ("José", 1))
    mycursor.execute("INSERT INTO alunos (nome, turma) VALUES (%s, %s)", ("Ana", 2))

    mycursor.execute("INSERT INTO professores (escola) VALUES (%s)", ("Anadia"))
    mycursor.execute("INSERT INTO professores (escola) VALUES (%s)", ("Aveiro"))

    mycursor.execute("INSERT INTO disciplinas (nome) VALUES (%s)", ("Matemática"))
    mycursor.execute("INSERT INTO disciplinas (nome) VALUES (%s)", ("Português"))
    mycursor.execute("INSERT INTO disciplinas (nome) VALUES (%s)", ("Inglês"))

    mycursor.execute("INSERT INTO disciplinas_professores (disciplina, professor_id) VALUES (%s, %s)", ("Matemática", 1))
    mycursor.execute("INSERT INTO disciplinas_professores (disciplina, professor_id) VALUES (%s, %s)", ("Português", 1))
    mycursor.execute("INSERT INTO disciplinas_professores (disciplina, professor_id) VALUES (%s, %s)", ("Inglês", 2))

    mycursor.execute("INSERT INTO disciplinas_turmas (disciplina, turma) VALUES (%s, %s)", ("Matemática", 1))
    mycursor.execute("INSERT INTO disciplinas_turmas (disciplina, turma) VALUES (%s, %s)", ("Português", 1))
    mycursor.execute("INSERT INTO disciplinas_turmas (disciplina, turma) VALUES (%s, %s)", ("Inglês", 2)) """







 
    

    
