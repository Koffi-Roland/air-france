terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~>3.64.0"
    }
    azapi = {
      source  = "azure/azapi"
      version = "~>1.4.0"
    }
  }
  cloud {
    hostname     = "terraform-awe.airfranceklm.com"
    organization = "afkl-airspace"
    workspaces {
      name = "ws-azure-mrn-ri-nonprod-001-cosmosNoSQL-ite"
    }
  }
}

provider "azurerm" {
  subscription_id    = var.subscription_id
  client_id          = var.client_id
  tenant_id          = var.tenant_id
  use_oidc           = true
  oidc_request_token = var.oidc_request_token
  oidc_request_url   = var.oidc_request_url
  features {}
}

provider "azapi" {
  subscription_id    = var.subscription_id
  client_id          = var.client_id
  tenant_id          = var.tenant_id
  oidc_request_token = var.oidc_request_token
  oidc_request_url   = var.oidc_request_url
  use_oidc           = true
}

variable "subscription_id" {}
variable "tenant_id" {}
variable "client_id" {}

variable "oidc_request_token" {}
variable "oidc_request_url" {}
