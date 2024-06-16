########################################
# http://dinb.airfrance.fr/outils/make #
########################################

# WORKSHOP_VERS = 6.1

# uses Pro*C interface
USE_ORA=1

# because of oracle bug between versions
# PROCFLAGS += prefetch=0 THREADS=YES
PROCFLAGS += prefetch=0
PROCFLAGS += sys_include=/usr/lib/gcc/x86_64-redhat-linux/4.8.2/include

# CFLAGS += -DSQLCA_NONE
# if you need traces
#CFLAGS += -DDEBUG_TRACE

# flag to stop linking if the lib is not self-contained
# APP_LDFLAGS += " -z defs" pas pris en compte
# OK :
# LIB_STRICT_DEPS = 1
# APP_LDFLAGS += -lCstd -lCrun 
# /exploit/ORACLE/server/9.2.0/lib32/libclntsh.so
# missing -lCstd -lCrun

# debug
DEBUG_APP = 1
USE_FAST = 0

SANS_EXE=1

APPL = SelectMany
# APPL_VERS=1.1
# test for scgp
APPL_VERS=1.2

# delivery directory
#APP_LIVRAISON_LIB=/OUTILS/SelectMany
APP_LIVRAISON_LIB=/var/tmp/SelectMany

api6.1 :
	echo "ORACLE_HOME='$(ORACLE_HOME)'"
	WORKSHOP_VERS=6.1 $(MAKE) lib

# dev :
api8.0 :
	echo "ORACLE_HOME='$(ORACLE_HOME)'"
	WORKSHOP_VERS=8.0 $(MAKE) lib
api11 :
	echo "ORACLE_HOME='$(ORACLE_HOME)'"
	WORKSHOP_VERS=11 $(MAKE) lib

cleanallliv :
	$(MAKE) clean
	$(RM) -f $(OBJDIR)/dynamicsql.c
	$(RM) -f $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS)/lib$(APPL).so.$(APPL_VERS)
	$(RM) -rf $(APP_LIVRAISON_LIB)/include_$(APPL_VERS)

api6.1ora8clean :
	WORKSHOP_VERS=6.1 ORACLE_VERS=8.1.7 $(MAKE) cleanallliv

api8.0ora8clean :
	WORKSHOP_VERS=8.0 ORACLE_VERS=8.1.7 $(MAKE) cleanallliv

api6.1ora8 :
	WORKSHOP_VERS=6.1 ORACLE_VERS=8.1.7 $(MAKE) api_ora8

# prod only
# dev : use api8.0
api8.0ora8 :
	WORKSHOP_VERS=8.0 ORACLE_VERS=8.1.7 $(MAKE) api_ora8

api_ora8 :
	$(MAKE) lib
	if [ ! -d $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS) ]; \
	       then mkdir -p $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS); fi
	$(CP) $(LIBDIR)/lib$(APPL).so \
	       $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS)/lib$(APPL).so.$(APPL_VERS)
	if [ -d $(APP_LIVRAISON_LIB)/include_$(APPL_VERS) ]; then $(RM) -rf $(APP_LIVRAISON_LIB)/include_$(APPL_VERS) ; fi
	mkdir -p $(APP_LIVRAISON_LIB)/include_$(APPL_VERS)
	$(CP) -rf $(INCDIR)/*.h $(APP_LIVRAISON_LIB)/include_$(APPL_VERS)

api6.1ora9 :
	WORKSHOP_VERS=6.1 ORACLE_VERS=9.2.0 $(MAKE) api_ora9

api8.0ora9 :
	WORKSHOP_VERS=8.0 ORACLE_VERS=9.2.0 $(MAKE) api_ora9

api6.1ora9clean :
	WORKSHOP_VERS=6.1 ORACLE_VERS=9.2.0 $(MAKE) cleanallliv

api8.0ora9clean :
	WORKSHOP_VERS=8.0 ORACLE_VERS=9.2.0 $(MAKE) cleanallliv

api_ora9 :
	$(MAKE) lib
	if [ ! -d $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS) ]; \
	       then mkdir -p $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS); fi
	$(CP) $(LIBDIR)/lib$(APPL).so \
	       $(APP_LIVRAISON_LIB)/$(WORKSHOP_VERS)/$(ORACLE_VERS)/lib$(APPL).so.$(APPL_VERS)
	if [ -d $(APP_LIVRAISON_LIB)/include_$(APPL_VERS) ]; then $(RM) -rf $(APP_LIVRAISON_LIB)/include_$(APPL_VERS) ; fi
	mkdir -p $(APP_LIVRAISON_LIB)/include_$(APPL_VERS)
	$(CP) -rf $(INCDIR)/*.h $(APP_LIVRAISON_LIB)/include_$(APPL_VERS)

cleanall :
	$(MAKE) clean
	$(RM) -f $(OBJDIR)/dynamicsql.c

include $(ROOT_MAKEFILE_NOYAU)/Makefile.noyau
