echo "Iniciando pruebas"
cd /tmp && ls
echo "Obteniendo dependencias"
mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
echo "Ejecutar pruebas selenium"
mvn test