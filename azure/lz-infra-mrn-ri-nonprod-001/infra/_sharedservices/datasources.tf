module "lzdatasource" {
  source  = "terraform-awe.airfranceklm.com/afkl-airspace/lzdatasource/azure"
  version = "~>1.2.3"
}


data "azurerm_resource_group" "rg_sharedservices" {
  name = "rg-ri-app-sharedservices-nonprod-frc-001"
}
