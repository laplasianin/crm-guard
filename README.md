This is a CRM for internal needs which can be usefull for almost any company.

It improves work with clients by operators and managers.

Quick explanation of hierarchy:
There are company using CRM. It has several funds (at least 1). 
It's customers can be either individual or company and can have 0..infinite contact persons with full name, phone numbers etc.
Each customer can have 0..infinite contracts with any fund. Each contract have invoice and payment history. 

You can check full schema in sql files CRM.sql and security.sql (written for MySql 5 innoDB) 

CRM users can be operators, managers or administrators.

Operators can see customers list, customer details, total debt, list of payments and invoices. Operator can perform some actions:
- Take operator to work
- Print contract, applications (implemented using Jasper Reports)
- CRUD for contact persons
- CRUD tasks for customer (implemented using modifiyng version of full calendar). 
- CRUD any binary files to customer
- Send SMS to customers or group (implemented using Smscenter library)
- Send email to customers or group (implemented using JMS)
- CRUD group of customers
- CRUD template for sms/emails. Keywords like {customer.fullName}, {customer.inn} are supported and resolves into real values (implemented using stringtemplate.v4)

Administrator
- can manage integration data (customers, debts, invoice) from external warehouse - change external warehouse, run it manually.
- manage SMS center settings 

<b>External data integration</b>

Customers, debts, invoices are coming from external data. Now implementing retreiving data from XLS files. For extending you need to reimplement *Integration files and add support to facade.
By default integration runs every day at 9AM. It's hardcoded (using spring scheduling) so you need to change java code to change this behaviour. TODO: reimplement it using quartz


<b>Test data generation</b>

You can fill data to database base by test values by running DataGenerator (implemented using jfairy library)

<b>Configuring and deploy</b>

All configuration is concentrated in *.properties files and self explained (jms, jdbc properties)
It can be run on any application server. Was tested on Tomcat 6, 7, 8 using MySql.

