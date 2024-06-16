module "cosmosdb-ri" {
  source  = "terraform-awe.airfranceklm.com/afklm-ngdc-mvp/cosmosdb/azure"
  version = "~> 2.0.0"

  # These values need to be input by user:
  resource_group_name           = data.azurerm_resource_group.rg_application.name
  environment                   = "ite"
  target_api                    = "core"
  capabilities_core             = ["EnableServerless"]
  is_serverless                 = true
  local_authentication_disabled = false
  nonprod_backup_type           = "Continuous"


  custom_tags = {
    "application" = "repind"
  }
}
