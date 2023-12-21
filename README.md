# IES_eDuca: Setup Guide

## Execution Guidelines

Ensure Docker is installed and operational on your system before proceeding.

---

### 1. Start Services

#### a. `eDuca_dataGen` Directory:

```bash
cd eDuca_dataGen
docker-compose up -d

```

#### b. `eDuca_db` Directory:
In the eDuca_db directory:
```bash
docker-compose up -d
```
### 2. Setup Data Generation

Navigate to the eDuca_dataGen directory to execute the Python Script:

```bash
cd eDuca_dataGen
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python GenerateData.py
```

### 3. Run Application (eDucaApp->SpringBoot)

In the eDucaApp directory:

```bash
./mvnw spring-boot:run
```

### 4. Run FrontEnd (React)
 
In the frontend directory:

```bash
npm start
```