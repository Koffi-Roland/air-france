/******************************************************************************
********************************* filesec.c ***********************************
*******************************************************************************
/******************************************************************************
******************************** Include Files ********************************
******************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*****************************************************************************
************************* Private Type Definitions  **************************
******************************************************************************/
#define USERID_LENGTH           20
#define PASSWORD_LENGTH         20
#define DB_NAME_LENGTH          40
typedef unsigned long  STATUS;
typedef struct tagSECURITY
{
  char UserID   [ USERID_LENGTH   + 1 ];
  char Password [ PASSWORD_LENGTH + 1 ];
  char Database [ DB_NAME_LENGTH  + 1 ];
	
} SECURITY;
/*****************************************************************************
****************************** Private Globals *******************************
******************************************************************************/
static const char *CryptKey = "Ohhhh! I'm a lumberjack and I'm OK, "
			"I sleep all night and I work all day. "
			"I chop down trees, I skip and jump, "
			"I like to press wild flowers.";
static SECURITY Login;
/******************************************************************************
***************************** Private Prototypes ******************************
******************************************************************************/
static STATUS Cryptage( void *, long );
int main (int argc, char **argv)
{
  char format[20];
  char *Login_Buffer;
  FILE *fptr;
  const char* ffichier = argv[1];
  if ( ! strcmp(argv[2],"READ" ) || ! strcmp(argv[2],"WRITE" )) {
	fprintf(stdout, "\n");
	fprintf(stdout, "Cryptage et DeCryptage des fichiers "
		  "de securite du SIC !\n");
  }
  if (argc != 3)
  {
    fprintf(stdout, "USAGE: %s <security filename (with path)> READ|READSIC|WRITE\n",argv[0]);
    return( -1 );
  }
  if ( ! strcmp(argv[2],"WRITE" ))
  {
  if((fptr = fopen(ffichier, "w")) == (FILE *)NULL)
  {
    fprintf(stdout, "Invalid filename, or unable to write in "
		    "specified location.\n");
    return( -1 );
  }
  fclose(fptr);
  memset(&Login, '\t', sizeof(SECURITY));
  fprintf(stdout, "      Enter User ID > ");
  sprintf(format, "%%%ds", USERID_LENGTH);
  fscanf(stdin, format, &Login.UserID);
  fprintf(stdout, "     Enter Password > ");
  sprintf(format, "%%%ds", PASSWORD_LENGTH);
  fscanf(stdin, format, &Login.Password);
  fprintf(stdout, "Enter Database Name > ");
  sprintf(format, "%%%ds", DB_NAME_LENGTH);
  fscanf(stdin, format, &Login.Database);
  Cryptage(&Login, sizeof(SECURITY));
  fptr = fopen(ffichier, "w");
  fwrite(&Login, sizeof(SECURITY), 1L, fptr);
  fclose(fptr);
  fprintf(stdout, "\nSecurity File Created -> %s\n", argv[1]);
  return(0);
  }
  if ( ! strcmp(argv[2],"READ" ) || ! strcmp(argv[2],"READSIC" ))
  {
  if((fptr = fopen(ffichier, "r")) == (FILE *)NULL)
  {
    fprintf(stdout, "Failed to open security file : %s!\n",ffichier);
    return( -1 );
  }
  Login_Buffer = (char *)calloc( (size_t)1, sizeof(SECURITY) );
  if( Login_Buffer == (char *)NULL )
  {
    fclose( fptr );
    fprintf(stdout, "Unable to allocate temp buffer for security file !\n");
    return( -1 );
  }
  if( fread( Login_Buffer, sizeof(SECURITY), (size_t)1, fptr) != (size_t)1 )
  {
    fclose(fptr);
    fprintf(stdout, "Incorrect number of bytes read from security file !\n");
    return( -1 );
  } 
  memcpy( &Login, Login_Buffer, sizeof(SECURITY) );
  free( Login_Buffer );
  fclose(fptr);
  Cryptage(&Login, sizeof(SECURITY));
  if (! strcmp(argv[2],"READ" )) {
	fprintf(stdout, "\nUserID   : %s", Login.UserID);
	fprintf(stdout, "\nPassword : %s", Login.Password);
	fprintf(stdout, "\nDatabase : %s\n", Login.Database);
  } else {
	fprintf(stdout, "%s/%s@%s", Login.UserID,Login.Password,Login.Database);	
  }
  return(0);
  }
}
/******************************************************************************
******************************* Cryptage() ************************************
*******************************************************************************/
static STATUS Cryptage( void *Buffer, long Length )
{
  long i = 0;
  long j = 0;
  if(Buffer == (char *)NULL)
  {
    fprintf(stderr, "Buffer = (char *)NULL");
    return( -1 );
  }
  while ( i < Length )
    for ( j = 0; i < Length && j < strlen(CryptKey); i++, j++ )
      *((char *)Buffer + i) ^= *(CryptKey + j);
  return(0);
}
