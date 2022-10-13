Copy-Item ./../../prog-intro-tests/java/markup/*.java ./../java-solutions/markup/
javac -sourcepath ../java-solutions/ -d ../out ../java-solutions/markup/*.java
javac -encoding utf8 -sourcepath ./../../prog-intro-tests/java/ -d ../out ./../../prog-intro-tests/java/markup/*.java
cd ../out
java -ea markup.MarkupTest Base