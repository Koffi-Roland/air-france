#include $(ORACLE_HOME)/precomp/lib/env_precomp.mk
include /exploit/oracle/client/12102/precomp/lib/env_precomp.mk

CC=/opt/SUNWspro/SC4.2/bin/cc -g -I. 

#CC=/opt/SUNWspro/SC4.2/bin/cc -g -I. -Xa  -xstrconst -xF  -mr -xarch=v8  -xchip=ultra -D_REENTRANT -K PIC -DPRECOMP  -I.  -DSLMXMX_ENABLE -DSLTS_ENABLE -D_SVID_GETTOD 

.SUFFIXES: .pc .c .o

LDSTRING=
PRODUCT_LIBHOME=
MAKEFILE=proc.mk
PROCPLSFLAGS=  dbms=v8 HOLD_CURSOR=YES RELEASE_CURSOR=NO MAXOPENCURSORS=200
PROCPPFLAGS= code=cpp $(CCPSYSINCLUDE)
USERID= tacticaf/tacticaf
INCLUDE=$(I_SYM). $(PRECOMPPUBLIC)

OBJS= dynamicsql.o

.pc.c:
	$(PROC) $(PROCFLAGS) iname=$*.pc

.pc.o:
	$(PROC) $(PROCFLAGS) iname=$*.pc
	$(CC) $(CFLAGS) -c $*.c

.c.o:
	$(CC) $(CFLAGS) -c $*.c


dynamicsql.o: proc/dynamicsql.pc
	$(PROC) $(PROCPLSFLAGS) iname=proc/$*.pc oname=$*.c
	$(CC) $(CFLAGS) $(PRECOMPPUBLIC) -c proc/$*.c
#	$(CC) $(PRECOMPPUBLIC) -c proc/$*.c
	@mv -f dynamicsql.o o/
	@cp -f o/dynamicsql.o ../o


