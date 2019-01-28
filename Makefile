JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = \
	city.java\
	Path.java \
	HillClimbing.java


	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

