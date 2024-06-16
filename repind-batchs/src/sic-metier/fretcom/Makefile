include makefileHeaderOutils

SANS_EXE=1

OLD_VERSION=7.3.4
OLD_ORACLE_HOME=$(shell dirname $(ORACLE_HOME))/$(OLD_VERSION)

# compil des outils et de l'interface Oracle avec le niveau courant d'Oracle et le precedent 
ALL:
	$(MAKE) -f Makefile current

# compil du niveau courant (base sur ORACLE_HOME du user) des outils et de l'interface Oracle
# --> generation des niveaux _dbg et _fast
current:
	$(MAKE) -f ./Makefile.TOOLS "PAR=_fast" clean; $(MAKE) -f ./Makefile.TOOLS "PAR=_fast" lib
	$(MAKE) -f ./Makefile.TOOLS "PAR=_dbg" clean; $(MAKE) -f ./Makefile.TOOLS "PAR=_dbg" lib
	$(MAKE) -f Makefile.PROC++ "PAR=_fast" clean; $(MAKE) -f Makefile.PROC++ "PAR=_fast" lib
	$(MAKE) -f Makefile.PROC++ "PAR=_dbg" clean; $(MAKE) -f Makefile.PROC++ "PAR=_dbg" lib

include $(ROOT_MAKEFILE_NOYAU)/Makefile.noyau

install:
	cp $(LIBDIR)/lib*.so $(APP_LIVRAISON_OUTILS)/$(LIBDIR)
