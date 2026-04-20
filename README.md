# EditorAdmin

## Run

1. Configure environment variables if needed:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/paper_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-password"
$env:JWT_SECRET="your-jwt-secret"
```

2. Start backend:

```powershell
mvn spring-boot:run
```

3. Open:

- Frontend: `http://localhost:2008/`
- Admin: `http://localhost:2008/admin`

## Frontend Dev

```powershell
cd frontend
npm install
npm run dev
```

Open `http://127.0.0.1:5173/`.
