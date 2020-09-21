

# Icinga Java Library

This library is a Java interface for the [Icinga2 REST API](https://icinga.com/docs/icinga2/latest/doc/12-icinga2-api/). It attempts to implement functions as described in the API for querying information from the monitoring system as well as being able to do basic actions like acknowledging issues and scheduling downtime. 

__Be aware that this is compatible with Icinga 2 only.__

## Setup

A working Icinga server with the REST API enabled must be in place before this library can be used. Refer to the [Icinga documentation](https://icinga.com/docs/icinga2/latest/doc/12-icinga2-api/) on how to set this up. 

An API user must also be setup to use the REST API. This can be done by adding the following to a configuration file Icinga will read at runtime. The below example allows all permissions to this user. See the [permissions section](https://icinga.com/docs/icinga2/latest/doc/12-icinga2-api/#permissions) of the Icinga documentation to restrict this to what you need. 

```
object ApiUser "api_user" {
  password = "PUT SECURE PASS HERE"
  permissions = [ "*" ]
}
```

Once this is setup you can use the Icinga Java Library. 

## Basic Example

The below example initializes the API and does a basic query. The ```IcingaApi``` class is the base class needed for any action. When executing a query you must specify one of the various _target endpoints_ to pull information from. These are:

* HOST_ENDPOINT - information related to hosts
* SERVICE_ENDPOINT - information related to services
* CONTACT_ENDPOINT - information related to Icinga contacts

```

//create an IcingaApi object
IcingaApi api = new IcingaApi("https://icinga_url:5665/","api_user","PUT SECURE PASS HERE");

//create an Icinga query, specify the host endpoint
IcingaQuery host = new IcingaQuery(IcingaQuery.HOST_ENDPOINT);
		
//add attributes to gather about the host. See HostAttributes class for full list
host.addAttributes(HostAttributes.ALIAS,HostAttributes.ACTIVE,HostAttributes.CURRENT_STATE,HostAttributes.ZONE,HostAttributes.GROUPS);

//execute the query and print results as a table
List<ResponseRow> response = api.search(host);
for(int count =0; count < response.size(); count ++)
{
  
  //The ColumnList class has a helper method for printing responses in a readable way
  System.out.println(ColumnList.printColumns(response.get(count),host.getAttributes());
}

```

To interact with the results within your own program you can use the```ResponseRow``` object. Getting specific attributes within  a loop: 

```
List<ResponseRow> response = api.search(host); 
ResponseRow aRow = null;
for(int count =0; count < response.size(); count ++)
{
  aRow = response.get(count);

  //get the Host Alias attribute. Must be one of the gathered attributes in the query
  System.out.println(aRow.getString(HostAttributes.ALIAS));
}
```


## Filter and Sort

Filters and Sorts can be applied to a query to get better results. An ```IcingaFilter``` object can be applied to a query before it is run. There are many options here but a simple filter would take the form of:

### Filter Example
```

//create a filter to grab hosts in a certain ZONE that exists in specific GROUPS
IcingaFilter filter = new AndFilter(new ComparisonFilter(HostAttributes.ZONE,FilterOperator.LIKE,"zone"), new ComparisonFilter(HostAttributes.GROUPS,FilterOperator.CONTAINS_VALUE,"host-group"));

//add filter to the search
host.addFilter(filter);

```

The ```AndFilter``` will accept numerous ```ComparisonFilter``` objects and join them together as a logicial AND. The opposite is an ```OrFilter```. 

### Sort Example

Sorting can be added to a query as well to have the results sorted prior to being returned. Sorting of the returned ```List``` object can always be done in a normal Java fashion. 
```
//sort by Host Alias, desc
host.orderBy(new SortOrder(HostAttributes.ALIAS));

//sort by Host Alias, asc
host.orderBy(new SortOrder(HostAttributes.ALIAS, false));

```

## Actions

Actions can update items in your Icinga instance. Right now three actions are defined. They are Acknowledgements, Scheduling Downtime, and Removing Downtime. 

### Acknowledge
The ```AcknowledgeAction``` class can be used to acknowledge any open issues on a host or service in Icinga. Specifying which host or services to acknowledge is done with the aid of a filter. 

```
//create a filter to find a host with a given name that is in an error state
IcingaFilter hostFilter = new AndFilter(new ComparisonFilter(HostAttributes.NAME,FilterOperator.EQUAL,"host_name",new ComparisonFilter(HostAttributes.IS_DOWN,FilterOperator.EQUAL,1));
				
//create the action, specifying if you are targeting a host or a service, the icinga_user must match a valid Icinga username
AcknowledgeAction ackHost = new AcknowledgeAction(ObjectType.Host,hostFilter,"icing_user","Comment");

//optionally you can set an expiration in minutes from now
ackHost.setExpiration(60);

//send the action
icinga.action(ackHost);

```

### Downtime
Scheduling and Removing Downtime are very similar actions. The only difference is that when scheduling you need to specify the duration of the downtime. Again, filters are used to specify what hosts or services you are scheduling downtime for. 

_Keep in mind that scheduling host downtime does not automatically schedule downtime for the services. These are two separate actions._

```
//create a service filter to find all services attached to a specific host
IcingaFilter serviceFilter = new ComparisonFilter(HostAttributes.NAME,FilterOperator.EQUAL,"host_name"));

//setup downtime for this service, 2 hrs total starting now by default
ScheduleDowntimeAction scheduleService = new ScheduleDowntimeAction(ObjectType.Service,serviceFilter,"icinga_user","Scheduled downtime");

//to use flexible downtime set the fixed and flex duration settings, for more info see https://www.icinga.com/docs/icinga2/latest/doc/08-advanced-topics/#fixed-and-flexible-downtimes
scheduleService.setFixed(false);
scheduleService.setFlexDuration(7200);
					
icinga.action(scheduleService);
```