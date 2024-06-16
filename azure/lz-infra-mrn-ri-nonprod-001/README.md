# Template repository for cloud computing infrastructure ![automation-2.0](https://img.shields.io/badge/MOVE2CLOUD-Automation%202.0-red)  ![automation-2.0](https://github.com/afkl-airspace/templates-infra-repository-automation-20/actions/workflows/event-on-create.yml/badge.svg)

This template contains Terraform examples and GitHub Workflow to manage your infrasctructure.
You can deploy services if you uncomment Terraform files and configure a workflow for your application.
All steps are describes here.

## Create a ticket for DevNet

You need to create a service request by asking this repository as template.
Be careful because the repository cannot be used directly.

This is a template GitHub repository, so you can create a repository in GitHub from it.
If you don't have any repository yet, you can go to the [Confluence Page](https://confluence.devnet.klm.com/display/NGDCTECH/Getting+Started+with+a+New+Application). Into this page, we explain how to open a ticket for DevNet.

Then you must follow the manual procedure to complete your specific context values.

## Define your values

### Reminder

This template could be used to start from scratch or to add a new application into an existing LZ.
In your cloned repository, you should have such tree (at least):

```
├── .github/
    ├── actions
    ├── workflows
    ├── PULL_REQUEST_TEMPLATE    
├── infra
    ├── _sharedservices
    ├── application_name
        ├── dev        
└── README.md
```

You can notice that:
- Under **_sharedservices**, all environments are at the same level. 
- Under **application_name**, all environments are in specific subdirectories. 

So you can potentially:
- Customize the shared services to create/update infrastructure.
- Create the infrastructure for a new application. Or update for an existing one.
It depends what you need but **always start by the shared services**.

### Define values for Shared services

1. First you must replace all occurences about these variables:
- replace **${{ values.zone }}** by your azure region : **weu** or **frc** :
    - all resources for KL applications should be in West Europe (**weu**).
    - all resources for AF should be into France Central (**frc**).
- replace **${{ values.businessUnit }}** by the value of your business unit (it, mrn, em...)
- replace **${{ values.team }}** by your product team name (example: teccse)

2. **Customize the [CODEOWNERS](.github/CODEOWNERS) file** : see [this documentation](https://confluence.devnet.klm.com/x/TAF_HQ)

3. You don't need to change the values into **.github/workflows/_sharedservices.yml**. The file can be used directly.

### Create your application

1. First duplicate the whole directory **applicationName** with the name of your application (ex: akevents, daisy...)

2. Now update one or many environments. By default, the env provided is **dev**.

3. Then you must replace all occurences about these variables into the files: 
- replace **${{ values.applicationName }}** by the name of your application.
- replace **${{ values.businessUnit }}** by the value of your business unit (it, mrn, em...)
- replace **${{ values.zone }}** by your azure region : **weu** or **frc** :
    - all resources for KL applications should be in West Europe (**weu**).
    - all resources for AF should be into France Central (**frc**).
 
4. Uncomment all needed resources because by default all resources for application part are commented.

5. Create a new file with the name of your application into **.github/workflows**. It is better to have separated files because all applications don't have the same environments.
By environment, you must copy/paste this 
- Production : [application_name-prod.yml](https://github.com/afkl-airspace/templates-infra-repository-automation-20/blob/main/.github/WORKFLOW_TEMPLATE/application_name-prod.yml)
- Non production : [application_name-nonprod.yml](https://github.com/afkl-airspace/templates-infra-repository-automation-20/blob/main/.github/WORKFLOW_TEMPLATE/application_name-nonprod.yml)

You need to configure the previous file by udpdating name of your application and the title of the workflow at least.

# Finalize the process before deployment

All steps are described into [Confluence Page](https://confluence.devnet.klm.com/display/NGDCTECH/Getting+Started+with+a+New+Application)

# Monitoring

You have to use [terraform-azure-alerts](https://github.com/afkl-airspace/terraform-azure-alerts) module provided by Monitoring team to define custom Azure Alerts and Action Groups on your shared services or applications.

Consult [terraform-azure-alerts Documentation](https://github.com/afkl-airspace/terraform-azure-alerts) for usage and examples.

# References

In case of problem, you can always watch the reference project Akevents in non production
- Infrastructure : https://github.com/afkl-airspace/lz-infra-it-teccse-nonprod-002 
- Landing zone : https://github.com/afkl-airspace/lz-management-it-teccse-nonprod-002/blob/main/landing-zone.yml

# Question and support

Check our [FAQ](https://confluence.devnet.klm.com/pages/viewpage.action?pageId=517355850) or ask support in [Service Desk](https://jira.devnet.klm.com/servicedesk/customer/portal/86/create/3966)

