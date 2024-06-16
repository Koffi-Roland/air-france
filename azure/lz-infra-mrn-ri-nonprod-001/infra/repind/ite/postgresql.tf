module "postgresql-ri" {
  source  = "terraform-awe.airfranceklm.com/afklm-ngdc-mvp/postgresql/azure"
  version = "~> 2.1.0"

  # These values need to be input by user:
  resource_group_name = data.azurerm_resource_group.rg_application.name
  application_name    = "repind"
  environment         = "ite"
  server_version      = 14
  compute_type        = "generalpurpose"
  compute_size        = "xsmall"
  storage_mb          = 32768


  custom_tags = {
    "application" = "repind"
  }
}
