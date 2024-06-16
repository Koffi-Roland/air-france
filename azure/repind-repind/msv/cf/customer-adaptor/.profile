#! /bin/bash
# Get service
SERVICE=$(echo "$VCAP_SERVICES" | jq '."user-provided"[] | select(.name=="como-appender")')

# Extract secrets
KEYTAB_ENCODED=$(echo "$SERVICE" | jq '.credentials.keytab' -r)
JAAS_CONFIG=$(echo "$SERVICE" | jq '.credentials.config' -r)
KRB5=$(echo "$SERVICE" | jq '.credentials.krb5' -r)

# Place secrets on disk
if [ "$KEYTAB_ENCODED" == "null" ] || [ "$JAAS_CONFIG" == "null" ]
then
  echo "[WARNING] The keytab and/or jaas configuration couldn't be found. The logger will not be able to ship the logs to Como."
else
  echo "$KEYTAB_ENCODED" | base64 -d > "$TMPDIR"/app.keytab
  echo "$JAAS_CONFIG" | base64 -d > "$TMPDIR"/jaas.config
fi

if [ "$KRB5" == "null" ]
then
  echo "KRB5 configuration not provided, assuming AF mode."
else
  echo "KRB5 configuration was provided, assuming KLM mode and creating krb5.conf file"
  echo "$KRB5" | base64 -d > "$TMPDIR"/krb5.config
fi