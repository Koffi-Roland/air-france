#!/bin/bash

# Clean dependencies
rm -f ~/prod/bin/java/lib/ref-commons-0.1.0-*.jar
rm -f ~/prod/bin/java/lib/sic-commons-0.1.0-*.jar
rm -f ~/prod/bin/java/lib/sic-entities-0.1.0-*.jar
rm -f ~/prod/bin/java/lib/sic-payment-preferences-commons-*.jar
rm -f ~/prod/bin/java/lib/sicutf8-commons-0.1.0-*.jar
rm -f ~/prod/bin/java/lib/tables_referencesJAVA-0.1.0-*.jar

#Create appropriate folder for SIC repository
rm -rf ~/livraison/bin/java
mkdir -p ~/livraison/bin/java
mkdir -p ~/livraison/bin/java/lib
mkdir -p ~/livraison/bin/java/tools
mkdir -p ~/livraison/bin/java/config

#Move jar and sh to parent java bin folder
mv ~/livraison/bin/sic/java/* ~/livraison/bin/java/
mv ~/livraison/bin/sic/scripts/* ~/livraison/bin/java/

#Remove SIC application folder used for delivery
rm -rf ~/livraison/bin/sic

exit 0