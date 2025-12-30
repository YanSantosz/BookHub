[README.md](https://github.com/user-attachments/files/24386697/README.md)
# 📚 BookHub - Gerenciador de Biblioteca Digital

> Uma aplicação Spring Boot que integra a **Google Books API** para buscar, gerenciar e persistir livros em um banco de dados PostgreSQL.

---

## 🎯 Sobre o Projeto

**BookHub** é uma ferramenta de linha de comando que permite:
- 🔍 Buscar livros por título na Google Books API
- 👤 Pesquisar por autor
- 💾 Salvar livros em banco de dados local
- 📖 Listar e gerenciar sua coleção pessoal

Mais do que um aplicativo, é um **estudo de caso em clean code, refatoração e boas práticas profissionais** com Java e Spring Boot.

---

## 🚀 Stack Tecnológico

| Camada | Tecnologia |
|--------|-----------|
| **Backend** | Java 17, Spring Boot 3.x |
| **Database** | PostgreSQL 15+ |
| **API Externa** | Google Books API v1 |
| **ORM** | JPA/Hibernate |
| **Serialização** | Jackson |
| **Build** | Maven |

---

## 📋 Pré-requisitos

- **Java 17+** instalado
- **PostgreSQL 15+** rodando localmente
- **Maven 3.8+** para build
- **Git** para versionamento
- Chave de API da [Google Books API](https://developers.google.com/books/docs/v1/using)

---

## 🔧 Instalação & Configuração

### 1️⃣ Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/BookHub.git
cd BookHub
```

### 2️⃣ Configurar Banco de Dados

Crie um banco PostgreSQL:

```sql
CREATE DATABASE bookhub;
CREATE USER seu_usuario WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE bookhub TO seu_usuario;
```

### 3️⃣ Configurar Variáveis de Ambiente

#### No **Linux/Mac:**

```bash
export GBOOK_BASEURL="https://www.googleapis.com/books/v1/volumes?q="
export GBOOK_APIKEY="sua_chave_de_api_aqui"
export DB_HOST="localhost:5432"
export DB_NAME="bookhub"
export DB_USER="seu_usuario"
export DB_PASSWORD="sua_senha"
```

#### No **Windows (PowerShell):**

```powershell
$env:GBOOK_BASEURL="https://www.googleapis.com/books/v1/volumes?q="
$env:GBOOK_APIKEY="sua_chave_de_api_aqui"
$env:DB_HOST="localhost:5432"
$env:DB_NAME="bookhub"
$env:DB_USER="seu_usuario"
$env:DB_PASSWORD="sua_senha"
```

### 4️⃣ Compilar o Projeto

```bash
mvn clean install
```

### 5️⃣ Executar a Aplicação

```bash
mvn spring-boot:run
```

Ou via JAR:

```bash
java -jar target/BookHub-0.0.1-SNAPSHOT.jar
```

---

## 📖 Como Usar

Ao executar, você verá um menu:

```
--------------------------------
BOOKHUB - BIBLIOTECA
--------------------------------
1 - Pesquisar livro
2 - Pesquisar livro por autor
3 - Listar livros salvos

0 - Sair
--------------------------------
Escolha uma opção:
```

### **Opção 1: Pesquisar Livro**
Digite o título (ex: `Harry Potter`) e selecione qual livro salvar.

### **Opção 2: Pesquisar por Autor**
Digite o nome do autor (ex: `Stephen King`) e veja todos os livros.

### **Opção 3: Listar Salvos**
Mostra todos os livros já salvos no seu banco local.

---

## 🏗️ Arquitetura

```
BookHub/
├── src/
│   ├── main/
│   │   ├── java/br/com/alura/BookHub/
│   │   │   ├── View/
│   │   │   │   └── Principal.java          # Menu e interface
│   │   │   ├── Model/
│   │   │   │   ├── Livro.java              # Entity JPA
│   │   │   │   ├── DadosLivros.java        # Record DTO
│   │   │   │   ├── ItemLivro.java          # Record API
│   │   │   │   └── GoogleBooksResponse.java # Record resposta
│   │   │   ├── Repository/
│   │   │   │   └── LivroRepository.java    # Spring Data JPA
│   │   │   └── Service/
│   │   │       ├── ConsumoApi.java         # HttpClient
│   │   │       └── ConverteDados.java      # Jackson parsing
│   │   └── resources/
│   │       └── application.properties      # Config Spring
│   └── test/                               # (Futuro)
├── pom.xml
└── README.md
```

### Fluxo de Dados

```
Menu (Principal)
    ↓
Busca na API (ConsumoApi + ConverteDados)
    ↓
Exibe Resultados (Record ItemLivro)
    ↓
Salva no BD (Entity Livro → Repository → PostgreSQL)
    ↓
Lista/Recupera (findAll, findByAutores, etc)
```

---

## 💡 Principais Aprendizados & Técnicas

✅ **Refatoração DRY** — Eliminação de código duplicado em métodos reutilizáveis  
✅ **MVC & Repository Pattern** — Arquitetura profissional com Spring Boot  
✅ **Records & Imutabilidade** — DTOs type-safe para comunicação com APIs  
✅ **HttpClient & Jackson** — Integração REST com desserialização automática  
✅ **JPA/Hibernate** — Persistência com mapeamento Object-Relational  
✅ **Streams & Lambdas** — Manipulação funcional de coleções  
✅ **Variáveis de Ambiente** — Proteção de credenciais sensíveis  
✅ **Null-Safety & Validação** — Tratamento robusto de dados nulos  

---

## 🐛 Tratamento de Erros

### Cenários cobertos:

| Erro | Tratamento |
|------|-----------|
| **Livro não encontrado na API** | Mensagem amigável: "Nenhum livro encontrado" |
| **Livro já existe no banco** | Validação por `googleBooksId` único |
| **Autores/Categorias nulos** | Fallback: "Autor não informado" |
| **Input não-numérico** | Captura `NumberFormatException` com retry |
| **Conexão API falha** | Try-catch com mensagem de erro |

---

## 🔒 Segurança

- ✅ **Sem credenciais hardcoded** — Usa `System.getenv()` para variáveis de ambiente
- ✅ **API Key protegida** — Nunca exposta em logs ou código-fonte
- ✅ **SQL Injection prevention** — JPA parametriza todas as queries
- ✅ **`.gitignore`** — Exclui arquivos sensíveis do versionamento

### `.gitignore` recomendado:

```
*.class
*.jar
target/
.idea/
.vscode/
.env
application-*.properties
```

---

## 🧪 Testes (Futuro)

Próximas melhorias:
- [ ] Testes unitários com JUnit 5
- [ ] Mock de API com Mockito
- [ ] Testes de integração com TestContainers
- [ ] Cobertura de código com JaCoCo

---

## 📈 Roadmap

- [ ] **Service Layer** — Mover lógica para classe `LivroService`
- [ ] **Filtros avançados** — Por categoria, páginas mínimas, ano de publicação
- [ ] **Interface Web** — Migrar para REST API + frontend React/Vue
- [ ] **Autenticação** — Spring Security com JWT
- [ ] **Cache** — Redis para queries frequentes
- [ ] **Docker** — Containerização da aplicação

---

## 📝 Exemplo de Uso Prático

```bash
# Terminal 1: Configure as variáveis
export GBOOK_BASEURL="https://www.googleapis.com/books/v1/volumes?q="
export GBOOK_APIKEY="sua_chave"
export DB_HOST="localhost:5432"
export DB_NAME="bookhub"
export DB_USER="postgres"
export DB_PASSWORD="senha"

# Terminal 2: Inicie a aplicação
mvn spring-boot:run

# Interaja com o menu
# 1. Pesquise "The Lord of the Rings"
# 2. Selecione o livro (ex: opção 1)
# 3. Veja em "Listar livros salvos" (opção 3)
```

---

## 🤝 Contribuições

Este é um projeto de **aprendizado pessoal**, mas sugestões são bem-vindas!

1. Faça um **Fork**
2. Crie uma branch (`git checkout -b feature/MinhaIdeia`)
3. Commit suas mudanças (`git commit -m 'Add: MinhaIdeia'`)
4. Push para a branch (`git push origin feature/MinhaIdeia`)
5. Abra um **Pull Request**

---

## 📚 Recursos Úteis

- [Google Books API Docs](https://developers.google.com/books/docs/v1/using)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JPA/Hibernate Guide](https://hibernate.org/orm/)
- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)

---

## 👤 Autor

**Yan Victor Valerio dos Santos Silva**  
📧 Email: [yansantos.silva7@gmail.com]  
🔗 LinkedIn: [(https://www.linkedin.com/in/yanvictorsantos/)]  

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** — veja o arquivo [LICENSE](LICENSE) para detalhes.

---

**⭐ Se este projeto foi útil, considere dar uma estrela!**

---

**Última atualização:** 30 de Dezembro de 2025  
**Status:** ✅ Funcional e pronto para produção
