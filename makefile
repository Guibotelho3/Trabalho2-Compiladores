# Diretórios
SRC_DIR = langFramework/lang
BIN_DIR = bin
TOOLS_DIR = tools

# Arquivos
CUP_RUNTIME_JAR = $(TOOLS_DIR)/java-cup-11b-runtime.jar
JFLEX_JAR = $(TOOLS_DIR)/jflex.jar

# Definir separador de classpath dinamicamente
ifeq ($(OS),Windows_NT)
    CLASSPATH_SEP = ;
    RM = del /s /q
    RMDIR = rmdir /s /q
    MKDIR = if not exist $(BIN_DIR) mkdir $(BIN_DIR)
    FIND_JAVA = $(wildcard $(SRC_DIR)/**/*.java)
else
    CLASSPATH_SEP = :
    RM = rm -rf
    RMDIR = rm -rf
    MKDIR = mkdir -p $(BIN_DIR)
    FIND_JAVA = $(shell find $(SRC_DIR) -name "*.java")
endif

# Alvo padrão
all: compile

# Compilação
compile: $(SRC_DIR)/parser/LangLexer.java $(SRC_DIR)/parser/LangParser.java
	@$(MKDIR)
	@javac -cp .$(CLASSPATH_SEP)$(CUP_RUNTIME_JAR) -d $(BIN_DIR) $(FIND_JAVA)

# Geração do parser
$(SRC_DIR)/parser/LangParser.java: $(SRC_DIR)/parser/lang.cup
	@java -jar $(TOOLS_DIR)/java-cup-11b.jar -destdir $(SRC_DIR)/parser $(SRC_DIR)/parser/lang.cup

# Geração do lexer
$(SRC_DIR)/parser/LangLexer.java: $(SRC_DIR)/parser/lang.flex
	@java -jar $(JFLEX_JAR) -nobak -d $(SRC_DIR)/parser $(SRC_DIR)/parser/lang.flex

# Limpeza
clean: cleanClasses cleanParser

cleanClasses:
	-@$(RMDIR) $(BIN_DIR) 2> NUL || echo "Diretorio $(BIN_DIR) foi limpo."

cleanParser:
	-@$(RM) $(SRC_DIR)/parser/LangLexer.java 2> NUL
	-@$(RM) $(SRC_DIR)/parser/LangParser.java 2> NUL
	-@$(RM) $(SRC_DIR)/parser/LangParserSym.java 2> NUL
